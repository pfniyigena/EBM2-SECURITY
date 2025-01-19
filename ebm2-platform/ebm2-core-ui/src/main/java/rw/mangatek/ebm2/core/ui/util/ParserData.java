/**
 * 
 */
package rw.mangatek.ebm2.core.ui.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import ebm.vsdc.excute.model.TrnsSalesSaveWrItem;
import ebm.vsdc.excute.model.TrnsSalesSaveWrReceipt;
import ebm.vsdc.excute.model.TrnsSalesSaveWrReq;
import ebm.vsdc.excute.model.TrnsSalesSaveWrRes.TrnsSalesSaveWrResData;
import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmInvoice;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.domain.EbmPurchase;
import rw.mangatek.ebm2.core.domain.EbmPurchaseItem;
import rw.mangatek.ebm2.core.domain.EbmReceipt;
import rw.mangatek.ebm2.core.domain.EbmSaleItem;

import rw.mangatek.ebm2.core.ui.dto.VCISInvoiceDto;
import rw.mangatek.ebm2.core.ui.dto.VCISPurchaseDto;
import rw.mangatek.ebm2.core.ui.dto.VCISPurchaseItemDto;
import rw.mangatek.ebm2.core.ui.dto.VCISSaleDto;
import rw.mangatek.ebm2.core.util.Ebm2StaticValue;

/**
 * @author pfniy
 *
 */
@Slf4j
public class ParserData {
	private ParserData() {
		throw new IllegalStateException("ParserData Utility class");
	}

