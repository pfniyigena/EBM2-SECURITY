/**
 * 
 */
package rw.mangatek.ebm2.core.ui.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import rw.mangatek.ebm2.core.domain.EbmInvoice;
import rw.mangatek.ebm2.core.domain.EbmSaleItem;

/**
 * @author pfniy
 *
 */
public class JournalFormatter {

	public static String generateJournal(EbmInvoice ebmInvoice) {
		StringBuilder bld = new StringBuilder();
		bld.append(formatForEJAddress(ebmInvoice.getRegistrationName()));
		bld.append(formatForEJAddress("TIN:" + ebmInvoice.getRegisteredTin()));
		if (ebmInvoice.getRegistrationAddress() != null && !ebmInvoice.getRegistrationAddress().isEmpty())
			bld.append(formatForEJAddress("ADDRESS:" + ebmInvoice.getRegistrationAddress()));
		if (ebmInvoice.getRegistrationPhone() != null && !ebmInvoice.getRegistrationPhone().isEmpty())
			bld.append(formatForEJAddress("PHONE:" + ebmInvoice.getRegistrationPhone()));
		if (ebmInvoice.getRegistrationEmail() != null && !ebmInvoice.getRegistrationEmail().isEmpty())
			bld.append(formatForEJAddress("EMAIL:" + ebmInvoice.getRegistrationEmail()));
		bld.append(formatForEJAddress("----------------------------------"));
		if (ebmInvoice.getTransactionType().equals("R")) {
			bld.append(formatForEJAddress("REFUND"));
			bld.append(formatForEJAddress("REF. NORMAL RECEIPT:" + ebmInvoice.getOrginalInvoice()));
		}
		bld.append(formatForEJAddress(ebmInvoice.getEbmReceipt().getTopMessage()));
		if (ebmInvoice.getCustomerTin() != null && !ebmInvoice.getCustomerTin().isEmpty())
			bld.append(formatForEJAddress("CLIENT ID:" + ebmInvoice.getCustomerTin()));
		bld.append(formatForEJAddress("----------------------------------"));
		for (EbmSaleItem sale : ebmInvoice.getSaleItems()) {
			bld.append(" " + sale.getItemName() + "\n");
			if (ebmInvoice.getTransactionType().equals("S")) {
				bld.append(formatForSDC3Data(sale.getSaleUnitPrice() + "x &" + sale.getQuantity() + "&"
						+ sale.getTotalAmount() + sale.getTaxLabel()));
			} else {
				bld.append(formatForSDC3Data(sale.getSaleUnitPrice() + "x &" + sale.getQuantity() + "&-"
						+ sale.getTotalAmount() + sale.getTaxLabel()));
			}
			if (sale.getDiscountAmount().intValue() > 0) {
				bld.append(formatForSDC2Data2("DISCOUNT-" + sale.getDiscountRate()) + "%#" + sale.getTotalAmount());
			}

		}
		bld.append(formatForEJAddress("----------------------------------"));
		if (ebmInvoice.getTransactionType().equals("S")) {
			bld.append(formatForSDC2Data2("TOTAL#" + ebmInvoice.getTotalAmount()));
			if (ebmInvoice.getTaxableAmountA().intValue() > 0) {
				bld.append(formatForSDC2Data2("TOTAL A-EX#" + ebmInvoice.getTaxableAmountA()));
			}
			if (ebmInvoice.getTaxableAmountB().intValue() > 0) {
				bld.append(formatForSDC2Data2(
						"TOTAL B-" + ebmInvoice.getTaxRateB() + "%#" + ebmInvoice.getTaxableAmountB()));
			}
			if (ebmInvoice.getTaxableAmountC().intValue() > 0) {
				bld.append(formatForSDC2Data2(
						"TOTAL C-" + ebmInvoice.getTaxRateC() + "%#" + ebmInvoice.getTaxableAmountC()));
			}
			if (ebmInvoice.getTaxableAmountD().intValue() > 0) {
				bld.append(formatForSDC2Data2(
						"TOTAL D-" + ebmInvoice.getTaxRateD() + "%#" + ebmInvoice.getTaxableAmountD()));

			}
			if (ebmInvoice.getTaxAmountB().intValue() > 0) {
				bld.append(formatForSDC2Data2("TOTAL TAX B#" + ebmInvoice.getTaxAmountB()));
			}
			if (ebmInvoice.getTaxAmountC().intValue() > 0) {
				bld.append(formatForSDC2Data2("TOTAL TAX C#" + ebmInvoice.getTaxAmountC()));
			}
			if (ebmInvoice.getTaxAmountD().intValue() > 0) {
				bld.append(formatForSDC2Data2("TOTAL TAX D#" + ebmInvoice.getTaxAmountD()));
			}
			if (ebmInvoice.getTotalTaxAmount().intValue() > 0) {
				bld.append(formatForSDC2Data2("TOTAL TAX #" + ebmInvoice.getTotalTaxAmount()));
				bld.append(formatForEJAddress("----------------------------------"));
				bld.append(formatForSDC2Data2("CASH #" + ebmInvoice.getTotalAmount()));
			}

		} else {
			bld.append(formatForSDC2Data2("TOTAL #-" + ebmInvoice.getTotalAmount()));
			if (ebmInvoice.getTaxableAmountA().intValue() > 0) {
				bld.append(formatForSDC2Data2("TOTAL A-EX#-" + ebmInvoice.getTaxableAmountA()));
			}
			if (ebmInvoice.getTaxableAmountB().intValue() > 0) {
				bld.append(formatForSDC2Data2(
						"TOTAL B-" + ebmInvoice.getTaxRateB() + "%#-" + ebmInvoice.getTaxableAmountB()));
			}
			if (ebmInvoice.getTaxableAmountC().intValue() > 0) {
				bld.append(formatForSDC2Data2(
						"TOTAL C-" + ebmInvoice.getTaxRateC() + "%#-" + ebmInvoice.getTaxableAmountC()));
			}
			if (ebmInvoice.getTaxableAmountD().intValue() > 0) {
				bld.append(formatForSDC2Data2(
						"TOTAL D-" + ebmInvoice.getTaxRateD() + "%#-" + ebmInvoice.getTaxableAmountD()));
			}
			if (ebmInvoice.getTaxAmountB().intValue() > 0) {
				bld.append(formatForSDC2Data2("TOTAL TAX B#-" + ebmInvoice.getTaxAmountB()));
			}
			if (ebmInvoice.getTaxAmountC().intValue() > 0) {
				bld.append(formatForSDC2Data2("TOTAL TAX C#-" + ebmInvoice.getTaxAmountC()));
			}
			if (ebmInvoice.getTaxAmountD().intValue() > 0) {
				bld.append(formatForSDC2Data2("TOTAL TAX D#-" + ebmInvoice.getTaxAmountD()));
			}
			if (ebmInvoice.getTotalTaxAmount().intValue() > 0) {
				bld.append(formatForSDC2Data2("TOTAL TAX #-" + ebmInvoice.getTotalTaxAmount()));
				bld.append(formatForEJAddress("----------------------------------"));
				bld.append(formatForSDC2Data2("CASH #-" + ebmInvoice.getTotalAmount()));
			}
		}
		bld.append(formatForSDC2Data2("ITEMS NUMBER #" + ebmInvoice.getSaleItems().size()));
		bld.append(formatForEJAddress("----------------------------------"));
		bld.append(formatForEJAddress("SDC INFORMATION"));
		String sdcDate = parseLOcalDateAndTimeToString(ebmInvoice.getEbmReceipt().getVsdcDate());
		
		if (ebmInvoice.getEbmReceipt().getVsdcDate() != null)
			bld.append(formatForSDC2Data2("DATE:" + sdcDate.split(" ")[0] + "#TIME:" + sdcDate.split(" ")[1]));

		bld.append(formatForSDC2Data2("VSDC RECEIPT NUMBER: #" + ebmInvoice.getEbmReceipt().getVsdcReceiptNumber() + "/"
				+ ebmInvoice.getEbmReceipt().getVsdcTotalReceiptNumber() + " " + ebmInvoice.getReceiptType()
				+ ebmInvoice.getTransactionType()));

		bld.append(formatForSDC2Data2("SDC ID: #" + ebmInvoice.getEbmReceipt().getSdcId()));
		if (ebmInvoice.getEbmReceipt().getVsdcInternalData() != null
				&& !ebmInvoice.getEbmReceipt().getVsdcInternalData().isEmpty()) {
			bld.append(formatForEJAddress("Internal Data:"));
			bld.append(formatForEJAddress(formateStringWithSlash(ebmInvoice.getEbmReceipt().getVsdcInternalData())));
		}
		if (ebmInvoice.getEbmReceipt().getVsdcSignature() != null
				&& !ebmInvoice.getEbmReceipt().getVsdcSignature().isEmpty()) {
			bld.append(formatForEJAddress("Receipt Signature:"));
			bld.append(formatForEJAddress(formateStringWithSlash(ebmInvoice.getEbmReceipt().getVsdcSignature())));
		}
		bld.append(formatForEJAddress("----------------------------------"));
		bld.append(formatForSDC2Data2("CIS RECEIPT NUMBER: #" + ebmInvoice.getInvoiceNumber()));
		String cisDate = parseLOcalDateAndTimeToString(ebmInvoice.getCisDate());
		bld.append(formatForSDC2Data2("DATE:" + cisDate.split(" ")[0] + "#TIME:" + cisDate.split(" ")[1]));
		bld.append(formatForSDC2Data2("MRC: #" + ebmInvoice.getEbmReceipt().getMrc()));
		bld.append(formatForEJAddress("----------------------------------"));
		if (ebmInvoice.getEbmReceipt().getBottomMessage() != null
				&& !ebmInvoice.getEbmReceipt().getBottomMessage().isEmpty())
			bld.append(formatForEJAddress(ebmInvoice.getEbmReceipt().getBottomMessage()));
		return bld.toString();
	}

