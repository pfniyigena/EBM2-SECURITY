/**
 * 
 */
package rw.mangatek.ebm2.core.security.api;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.security.dto.Header;
import rw.mangatek.ebm2.core.security.dto.LicenseResponseDto;
import rw.mangatek.ebm2.core.security.dto.ResponseWrapper;
import rw.mangatek.ebm2.core.security.dto.Row;
import rw.mangatek.ebm2.core.security.dto.RraVscDataResponseDto;
import rw.mangatek.ebm2.core.security.dto.RraVscResponseDto;
import rw.mangatek.ebm2.core.security.util.CoreUIStatiValue;

/**
 * @author pfniy
 *
 */
@Slf4j
@RestController
public class StaticSignatureApi {

	@PostMapping(value = "/mangatek/rest/api/post/cisinvoice")
	public @ResponseBody ResponseEntity<LicenseResponseDto> generateInvoices(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpHeaders headers = new HttpHeaders();

		try {
			log.debug(String.format("-----Requesting signature:%s",
					"SDC016000053,72,116,NS,12/11/2021 17:06:44,PDY37AP6BGE5V5DF,4MIE4VOKBHTFLHZLHKRMOYCD4I"));
			headers.add(CoreUIStatiValue.ERROR_KEY, null);
			return new ResponseEntity<>(
					new LicenseResponseDto(
							"SDC016000053,72,116,NS,12/11/2021 17:06:44,PDY37AP6BGE5V5DF,4MIE4VOKBHTFLHZLHKRMOYCD4I"),
					headers, HttpStatus.OK);

		} catch (Exception e) {

			headers.add(CoreUIStatiValue.ERROR_KEY, "Exception occur Contact Admin--");
			return new ResponseEntity<>(new LicenseResponseDto(null), headers, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/rra/rest/api/post/cisinvoice")
	public @ResponseBody ResponseEntity<LicenseResponseDto> generateInvoicesFromRRRA(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpHeaders headers = new HttpHeaders();

		try {
			log.debug(String.format("-----Requesting signature:%s",
					"SDC014000807,24531,24531,NS,2024-10-15 11:29:53,U2O4NOWJZ5P4A2EL,XAIE3E25NPZVBIQAQFVWBTW7LA"));
			headers.add(CoreUIStatiValue.ERROR_KEY, null);
			return new ResponseEntity<>(new LicenseResponseDto(
					"SDC014000807,24531,24531,NS,2024-10-15 11:29:53,U2O4NOWJZ5P4A2EL,XAIE3E25NPZVBIQAQFVWBTW7LA"),
					headers, HttpStatus.OK);

		} catch (Exception e) {

			headers.add(CoreUIStatiValue.ERROR_KEY, "Exception occur Contact Admin--");
			return new ResponseEntity<>(new LicenseResponseDto(null), headers, HttpStatus.OK);
		}
	}

	@PostMapping(value = "/mangatek/rest/api/vsdc/activation", consumes = { MediaType.APPLICATION_XML_VALUE,
			"text/xml" }, produces = MediaType.APPLICATION_XML_VALUE // Returns XML output
	)
	public ResponseWrapper processActivation(@RequestBody String requestXml) {
		// Log the incoming request for debugging
		System.out.println("Received Request: " + requestXml);

		// Create the Response Object
		ResponseWrapper response = new ResponseWrapper();

		// 1. Set Header
		Header header = new Header();
		header.setResultCode("00");
		header.setResultMsg("SUCCESS");
		header.setResDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
		response.setHeader(header);

		// 2. Set Row Data
		Row row = new Row();
		row.setTable("VSDCACTIVATION");
		row.setActionCd("ACT");
		row.setTin("100018436");
		row.setMrcno("WIS01042351");
		row.setIntkey(
				"PK4IMVE34VWM77RBN3NJJXU3TZQSXMCH73H646O5SYEF5LSYXSCZO4W3EAUZXHQN5XV3C2HUCB5FX6TWKE6TN65DYXQIVVPTHEPJVQY");
		row.setSignkey(
				"MOHIGA3S3SVMSCU4JAQRPPJI73W5EPETGIRQWFXNNYGIDRBUWJVRIC6NR5PMXE4CWJTK3Q36ZSFZEMQZUVDBWRWW26RVLM2BVSAWCXY");
		row.setCommkey(
				"YLIR4WKU3H4SJ466GOUAP63PXVN6CTEWWG2O7XFUTKRZFGCFBZYWYIARHU53F7PZGZCLG2YS2UH3SCNOYCQ3XANRMT6GSKBTLOKMDOY");
		row.setToken("10797EE2-E107-4320-9FE0-1D32D9D694CB");
		row.setNonVat("00");
		row.setTrsmTaxFlg("N");
		response.setRow(row);

		return response;
	}

	@PostMapping(value = "/trnsSales/saveSales",produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<RraVscResponseDto> returnRraVscResponseDto(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpHeaders headers = new HttpHeaders();

		try {
			log.debug(String.format("-----returnRraVscResponseDto:%s",
					"SDC014000807,24531,24531,NS,2024-10-15 11:29:53,U2O4NOWJZ5P4A2EL,XAIE3E25NPZVBIQAQFVWBTW7LA"));
			headers.add(CoreUIStatiValue.ERROR_KEY, null);
			RraVscResponseDto rraVscResponseDto = RraVscResponseDto.builder().resultCd("000")
					.resultMsg("It is succeeded")
					.resultDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
					.rraVscDataResponseDto(RraVscDataResponseDto.builder().intrlData("5BHRN3AQ7RB6UKDO3XA5FLEEWU")
							.rcptSign("U47NUJSPJOF4DLJR").rcptNo(1).totRcptNo(1)
							.vsdcRcptPbctDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
							.sdcId("SDC014000807").mrcNo("WIS01042351").build())
					.build();
			return new ResponseEntity<>(rraVscResponseDto, headers, HttpStatus.OK);

		} catch (Exception e) {

			headers.add(CoreUIStatiValue.ERROR_KEY, "Exception occur Contact Admin--");
			return new ResponseEntity<>(RraVscResponseDto.builder().build(), headers, HttpStatus.OK);
		}
	}
}