	public static Map<String, Object> generateReceiptDataFromVCISInvoiceDto(VCISInvoiceDto vcisInvoiceDto,
			String registeredTin, String registeredBranch, String registeredName, String registeredAddress,
			String mrc) {
		Map<String, Object> invoiceMap = parseCisInvoice(vcisInvoiceDto.getRegisteredTin(), registeredBranch,
				Long.valueOf(vcisInvoiceDto.getInvoiceNumber()),
				vcisInvoiceDto.getTransactionType().equalsIgnoreCase("R") ? new Long(vcisInvoiceDto.getRefundRef())
						: Long.valueOf("0"),
				vcisInvoiceDto.getClientTin(), vcisInvoiceDto.getClientName(), vcisInvoiceDto.getInvoiceType(),
				vcisInvoiceDto.getTransactionType(), vcisInvoiceDto.getPaymentCode(), vcisInvoiceDto.getInvoiceStatus(),
				vcisInvoiceDto.getValidatedDate(), vcisInvoiceDto.getCisDate(), vcisInvoiceDto.getStockReleasedDate(),
				vcisInvoiceDto.getCancelReqeuestedDate(), vcisInvoiceDto.getCanceledDate(),
				vcisInvoiceDto.getRefundedReasonCode(), vcisInvoiceDto.getSales().size(),
				vcisInvoiceDto.getTaxableAmountA(), vcisInvoiceDto.getTaxableAmountB(),
				vcisInvoiceDto.getTaxableAmountC(), vcisInvoiceDto.getTaxableAmountD(), vcisInvoiceDto.getTaxRateA(),
				vcisInvoiceDto.getTaxRateB(), vcisInvoiceDto.getTaxRateC(), vcisInvoiceDto.getTaxRateD(),
				vcisInvoiceDto.getTaxAmountA(), vcisInvoiceDto.getTaxAmountB(), vcisInvoiceDto.getTaxAmountC(),
				vcisInvoiceDto.getTaxAmountD(), vcisInvoiceDto.getTotalTaxAmount(), vcisInvoiceDto.getTotalAmount(),
				vcisInvoiceDto.getPurchaseAccept(), vcisInvoiceDto.getRemark(), vcisInvoiceDto.getUserId(),
				registeredName, registeredTin, registeredAddress);

		TrnsSalesSaveWrReq rraInvoice = (TrnsSalesSaveWrReq) invoiceMap.get(CoreUIStatiValue.RRA_INVOICE);
		EbmInvoice ebmInvoice = (EbmInvoice) invoiceMap.get(CoreUIStatiValue.EBM_INVOICE);
		List<TrnsSalesSaveWrItem> rraItemList = new ArrayList<>();
		List<EbmSaleItem> ebmItemList = new ArrayList<>();
		for (VCISSaleDto sale : vcisInvoiceDto.getSales()) {
			Map<String, Object> itemMap = parseSaleItem(sale.getProductCode(), sale.getUnspcCode(),
					sale.getProductName(), sale.getBarcode(), sale.getPackageUnit(), sale.getPackaging(),
					sale.getQuantityUnitCode(), sale.getQuantity(), sale.getUnitPrice(), sale.getUnitPrice(),
					sale.getDiscoutRate(), sale.getDiscountAmount(), sale.getTaxableAmount(), sale.getTaxAmount(),
					sale.getInsuranceCode(), sale.getInsuranceName(), sale.getPremiumRate(), sale.getInsuranceAmount(),
					sale.getTaxLabel(), sale.getTotalAmount());

			TrnsSalesSaveWrItem rraItem = (TrnsSalesSaveWrItem) itemMap.get(CoreUIStatiValue.RRA_SALE);
			EbmSaleItem ebmItem = (EbmSaleItem) itemMap.get(CoreUIStatiValue.EBM_SALE);
			rraItemList.add(rraItem);
			ebmItem.setEbmInvoice(ebmInvoice);
			ebmItemList.add(ebmItem);
		}

		Map<String, Object> rraReceiptMap = parseRRAReceipt(mrc, null, null, null, null, null, null, registeredName,
				registeredAddress, vcisInvoiceDto.getTopMessage(), vcisInvoiceDto.getBottomMessage(), "N",
				vcisInvoiceDto.getClientPhone(), vcisInvoiceDto.getClientTin());
		Map<String, Object> ebmReceiptMap = parseEbmReceipt(mrc, null, null, null, null, null, null, registeredName,
				registeredAddress, vcisInvoiceDto.getTopMessage(), vcisInvoiceDto.getBottomMessage(), "N",
				vcisInvoiceDto.getClientPhone(), vcisInvoiceDto.getClientTin());

		TrnsSalesSaveWrReceipt rraReceipt = (TrnsSalesSaveWrReceipt) rraReceiptMap.get(CoreUIStatiValue.RRA_RECEIPT);
		EbmReceipt ebmReceipt = (EbmReceipt) ebmReceiptMap.get(CoreUIStatiValue.EBM_RECEIPT);
		ebmInvoice.setSaleItems(ebmItemList);
		ebmReceipt.setEbmInvoice(ebmInvoice);
		ebmInvoice.setEbmReceipt(ebmReceipt);
		String journal = JournalFormatter.generateJournal(ebmInvoice);
		ebmReceipt.setJournal(journal);
		rraReceipt.setJrnl(journal);
		rraInvoice.setItemList(rraItemList);
		rraInvoice.setReceipt(rraReceipt);
		log.debug(journal);
		Map<String, Object> result = new HashMap<>();
		result.put(CoreUIStatiValue.RRA_INVOICE, rraInvoice);
		result.put(CoreUIStatiValue.EBM_INVOICE, ebmInvoice);
		return result;

	}