	// formatForEJAddress
	private static String formatForEJAddress(String message) {
		StringBuilder bld = new StringBuilder();
		int s = message.length();
		int reste = 0;
		int sSize = 0;
		if (s < 40) {
			reste = 40 - s;
			sSize = reste / 2;
			for (int i = 0; i < sSize; i++) {
				bld.append(" ");
			}
			bld.append(message);

			sSize = sSize + reste % 2;
			for (int i = 0; i < sSize; i++) {
				bld.append(" ");
			}
			bld.append("\n");
			return bld.toString();

		} else {
			bld.append(message);
			bld.append("\n");
			return bld.toString();
		}

	}

	// formatForSDC3Data
	private static String formatForSDC3Data(String message) {
		StringBuilder bld = new StringBuilder();
		String[] list2 = message.split("&");
		if (list2.length >= 3) {
			String l1 = list2[0];
			String l2 = list2[1];
			String l3 = list2[2];
			int size1 = l1.length();
			int size2 = l2.length();
			int size3 = l3.length();
			int totalSize = size1 + size2 + size3;
			int rest = ((40 - totalSize) / 2);
			bld.append("");
			bld.append(" ");
			bld.append(l1);
			for (int i = 0; i < rest; i++) {
				bld.append(" ");
			}
			bld.append(l2);

			rest = rest + (40 - totalSize) % 2;
			for (int i = 0; i < rest; i++) {
				bld.append(" ");
			}
			bld.append(l3);
			bld.append(" ");
			bld.append("\n");
			return bld.toString();
		} else {
			bld.append(message);
			bld.append("\n");
			return bld.toString();
		}

	}

