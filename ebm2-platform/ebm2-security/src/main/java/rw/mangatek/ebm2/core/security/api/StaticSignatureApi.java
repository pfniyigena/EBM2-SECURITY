/**
 * 
 */
package rw.mangatek.ebm2.core.security.api;

import java.io.IOException;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.security.dto.LicenseResponseDto;
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
			return new ResponseEntity<>(
					new LicenseResponseDto(
							"SDC014000807,24531,24531,NS,2024-10-15 11:29:53,U2O4NOWJZ5P4A2EL,XAIE3E25NPZVBIQAQFVWBTW7LA"),
					headers, HttpStatus.OK);

		} catch (Exception e) {

			headers.add(CoreUIStatiValue.ERROR_KEY, "Exception occur Contact Admin--");
			return new ResponseEntity<>(new LicenseResponseDto(null), headers, HttpStatus.OK);
		}
	}

}