	public static Map<String, Object> parseCisInvoice(String registeredTin, String branchId, Long invoiceNumber,
			Long orginalInvoice, String customerTin, String customerName, String receiptType, String transactionType,
			String paymentCode, String invoiceStatus, String validatedDate, String cisDate, String stockReleasedDate,
			String cancelReqeuestedDate, String canceledDate, String refundedReasonCode, int itemNumber,
			BigDecimal taxableAmountA, BigDecimal taxableAmountB, BigDecimal taxableAmountC, BigDecimal taxableAmountD,
			BigDecimal taxRateA, BigDecimal taxRateB, BigDecimal taxRateC, BigDecimal taxRateD, BigDecimal taxAmountA,
			BigDecimal taxAmountB, BigDecimal taxAmountC, BigDecimal taxAmountD, BigDecimal totalTaxAmount,
			BigDecimal totalAmount, String purchaseAccept, String remark, String userId, String registrationName,
			String registrationId, String registrationAddress) {

		TrnsSalesSaveWrReq trnsSalesSaveWrReq = new TrnsSalesSaveWrReq();
		trnsSalesSaveWrReq.setTin(registeredTin);
		trnsSalesSaveWrReq.setBhfId(branchId);
		trnsSalesSaveWrReq.setInvcNo(invoiceNumber);
		trnsSalesSaveWrReq.setOrgInvcNo(orginalInvoice);
		trnsSalesSaveWrReq.setCustTin(customerTin);
		trnsSalesSaveWrReq.setCustNm(customerName);
		trnsSalesSaveWrReq.setSalesTyCd(receiptType);
		trnsSalesSaveWrReq.setRcptTyCd(transactionType);
		trnsSalesSaveWrReq.setPmtTyCd(paymentCode);
		trnsSalesSaveWrReq.setSalesSttsCd(invoiceStatus);
		trnsSalesSaveWrReq.setCfmDt(parseCisStringDateToVsdcStringDateTime(cisDate));
		trnsSalesSaveWrReq.setSalesDt(parseCisStringDateToVsdcStringDate(cisDate)); // CIS
		trnsSalesSaveWrReq.setStockRlsDt(parseCisStringDateToVsdcStringDateTime(cisDate));
		trnsSalesSaveWrReq.setRfdDt(parseCisStringDateToVsdcStringDateTime(cisDate));
		trnsSalesSaveWrReq.setCnclReqDt(parseCisStringDateToVsdcStringDateTime(cisDate));
		trnsSalesSaveWrReq.setCnclDt(parseCisStringDateToVsdcStringDateTime(cisDate));
		trnsSalesSaveWrReq.setRfdRsnCd(refundedReasonCode);
		trnsSalesSaveWrReq.setTotItemCnt(itemNumber);
		trnsSalesSaveWrReq.setTaxblAmtA(taxableAmountA);
		trnsSalesSaveWrReq.setTaxblAmtB(taxableAmountB);
		trnsSalesSaveWrReq.setTaxblAmtC(taxableAmountC);
		trnsSalesSaveWrReq.setTaxblAmtD(taxableAmountD);
		trnsSalesSaveWrReq.setTaxRtA(taxRateA);
		trnsSalesSaveWrReq.setTaxRtB(taxRateB);
		trnsSalesSaveWrReq.setTaxRtC(taxRateC);
		trnsSalesSaveWrReq.setTaxRtD(taxRateD);
		trnsSalesSaveWrReq.setTaxAmtA(taxAmountA);
		trnsSalesSaveWrReq.setTaxAmtB(taxAmountB);
		trnsSalesSaveWrReq.setTaxAmtC(taxAmountC);
		trnsSalesSaveWrReq.setTaxAmtD(taxAmountD);
		trnsSalesSaveWrReq.setTotTaxAmt(totalTaxAmount);
		trnsSalesSaveWrReq.setTotTaxblAmt(taxAmountA.add(taxableAmountB).add(taxableAmountC).add(taxableAmountD));
		trnsSalesSaveWrReq.setTotAmt(totalAmount);
		trnsSalesSaveWrReq.setPrchrAcptcYn(purchaseAccept);
		trnsSalesSaveWrReq.setRemark(remark);
		trnsSalesSaveWrReq.setRegrId(userId);
		trnsSalesSaveWrReq.setRegrNm(registrationName);
		trnsSalesSaveWrReq.setModrId(registrationId);
		trnsSalesSaveWrReq.setModrNm(registrationName);
		EbmInvoice ebmInvoice = EbmInvoice.builder().registeredTin(registeredTin).branchId(branchId)
				.invoiceNumber(invoiceNumber).orginalInvoice(orginalInvoice).customerTin(customerTin)
				.customerName(customerName).receiptType(receiptType).transactionType(transactionType)
				.paymentCode(paymentCode).invoiceStatus(invoiceStatus)
				.validatedDate(parseApiStringDateToLocalDate(validatedDate))
				.cisDate(parseApiStringDateToLocalDateTime(cisDate))
				.stockReleasedDate(parseApiStringDateToLocalDate(stockReleasedDate))
				.cancelReqeuestedDate(parseApiStringDateToLocalDate(cancelReqeuestedDate))
				.canceledDate(parseApiStringDateToLocalDate(canceledDate)).refundedReasonCode(refundedReasonCode)
				.itemNumber(itemNumber).taxableAmountA(taxableAmountA).taxableAmountB(taxableAmountB)
				.taxableAmountC(taxableAmountC).taxableAmountD(taxableAmountD).taxRateA(taxRateA).taxRateB(taxRateB)
				.taxRateC(taxRateC).taxRateD(taxRateD).taxAmountA(taxAmountA).taxAmountB(taxAmountB)
				.taxAmountC(taxAmountC).taxAmountD(taxAmountD).totalTaxAmount(totalTaxAmount).totalAmount(totalAmount)
				.purchaseAccept(purchaseAccept).remark(remark).userId(userId).registrationName(registrationName)
				.registrationId(registrationId).registrationAddress(registrationAddress).build();
		log.debug(String.format("cisDate:%s", cisDate));
		log.debug(String.format("parseApiStringDateToLocalDateTime:%s", parseApiStringDateToLocalDateTime(cisDate)));
		Map<String, Object> result = new HashMap<>();
		result.put(CoreUIStatiValue.RRA_INVOICE, trnsSalesSaveWrReq);
		result.put(CoreUIStatiValue.EBM_INVOICE, ebmInvoice);
		return result;
	}