	// formatForSDC2DataReverse
	public static String formatForSDC2DataReverse(String message) {

		StringBuilder bld = new StringBuilder();
		String[] list2 = message.split("^");

		if (list2.length >= 2) {
			String l1 = list2[0];
			String l2 = list2[1];
			int size1 = l1.length();
			int size2 = l2.length();
			int totalSize = size1 + size2;
			int rest = 40 - totalSize;
			bld.append("");
			bld.append(" ");
			bld.append(l1);
			for (int i = 0; i < rest; i++) {
				bld.append(" ");
			}
			bld.append(l2);
			bld.append(" ");
			bld.append("\n");
			return bld.toString();
		} else {
			bld.append(message);
			bld.append("\n");
			return bld.toString();
		}
	}

	// formatForSDC2Data2
	private static String formatForSDC2Data2(String message) {
		StringBuilder bld = new StringBuilder();
		String[] list2 = message.split("#");
		if (list2.length >= 2) {
			String l1 = list2[0];
			String l2 = list2[1];
			int size1 = l1.length();
			int size2 = l2.length();
			int totalSize = size1 + size2;
			int rest = 40 - totalSize;
			bld.append("");
			bld.append(" ");
			bld.append(l1);
			for (int i = 0; i < rest; i++) {
				bld.append(" ");
			}
			bld.append(l2);
			bld.append(" ");
			bld.append("\n");
			return bld.toString();

		} else {
			bld.append(message);
			bld.append("\n");
			return bld.toString();
		}
	}

	// formateStringWithSlash
	public static String formateStringWithSlash(String d) {

		int j = 4;
		String data = "";
		for (int i = 0; i < d.length(); i++) {
			if (j >= d.length()) {
				data = data + d.substring(i, d.length());
			} else {
				data = data + d.substring(i, j) + "-";
			}
			i = j - 1;
			j = j + 4;
		}
		return data;
	}
	public static String formateDateAndTimeStringWithSlash(LocalDateTime datetime) {
		StringBuilder bld = new StringBuilder();
		String cisDate = parseLOcalDateAndTimeToString(datetime);
		bld.append(formatForSDC2Data2("DATE:" + cisDate.split(" ")[0] + " TIME:" + cisDate.split(" ")[1]));
		return bld.toString();
	}

	private static String parseLOcalDateAndTimeToString(LocalDateTime date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);

		if (date == null) {
			date = LocalDateTime.now();
		}
		String dateFormat = dtf.format(date);
		return dateFormat;

	}
}
