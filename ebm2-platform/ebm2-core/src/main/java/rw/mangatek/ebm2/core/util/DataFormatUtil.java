package rw.mangatek.ebm2.core.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.enns.ImportStatus;
import rw.mangatek.ebm2.core.enns.PurchaseStatus;

@Slf4j
public class DataFormatUtil {

	private DataFormatUtil() {
		throw new IllegalStateException("DataFormatUtil Utility class");
	}

	public static Date getDateFromUiString(String dateInString) {
		log.debug("getDateFromUiString:" + dateInString);
		Date parsedDate = null;
		try {
			if (dateInString == null || dateInString.isEmpty())
				return null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			parsedDate = dateFormat.parse(dateInString);
		} catch (Exception e) {
			e.getStackTrace();

		}
		return parsedDate;
	}

	public static LocalDate getLocalDateFromUiString(String dateInString) {

		LocalDate localDate = null;

		try {
			if (dateInString == null || dateInString.isEmpty())
				return null;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			localDate = LocalDate.parse(dateInString, formatter);
		} catch (Exception e) {
			e.getStackTrace();

		}
		return localDate;
	}

	public static String getDecimal(Double value) {
		log.debug("getDecimal:" + value);
		String result = String.format("%.2f", value);
		return result;

	}

	public static String parseCisStringDateToVsdcStringDate(LocalDate date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.US);
		if (date == null) {
			date = LocalDate.now();
		}
		return dtf.format(date);

	}

	public static LocalDate parseVsdcStringDateToLocalDate(String date) {
		if (date == null || date.isEmpty())
			return LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		return LocalDate.parse(date, formatter);

	}

	public static BigDecimal generateZeroDecimal(BigDecimal bigDecimal) {
		return bigDecimal == null ? new BigDecimal("0.00") : bigDecimal;

	}
	public static LocalDateTime parseApiStringDateToLocalDateTime(String date) {
		if (date == null || date.isEmpty())
			return LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.parse(date, formatter);
		return localDate;
	}
	public static ImportStatus parseImportationStatus(String status) {
		if (status.equals(ImportStatus.PENDING.getStatus())) {
			return ImportStatus.PENDING;
		} else if (status.equals(ImportStatus.APPROVED.getStatus())) {
			return ImportStatus.APPROVED;
		} else if (status.equals(ImportStatus.REJECTED.getStatus())) {
			return ImportStatus.REJECTED;
		} else {
			return ImportStatus.PENDING;
		}
	}
	public static PurchaseStatus parsePurchaseStatus(String status) {
		if (status.equals(PurchaseStatus.PENDING.getStatus())) {
			return PurchaseStatus.PENDING;
		} else if (status.equals(PurchaseStatus.APPROVED.getStatus())) {
			return PurchaseStatus.APPROVED;
		} else if (status.equals(PurchaseStatus.REJECTED.getStatus())) {
			return PurchaseStatus.REJECTED;
		} else {
			return PurchaseStatus.PENDING;
		}
	}
}
