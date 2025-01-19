/**
 * 
 */
package rw.mangatek.ebm2.core.ui.api;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import ebm.vsdc.excute.model.TrnsSalesSaveWrReq;
import ebm.vsdc.excute.model.TrnsSalesSaveWrRes;
import ebm.vsdc.excute.model.TrnsSalesSaveWrRes.TrnsSalesSaveWrResData;
import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmInvoice;
import rw.mangatek.ebm2.core.domain.EbmSetting;
import rw.mangatek.ebm2.core.enns.SettingName;
import rw.mangatek.ebm2.core.service.IEbmInvoiceService;
import rw.mangatek.ebm2.core.service.IEbmSettingService;
import rw.mangatek.ebm2.core.ui.dto.VCISInvoiceDto;
import rw.mangatek.ebm2.core.ui.dto.VCISInvoiceResponseDto;
import rw.mangatek.ebm2.core.ui.util.CoreUIStatiValue;
import rw.mangatek.ebm2.core.ui.util.ParserData;
import rw.mangatek.ebm2.rra.service.IVsdcService;
import rw.mangatek.ebm2.rra.util.VsdcRRACodeUtil;

/**
 * @author pfniy
 *
 */
@Slf4j
@RestController
public class InvoiceApi {
	@Autowired
	private IVsdcService vsdcService;

	@Autowired
	private IEbmInvoiceService ebmInvoiceService;

	@Autowired
	private IEbmSettingService ebmSettingService;

	@PostMapping(value = "/rest/api/post/cisinvoice")
	public @ResponseBody ResponseEntity<VCISInvoiceResponseDto> generateInvoices(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpHeaders headers = new HttpHeaders();
		TrnsSalesSaveWrResData responseSignature = null;
		VCISInvoiceDto cisInvoice = null;
		TrnsSalesSaveWrRes vsdcResponse = null;
		try {

			Map<String, Object> jsonMap = new HashMap<String, Object>();
			log.debug("===calling generateInvoices API=====:" + request.getContentType()
					+ request.getCharacterEncoding());
			if (request.getContentType().equals("application/xml")) {
				jsonMap = processXmlRequest(request, response, "VCIS SIGNATURE");
				headers.setContentType(MediaType.APPLICATION_XML);
			} else if (request.getContentType().equals("application/json")) {
				jsonMap = processJsonRequest(request, response, "VCIS SIGNATURE");
				headers.setContentType(MediaType.APPLICATION_JSON);
			}
			if (jsonMap.get(CoreUIStatiValue.OBJECT_KEY) != null) {
				cisInvoice = (VCISInvoiceDto) jsonMap.get(CoreUIStatiValue.OBJECT_KEY);
			}

			if (cisInvoice == null) {
				headers.add(CoreUIStatiValue.ERROR_KEY, (String) jsonMap.get(CoreUIStatiValue.ERROR_KEY));
				return new ResponseEntity<>(new VCISInvoiceResponseDto(null), headers, HttpStatus.OK);
			}

			EbmSetting mrcSetting = ebmSettingService.getByName(SettingName.CURRENT_MRC.name());
			EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
			EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
			EbmSetting registeredAddress = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_ADDRESS.name());
			EbmSetting registeredName = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_NAME.name());

			log.debug(String.format("------------GENERATING INVOICE FROM API-----TIN:{%s},MRC:{%s},NAME:{%s},BRANCH:{%s}",
					registeredTin.getSettingValue(), mrcSetting.getSettingValue(), registeredName.getSettingValue(),
					registeredBranch.getSettingValue()));

			if (!mrcSetting.getSettingValue().equals(cisInvoice.getMrc())) {
				headers.add(CoreUIStatiValue.ERROR_KEY, "DEVICE NOT FOUND");
				return new ResponseEntity<>(new VCISInvoiceResponseDto(null, null, null, null, null, null, null, null),
						headers, HttpStatus.OK);
			}

			Map<String, Object> parseDataModel = ParserData.generateReceiptDataFromVCISInvoiceDto(cisInvoice,
					registeredTin.getSettingValue(), registeredBranch.getSettingValue(),
					registeredName.getSettingValue(), registeredAddress.getSettingValue(),
					mrcSetting.getSettingValue());
			EbmInvoice ebmInvoice = (EbmInvoice) parseDataModel.get(CoreUIStatiValue.EBM_INVOICE);
			EbmInvoice savedEbmInvoice = ebmInvoiceService.saveNewInvoice(ebmInvoice);

			boolean sentToServer = false;
			if (savedEbmInvoice == null) {
				headers.add(CoreUIStatiValue.ERROR_KEY, "ERROR WHILE SAVING INVOICE");
				return new ResponseEntity<>(new VCISInvoiceResponseDto(null, null, null, null, null, null, null, null),
						headers, HttpStatus.OK);
			}

			TrnsSalesSaveWrReq req = (TrnsSalesSaveWrReq) parseDataModel.get(CoreUIStatiValue.RRA_INVOICE);
			vsdcResponse = vsdcService.generateReceipt(req);