	public static Map<String, Object> parseSaleItem(String itemCode, String itemClassificationCode, String itemName,
			String barcode, String packageUnit, BigDecimal packaging, String quantityUnitCode, BigDecimal quantity,
			BigDecimal saleUnitPrice, BigDecimal purcaseUnitPrice, BigDecimal discountRate, BigDecimal discountAmount,
			BigDecimal taxableAmount, BigDecimal taxAmount, String insuranceCode, String insuranceName,
			BigDecimal premiumRate, BigDecimal insuranceAmount, String taxLabel, BigDecimal totalAmount) {
		TrnsSalesSaveWrItem trnsSalesSaveWrItem = new TrnsSalesSaveWrItem();
		trnsSalesSaveWrItem.setItemSeq(1);
		trnsSalesSaveWrItem.setItemCd("RW1NTXU0000002");
		trnsSalesSaveWrItem.setItemClsCd(itemClassificationCode);
		trnsSalesSaveWrItem.setItemNm(itemName);
		trnsSalesSaveWrItem.setBcd(barcode);
		trnsSalesSaveWrItem.setPkgUnitCd(packageUnit);
		trnsSalesSaveWrItem.setPkg(packaging);
		trnsSalesSaveWrItem.setQtyUnitCd(quantityUnitCode);
		trnsSalesSaveWrItem.setQty(quantity);
		trnsSalesSaveWrItem.setPrc(saleUnitPrice);
		trnsSalesSaveWrItem.setSplyAmt(purcaseUnitPrice);
		trnsSalesSaveWrItem.setDcRt(discountRate);
		trnsSalesSaveWrItem.setDcAmt(discountAmount);
		trnsSalesSaveWrItem.setTaxblAmt(taxableAmount);
		trnsSalesSaveWrItem.setTaxAmt(taxAmount);
		trnsSalesSaveWrItem.setIsrccCd(insuranceCode);
		trnsSalesSaveWrItem.setIsrccNm(insuranceName);
		trnsSalesSaveWrItem.setIsrcRt(premiumRate);
		trnsSalesSaveWrItem.setIsrcAmt(insuranceAmount);
		trnsSalesSaveWrItem.setTaxTyCd("B");
		trnsSalesSaveWrItem.setTotAmt(totalAmount);
		EbmSaleItem saleItem = EbmSaleItem.builder().itemCode(itemCode).itemClassificationCode(itemClassificationCode)
				.itemName(itemName).barcode(barcode).packageUnit(packageUnit).packaging(packaging)
				.quantityUnitCode(quantityUnitCode).quantity(quantity).saleUnitPrice(saleUnitPrice)
				.purchaseUnitPrice(purcaseUnitPrice).discountRate(discountRate).discountAmount(discountAmount)
				.taxableAmount(taxableAmount).taxAmount(taxAmount).insuranceCode(insuranceCode)
				.insuranceName(insuranceName).premiumRate(premiumRate).insuranceAmount(insuranceAmount)
				.taxLabel(taxLabel).totalAmount(totalAmount).build();
		Map<String, Object> result = new HashMap<>();
		result.put(CoreUIStatiValue.RRA_SALE, trnsSalesSaveWrItem);
		result.put(CoreUIStatiValue.EBM_SALE, saleItem);
		return result;
	}

