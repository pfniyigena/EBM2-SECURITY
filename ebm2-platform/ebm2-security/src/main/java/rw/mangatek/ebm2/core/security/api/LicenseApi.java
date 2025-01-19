/**
 * 
 */
package rw.mangatek.ebm2.core.security.api;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.Company;
import rw.mangatek.ebm2.core.enns.CompanyStatus;
import rw.mangatek.ebm2.core.security.dto.CompanyRequestDto;
import rw.mangatek.ebm2.core.security.dto.LicenseResponseDto;
import rw.mangatek.ebm2.core.security.util.CoreUIStatiValue;
import rw.mangatek.ebm2.core.security.util.MangatekInternalSecurityUtil;
import rw.mangatek.ebm2.core.service.ICompanyService;

import javax.xml.bind.JAXB;

/**
 * @author pfniy
 *
 */
@Slf4j
@RestController
public class LicenseApi {

	@Autowired
	private ICompanyService companyService;

	@PostMapping(value = "/rest/api/post/license")
	public @ResponseBody ResponseEntity<LicenseResponseDto> checkLicense(HttpServletRequest request,
																		 HttpServletResponse response) throws ServletException, IOException {
		HttpHeaders headers = new HttpHeaders();

		CompanyRequestDto cisInvoice = null;

		try {
			String ipAddress = MangatekInternalSecurityUtil.getClientIp(request);

			Map<String, Object> jsonMap = new HashMap<>();
			log.debug(String.format("===calling checkLicense API=====:CONTENTYPE:%s,ENCODING:%s",
					request.getContentType(), request.getCharacterEncoding()));
			if (request.getContentType().equals("application/xml")) {
				jsonMap = processXmlRequest(request, response, "VCIS SIGNATURE");
				headers.setContentType(MediaType.APPLICATION_XML);
			} else if (request.getContentType().equals("application/json")) {
				jsonMap = processJsonRequest(request, response, "VCIS SIGNATURE");
				headers.setContentType(MediaType.APPLICATION_JSON);
			}
			log.debug(String.format("------------CHECK LICENSE API-----OBJECT_KEY:{%s}",
					jsonMap.get(CoreUIStatiValue.OBJECT_KEY)));

			if (jsonMap.get(CoreUIStatiValue.OBJECT_KEY) != null) {
				cisInvoice = (CompanyRequestDto) jsonMap.get(CoreUIStatiValue.OBJECT_KEY);
			}

			if (cisInvoice == null) {
				headers.add(CoreUIStatiValue.ERROR_KEY, (String) jsonMap.get(CoreUIStatiValue.ERROR_KEY));
				return new ResponseEntity<>(new LicenseResponseDto(null), headers, HttpStatus.OK);
			}

			log.debug(String.format("------------CHECK LICENSE API-----CODE:{%s}", cisInvoice.getCode()));

			Company company = companyService.getByCode(cisInvoice.getCode());
			if (company == null) {

				headers.add(CoreUIStatiValue.ERROR_KEY, "COMPANY IS NOT FOUND");
				return new ResponseEntity<>(new LicenseResponseDto(null), headers, HttpStatus.OK);
			}
			company.setSyncTime(LocalDateTime.now());
			company.setIpAddress(ipAddress);
			companyService.updateEntity(company);
			log.debug(String.format("------------CHECK LICENSE API-----STATUS:{%s}", company.getStatus()));
			if (!company.getStatus().equals(CompanyStatus.ACTIVE)) {
				headers.add(CoreUIStatiValue.ERROR_KEY, "COMPANY IS :" + company.getStatus());
				return new ResponseEntity<>(new LicenseResponseDto(null), headers, HttpStatus.OK);
			}
			if (!company.getTinNumber().equals(cisInvoice.getTinNumber())) {
				headers.add(CoreUIStatiValue.ERROR_KEY,
						"TIN NUMBER NOT MATCH" + company.getTinNumber() + "---" + cisInvoice.getTinNumber());
				return new ResponseEntity<>(new LicenseResponseDto(null), headers, HttpStatus.OK);
			}
			headers.add(CoreUIStatiValue.ERROR_KEY, null);
			return new ResponseEntity<>(new LicenseResponseDto(CompanyStatus.ACTIVE.getStatus()), headers,
					HttpStatus.OK);

		} catch (Exception e) {

			headers.add(CoreUIStatiValue.ERROR_KEY, "Exception occur Contact Admin--");
			return new ResponseEntity<>(new LicenseResponseDto(null), headers, HttpStatus.OK);
		}
	}

	protected Map<String, Object> processJsonRequest(HttpServletRequest request, HttpServletResponse response,
			String method) throws ServletException, IOException {
		response.setContentType("application/json");
		Gson gson = null;

		Map<String, Object> result = new HashMap<>();
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
				CompanyRequestDto dto = (CompanyRequestDto) gson.fromJson(sb.toString(), CompanyRequestDto.class);
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
						(CompanyRequestDto) JAXB.unmarshal(new StringReader(sb.toString()), CompanyRequestDto.class));

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