			if (vsdcResponse == null) {
				headers.add(CoreUIStatiValue.ERROR_KEY, "ERROR WHILE GENERATING SIGNATURE");
				return new ResponseEntity<>(new VCISInvoiceResponseDto(null, null, null, null, null, null, null, null),
						headers, HttpStatus.OK);
			}
			if (vsdcResponse.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {
				sentToServer = true;
			}
			responseSignature = vsdcResponse.getData();
			if (responseSignature == null) {
				headers.add(CoreUIStatiValue.ERROR_KEY,
						"ERROR WHILE GENERATING SIGNATURE" + vsdcResponse.getResultMsg());
				return new ResponseEntity<>(new VCISInvoiceResponseDto(null, null, null, null, null, null, null, null),
						headers, HttpStatus.OK);
			}
			EbmInvoice updatedInvoice = ParserData.updateEbmInvoiceWithVsdcResponse(savedEbmInvoice, responseSignature);
			ebmInvoiceService.updateInvoice(updatedInvoice, sentToServer);
			headers.add(CoreUIStatiValue.ERROR_KEY, null);
			return new ResponseEntity<>(new VCISInvoiceResponseDto(responseSignature.getSdcId(),
					responseSignature.getRcptNo().intValue(), responseSignature.getTotRcptNo().intValue(),
					cisInvoice.getInvoiceType(), cisInvoice.getTransactionType(),
					ParserData.parseVsdcStringDateToVsdcStringDateTime(vsdcResponse.getResultDt()),
					responseSignature.getRcptSign(), responseSignature.getIntrlData()), headers, HttpStatus.OK);

		} catch (Exception e) {
			String message = "";
			if (responseSignature != null) {
				VCISInvoiceResponseDto result = new VCISInvoiceResponseDto(responseSignature.getSdcId(),
						responseSignature.getRcptNo().intValue(), responseSignature.getTotRcptNo().intValue(),
						cisInvoice.getInvoiceType(), cisInvoice.getTransactionType(), vsdcResponse.getResultDt(),
						responseSignature.getRcptSign(), responseSignature.getIntrlData());
				message = result.getSdcData();
			}
			log.debug("EXCEPTION:{}", e.toString());
			e.printStackTrace();
			headers.add(CoreUIStatiValue.ERROR_KEY, "Exception occur Contact Admin--" + message);
			return new ResponseEntity<>(new VCISInvoiceResponseDto(null), headers, HttpStatus.OK);
		}
	}

	protected Map<String, Object> processJsonRequest(HttpServletRequest request, HttpServletResponse response,
			String method) throws ServletException, IOException {
		response.setContentType("application/json");
		Gson gson = null;

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			gson = new GsonBuilder().registerTypeAdapter(Date.class, new GsonUTCDateAdapter()).create();
			// Check whether client is behind any proxy
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) { // Means client was not behind any proxy
				ipAddress = request.getRemoteAddr(); // Then we can use
														// getRemoteAddress to
														// get the client ip
														// address
			}

			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}

			log.debug("=========================:" + sb);
			if (StringUtils.isNotBlank(sb)) {
				VCISInvoiceDto dto = (VCISInvoiceDto) gson.fromJson(sb.toString(), VCISInvoiceDto.class);
				result.put(CoreUIStatiValue.OBJECT_KEY, dto);
				log.debug("=========================:" + dto);
			} else {
				result.put(CoreUIStatiValue.ERROR_KEY, "Empty Request");
				result.put(CoreUIStatiValue.OBJECT_KEY, null);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			result.put(CoreUIStatiValue.ERROR_KEY, ex.toString());
			result.put(CoreUIStatiValue.OBJECT_KEY, null);
		}
		return result;
	}

	protected Map<String, Object> processXmlRequest(HttpServletRequest request, HttpServletResponse response,
			String method) throws ServletException, IOException {
		response.setContentType("application/xml");
		Map<String, Object> result = new HashMap<String, Object>();
		try {

			// Check whether client is behind any proxy
			String ipAddress = request.getHeader("X-FORWARDED-FOR");
			if (ipAddress == null) { // Means client was not behind any proxy
				ipAddress = request.getRemoteAddr(); // Then we can use
														// getRemoteAddress to
														// get the client ip
														// address
			}

			StringBuilder sb = new StringBuilder();
			String s;
			while ((s = request.getReader().readLine()) != null) {
				sb.append(s);
			}

			log.debug("=========================:" + sb);
			if (StringUtils.isNotBlank(sb)) {

				result.put(CoreUIStatiValue.OBJECT_KEY,
						(VCISInvoiceDto) JAXB.unmarshal(new StringReader(sb.toString()), VCISInvoiceDto.class));

			} else {
				result.put(CoreUIStatiValue.ERROR_KEY, "Empty Request");
				result.put(CoreUIStatiValue.OBJECT_KEY, null);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			result.put(CoreUIStatiValue.ERROR_KEY, ex.toString());
			result.put(CoreUIStatiValue.OBJECT_KEY, null);
		}
		return result;
	}

	public static class GsonUTCDateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

		private DateFormat dateFormat = null;;

		public GsonUTCDateAdapter() {
			try {
				dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US); // This is the format I need
				dateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // This is the key line which converts the date to
																		// UTC which cannot be accessed with the default
																		// serializer
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public synchronized JsonElement serialize(Date date, Type type,
				JsonSerializationContext jsonSerializationContext) {
			return new JsonPrimitive(dateFormat.format(date));
		}

		@Override
		public synchronized Date deserialize(JsonElement jsonElement, Type type,
				JsonDeserializationContext jsonDeserializationContext) {
			try {
				return dateFormat.parse(jsonElement.getAsString());
			} catch (ParseException e) {
				e.printStackTrace();
				throw new JsonParseException(e);
			}
		}
	}

}