	public static Map<String, Object> parseEbmReceipt(String mrc, Long vsdcReceiptNumber, Long vsdcTotalReceiptNumber,
			String vsdcDate, String vsdcInternalData, String vsdcSignature, String journal, String tradeName,
			String tradeAddress, String topMessage, String bottomMessage, String receivedReceipt, String customerPhone,
			String customerTin) {

		Map<String, Object> result = new HashMap<>();
		EbmReceipt receipt = EbmReceipt.builder().mrc(mrc).vsdcReceiptNumber(vsdcReceiptNumber)
				.vsdcTotalReceiptNumber(vsdcTotalReceiptNumber).vsdcDate(parseApiStringDateToLocalDateTime(vsdcDate))
				.vsdcInternalData(vsdcInternalData).vsdcSignature(vsdcSignature).journal(journal).tradeName(tradeName)
				.tradeAddress(tradeAddress).topMessage(topMessage).bottomMessage(bottomMessage)
				.receivedReceipt(receivedReceipt).customerPhone(customerPhone).customerTin(customerTin).build();

		result.put(CoreUIStatiValue.EBM_RECEIPT, receipt);
		return result;
	}

	public static Map<String, Object> parseRRAReceipt(String mrc, Long vsdcReceiptNumber, Long vsdcTotalReceiptNumber,
			String vsdcDate, String vsdcInternalData, String vsdcSignature, String journal, String tradeName,
			String tradeAddress, String topMessage, String bottomMessage, String receivedReceipt, String customerPhone,
			String customerTin) {

		TrnsSalesSaveWrReceipt trnsSalesSaveWrReceipt = new TrnsSalesSaveWrReceipt();
		trnsSalesSaveWrReceipt.setRptNo(vsdcReceiptNumber);
		trnsSalesSaveWrReceipt.setTotRcptNo(vsdcTotalReceiptNumber);
		trnsSalesSaveWrReceipt.setRcptPbctDt(vsdcDate);// VSDC generated
		trnsSalesSaveWrReceipt.setIntrlData(vsdcInternalData);
		trnsSalesSaveWrReceipt.setRcptSign(vsdcSignature);
		trnsSalesSaveWrReceipt.setJrnl(journal);
		trnsSalesSaveWrReceipt.setTrdeNm(tradeName);
		trnsSalesSaveWrReceipt.setAdrs(tradeAddress);
		trnsSalesSaveWrReceipt.setTopMsg(topMessage);
		trnsSalesSaveWrReceipt.setBtmMsg(bottomMessage);
		trnsSalesSaveWrReceipt.setPrchrAcptcYn(receivedReceipt);
		trnsSalesSaveWrReceipt.setCustMblNo(customerPhone);
		trnsSalesSaveWrReceipt.setCustTin(customerTin);
		Map<String, Object> result = new HashMap<>();
		result.put(CoreUIStatiValue.RRA_RECEIPT, trnsSalesSaveWrReceipt);
		return result;
	}

	public static EbmInvoice updateEbmInvoiceWithVsdcResponse(EbmInvoice ebmInvoice, TrnsSalesSaveWrResData data) {
		EbmReceipt receipt = ebmInvoice.getEbmReceipt();
		receipt.setSdcId(data.getSdcId());
		receipt.setVsdcDate(parseVsdcStringDateToLocalDateTime(data.getVsdcRcptPbctDate()));
		receipt.setVsdcReceiptNumber(data.getRcptNo());
		receipt.setVsdcTotalReceiptNumber(data.getTotRcptNo());
		receipt.setVsdcInternalData(data.getIntrlData());
		receipt.setVsdcSignature(data.getRcptSign());
		ebmInvoice.setEbmReceipt(receipt);
		String journal = JournalFormatter.generateJournal(ebmInvoice);
		receipt.setJournal(journal);
		ebmInvoice.setEbmReceipt(receipt);
		return ebmInvoice;
	}

	public static LocalDate parseApiStringDateToLocalDate(String date) {
		if (date == null || date.isEmpty())
			return LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

	public static LocalDateTime parseApiStringDateToLocalDateTime(String date) {
		if (date == null || date.isEmpty())
			return LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.parse(date, formatter);
		return localDate;
	}

	public static LocalDate parseVsdcStringDateToLocalDate(String date) {
		if (date == null || date.isEmpty())
			return LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDate localDate = LocalDate.parse(date, formatter);
		return localDate;
	}

	public static LocalDateTime parseVsdcStringDateToLocalDateTime(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		if (date == null || date.isEmpty()) {
			LocalDateTime now = LocalDateTime.now();
			date = now.format(formatter);
		}
		LocalDateTime localDate = LocalDateTime.parse(date, formatter);
		return localDate;
	}

	public static String parseCisStringDateToVsdcStringDate(String date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd", Locale.US);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDate localDate = null;
		if (date == null || date.isEmpty()) {
			localDate = LocalDate.now();
		} else {
			localDate = LocalDate.parse(date, formatter);
		}
		String dateFormat = dtf.format(localDate);
		return dateFormat;
	}

	public static String parseCisStringDateToVsdcStringDateTime(String date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.US);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime localDate = null;
		if (date == null || date.isEmpty()) {
			localDate = LocalDateTime.now();
		} else {
			localDate = LocalDateTime.parse(date, formatter);
		}
		String dateFormat = dtf.format(localDate);
		return dateFormat;
	}

	public static String parseVsdcStringDateToVsdcStringDateTime(String date) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime localDate = null;
		if (date == null || date.isEmpty()) {
			localDate = LocalDateTime.now();
		} else {
			localDate = LocalDateTime.parse(date, formatter);
		}
		String dateFormat = dtf.format(localDate);
		return dateFormat;
	}

	public static VCISSaleDto parseFromSaleCart(EbmItem item, BigDecimal quantity, int seq) {
		BigDecimal totalAmount = new BigDecimal("0.00");
		totalAmount = quantity.multiply(item.getDefaultUnitPrice());
		VCISSaleDto sale = new VCISSaleDto();
		sale.setBarcode(item.getBarcode());
		sale.setDiscountAmount(new BigDecimal("0.00"));
		sale.setDiscoutRate(new BigDecimal("0.00"));
		sale.setInsuranceAmount(new BigDecimal("0.00"));
		sale.setInsuranceCode("0.00");
		sale.setInsuranceName("0.00");
		sale.setItemSeq(seq);
		sale.setPackageUnit(item.getPackagingUnitCode());
		sale.setPackaging(null);
		sale.setPremiumRate(new BigDecimal("0.00"));
		sale.setProductCode(item.getItemCode());
		sale.setProductName(item.getItemName());
		sale.setQuantity(quantity);
		sale.setQuantityUnitCode(item.getQuantityUnitCode());
		sale.setTaxableAmount(totalAmount);
		sale.setTaxAmount(new BigDecimal("0.00"));
		sale.setTaxLabel(item.getTaxationTypeCode());
		sale.setTotalAmount(totalAmount);
		sale.setUnitPrice(item.getDefaultUnitPrice());
		sale.setUnspcCode(item.getItemClassificationCode());
		return sale;

	}

	public static EbmPurchase parseCisPurchase(String registeredTin, String branchId, Long invoiceNumber,
			Long orginalInvoice, String customerTin, String customerName, String receiptType, String transactionType,
			String paymentCode, String invoiceStatus, String validatedDate, String cisDate, String stockReleasedDate,
			String cancelReqeuestedDate, String canceledDate, String refundedReasonCode, int itemNumber,
			BigDecimal taxableAmountA, BigDecimal taxableAmountB, BigDecimal taxableAmountC, BigDecimal taxableAmountD,
			BigDecimal taxRateA, BigDecimal taxRateB, BigDecimal taxRateC, BigDecimal taxRateD, BigDecimal taxAmountA,
			BigDecimal taxAmountB, BigDecimal taxAmountC, BigDecimal taxAmountD, BigDecimal totalTaxAmount,
			BigDecimal totalAmount, String purchaseAccept, String remark, String userId, String registrationName,
			String registrationId, String registrationAddress, String supplierBranchId) {

		EbmPurchase ebmPurchase = EbmPurchase.builder().registeredTin(registeredTin).branchId(branchId)
				.invoiceNumber(invoiceNumber).orginalInvoice(orginalInvoice).supplierTin(customerTin)
				.supplierName(customerName).receiptType(receiptType).transactionType(transactionType)
				.paymentCode(paymentCode).validatedDate(parseApiStringDateToLocalDate(validatedDate))
				.cisDate(parseApiStringDateToLocalDateTime(cisDate))
				.stockReleasedDate(parseApiStringDateToLocalDate(stockReleasedDate))
				.cancelReqeuestedDate(parseApiStringDateToLocalDate(cancelReqeuestedDate))
				.canceledDate(parseApiStringDateToLocalDate(canceledDate)).refundedReasonCode(refundedReasonCode)
				.itemNumber(itemNumber).taxableAmountA(taxableAmountA).taxableAmountB(taxableAmountB)
				.taxableAmountC(taxableAmountC).taxableAmountD(taxableAmountD).taxRateA(taxRateA).taxRateB(taxRateB)
				.taxRateC(taxRateC).taxRateD(taxRateD).taxAmountA(taxAmountA).taxAmountB(taxAmountB)
				.taxAmountC(taxAmountC).taxAmountD(taxAmountD).totalTaxAmount(totalTaxAmount).totalAmount(totalAmount)
				.totalTaxableAmount(totalAmount).purchaseAccept(purchaseAccept).remark(remark).userId(userId)
				.registrationName(registrationName).registrationId(registrationId)
				.registrationAddress(registrationAddress).sentServer(false)
				.registrationTypeCode(Ebm2StaticValue.MANUAL_REGISTRATION_TYPE_CODE).supplierBranch(supplierBranchId)
				.build();

		return ebmPurchase;
	}

	public static EbmPurchaseItem parsePurchaseItem(String itemCode, String itemClassificationCode, String itemName,
			String barcode, String packageUnit, BigDecimal packaging, String quantityUnitCode, BigDecimal quantity,
			BigDecimal saleUnitPrice, BigDecimal purcaseUnitPrice, BigDecimal discountRate, BigDecimal discountAmount,
			BigDecimal taxableAmount, BigDecimal taxAmount, String insuranceCode, String insuranceName,
			BigDecimal premiumRate, BigDecimal insuranceAmount, String taxLabel, BigDecimal totalAmount) {

		EbmPurchaseItem purchaseItem = EbmPurchaseItem.builder().itemCode(itemCode)
				.itemClassificationCode(itemClassificationCode).itemName(itemName).barcode(barcode)
				.packageUnit(packageUnit).packaging(packaging).quantityUnitCode(quantityUnitCode).quantity(quantity)
				.saleUnitPrice(saleUnitPrice).purchaseUnitPrice(purcaseUnitPrice).discountRate(discountRate)
				.discountAmount(discountAmount).taxableAmount(taxableAmount).taxAmount(taxAmount)
				.insuranceCode(insuranceCode).insuranceName(insuranceName).premiumRate(premiumRate)
				.insuranceAmount(insuranceAmount).taxLabel(taxLabel).totalAmount(totalAmount).build();

		return purchaseItem;
	}

	public static EbmPurchase generatePurchaseDataFromVCISPurchaseDto(VCISPurchaseDto vcisPurchaseDto,
			String registeredTin, String registeredBranch, String registeredName, String registeredAddress,
			String mrc) {
		EbmPurchase ebmPurchase = parseCisPurchase(vcisPurchaseDto.getRegisteredTin(), registeredBranch,
				Long.valueOf(vcisPurchaseDto.getInvoiceNumber()),
				vcisPurchaseDto.getTransactionType().equalsIgnoreCase("R") ? new Long(vcisPurchaseDto.getRefundRef())
						: Long.valueOf("0"),
				vcisPurchaseDto.getSupplierTin(), vcisPurchaseDto.getSupplierName(), vcisPurchaseDto.getInvoiceType(),
				vcisPurchaseDto.getTransactionType(), vcisPurchaseDto.getPaymentCode(),
				vcisPurchaseDto.getInvoiceStatus(), vcisPurchaseDto.getValidatedDate(), vcisPurchaseDto.getCisDate(),
				vcisPurchaseDto.getStockReleasedDate(), vcisPurchaseDto.getCancelReqeuestedDate(),
				vcisPurchaseDto.getCanceledDate(), vcisPurchaseDto.getRefundedReasonCode(),
				vcisPurchaseDto.getPurchases().size(), vcisPurchaseDto.getTaxableAmountA(),
				vcisPurchaseDto.getTaxableAmountB(), vcisPurchaseDto.getTaxableAmountC(),
				vcisPurchaseDto.getTaxableAmountD(), vcisPurchaseDto.getTaxRateA(), vcisPurchaseDto.getTaxRateB(),
				vcisPurchaseDto.getTaxRateC(), vcisPurchaseDto.getTaxRateD(), vcisPurchaseDto.getTaxAmountA(),
				vcisPurchaseDto.getTaxAmountB(), vcisPurchaseDto.getTaxAmountC(), vcisPurchaseDto.getTaxAmountD(),
				vcisPurchaseDto.getTotalTaxAmount(), vcisPurchaseDto.getTotalAmount(),
				vcisPurchaseDto.getPurchaseAccept(), vcisPurchaseDto.getRemark(), vcisPurchaseDto.getUserId(),
				registeredName, registeredTin, registeredAddress, vcisPurchaseDto.getSupplierBranchId());

		List<EbmPurchaseItem> ebmItemList = new ArrayList<>();
		for (VCISPurchaseItemDto sale : vcisPurchaseDto.getPurchases()) {
			EbmPurchaseItem ebmItem = parsePurchaseItem(sale.getProductCode(), sale.getUnspcCode(),
					sale.getProductName(), sale.getBarcode(), sale.getPackageUnit(), sale.getPackaging(),
					sale.getQuantityUnitCode(), sale.getQuantity(), sale.getUnitPrice(), sale.getUnitPrice(),
					sale.getDiscoutRate(), sale.getDiscountAmount(), sale.getTaxableAmount(), sale.getTaxAmount(),
					sale.getInsuranceCode(), sale.getInsuranceName(), sale.getPremiumRate(), sale.getInsuranceAmount(),
					sale.getTaxLabel(), sale.getTotalAmount());
			ebmItem.setEbmPurchase(ebmPurchase);
			ebmItemList.add(ebmItem);
		}
		ebmPurchase.setPurchaseItems(ebmItemList);
		return ebmPurchase;

	}
}
