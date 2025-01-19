/*
 * package rw.mangatek.ebm2.rra.model;
 * 
 * import java.math.BigDecimal; import java.util.List;
 * 
 * import ebm.vsdc.excute.model.TrnsSalesSaveWrItem; import
 * ebm.vsdc.excute.model.TrnsSalesSaveWrReceipt; import
 * ebm.vsdc.excute.model.TrnsSalesSaveWrReq;
 * 
 * public class EbmTrnsSalesSaveWrReq {
 * 
 * public static TrnsSalesSaveWrReq parseCisInvoice(String registeredTin, String
 * branchId, Long invoiceNumber, Long orginalInvoice, String customerTin, String
 * customerName, String receiptType, String transactionType, String paymentCode,
 * String invoiceStatus, String validatedDate, String cisDate, String
 * stockReleasedDate, String cancelReqeuestedDate, String canceledDate, String
 * refundedReasonCode, int itemNumber, BigDecimal taxableAmountA, BigDecimal
 * taxableAmountB, BigDecimal taxableAmountC, BigDecimal taxableAmountD,
 * BigDecimal taxRateA, BigDecimal taxRateB, BigDecimal taxRateC, BigDecimal
 * taxRateD, BigDecimal taxAmountA, BigDecimal taxAmountB, BigDecimal
 * taxAmountC, BigDecimal taxAmountD, BigDecimal totalTaxAmount, BigDecimal
 * totalAmount, String purchaseAccept, String remark, String userId, String
 * registrationName, String registrationId, String registrationAddress,
 * List<TrnsSalesSaveWrItem> itemList, TrnsSalesSaveWrReceipt receipt) {
 * TrnsSalesSaveWrReq trnsSalesSaveWrReq = new TrnsSalesSaveWrReq();
 * trnsSalesSaveWrReq.setTin(registeredTin);
 * trnsSalesSaveWrReq.setBhfId(branchId);
 * trnsSalesSaveWrReq.setInvcNo(invoiceNumber);
 * trnsSalesSaveWrReq.setOrgInvcNo(orginalInvoice);
 * trnsSalesSaveWrReq.setCustTin(customerTin);
 * trnsSalesSaveWrReq.setCustNm(customerName);
 * trnsSalesSaveWrReq.setSalesTyCd(receiptType);
 * trnsSalesSaveWrReq.setRcptTyCd(transactionType);
 * trnsSalesSaveWrReq.setPmtTyCd(paymentCode);
 * trnsSalesSaveWrReq.setSalesSttsCd(invoiceStatus);
 * trnsSalesSaveWrReq.setCfmDt(validatedDate);
 * trnsSalesSaveWrReq.setSalesDt(cisDate); // CIS
 * trnsSalesSaveWrReq.setStockRlsDt(stockReleasedDate);
 * trnsSalesSaveWrReq.setCnclReqDt(cancelReqeuestedDate);
 * trnsSalesSaveWrReq.setCnclDt(canceledDate);
 * trnsSalesSaveWrReq.setRfdRsnCd(refundedReasonCode);
 * trnsSalesSaveWrReq.setTotItemCnt(itemNumber);
 * trnsSalesSaveWrReq.setTaxblAmtA(taxableAmountA);
 * trnsSalesSaveWrReq.setTaxblAmtB(taxableAmountB);
 * trnsSalesSaveWrReq.setTaxblAmtC(taxableAmountC);
 * trnsSalesSaveWrReq.setTaxblAmtD(taxableAmountD);
 * trnsSalesSaveWrReq.setTaxRtA(taxRateA);
 * trnsSalesSaveWrReq.setTaxRtB(taxRateB);
 * trnsSalesSaveWrReq.setTaxRtC(taxRateC);
 * trnsSalesSaveWrReq.setTaxRtD(taxRateD);
 * trnsSalesSaveWrReq.setTaxAmtA(taxAmountA);
 * trnsSalesSaveWrReq.setTaxAmtB(taxAmountB);
 * trnsSalesSaveWrReq.setTaxAmtC(taxAmountC);
 * trnsSalesSaveWrReq.setTaxAmtD(taxAmountD);
 * trnsSalesSaveWrReq.setTotTaxAmt(totalTaxAmount);
 * trnsSalesSaveWrReq.setTotAmt(totalAmount);
 * trnsSalesSaveWrReq.setPrchrAcptcYn(purchaseAccept);
 * trnsSalesSaveWrReq.setRemark(remark); trnsSalesSaveWrReq.setRegrId(userId);
 * trnsSalesSaveWrReq.setRegrNm(registrationName);
 * trnsSalesSaveWrReq.setModrId(registrationId);
 * trnsSalesSaveWrReq.setModrNm(registrationAddress);
 * trnsSalesSaveWrReq.setItemList(itemList);
 * trnsSalesSaveWrReq.setReceipt(receipt); return trnsSalesSaveWrReq; }
 * 
 * public static TrnsSalesSaveWrItem parseSaleItem(String itemCode, String
 * itemClassificationCode, String itemName, String barcode, String packageUnit,
 * BigDecimal packaging, String quantityUnitCode, BigDecimal quantity,
 * BigDecimal saleUnitPrice, BigDecimal purcaseUnitPrice, BigDecimal
 * dicountRate, BigDecimal discountAmount, BigDecimal taxableAmount, BigDecimal
 * taxAmount, String insuranceCode, String insuranceName, BigDecimal
 * premiumRate, BigDecimal insuranceAmount, String taxLabel, BigDecimal
 * totalAmount) { TrnsSalesSaveWrItem trnsSalesSaveWrItem = new
 * TrnsSalesSaveWrItem(); trnsSalesSaveWrItem.setItemSeq(1);
 * trnsSalesSaveWrItem.setItemCd(itemCode);
 * trnsSalesSaveWrItem.setItemClsCd(itemClassificationCode);
 * trnsSalesSaveWrItem.setItemNm(itemName); trnsSalesSaveWrItem.setBcd(barcode);
 * trnsSalesSaveWrItem.setPkgUnitCd(packageUnit);
 * trnsSalesSaveWrItem.setPkg(packaging);
 * trnsSalesSaveWrItem.setQtyUnitCd(quantityUnitCode);
 * trnsSalesSaveWrItem.setQty(quantity);
 * trnsSalesSaveWrItem.setPrc(saleUnitPrice);
 * trnsSalesSaveWrItem.setSplyAmt(purcaseUnitPrice);
 * trnsSalesSaveWrItem.setDcRt(dicountRate);
 * trnsSalesSaveWrItem.setDcAmt(discountAmount);
 * trnsSalesSaveWrItem.setTaxblAmt(taxableAmount);
 * trnsSalesSaveWrItem.setTaxAmt(taxAmount);
 * trnsSalesSaveWrItem.setIsrccCd(insuranceCode);
 * trnsSalesSaveWrItem.setIsrccNm(insuranceName);
 * trnsSalesSaveWrItem.setIsrcRt(premiumRate);
 * trnsSalesSaveWrItem.setIsrcAmt(insuranceAmount);
 * trnsSalesSaveWrItem.setTaxTyCd(taxLabel);
 * trnsSalesSaveWrItem.setTotAmt(totalAmount); return trnsSalesSaveWrItem; }
 * 
 * public static TrnsSalesSaveWrReceipt parseReceipt(String vsdcDate, String
 * vsdcInternalData, String vsdcSignature, String journal, String tradeName,
 * String tradeAddress, String topMessage, String bottomMessage, String
 * receivedReceipt, String customerPhone, String customerTin) {
 * 
 * TrnsSalesSaveWrReceipt trnsSalesSaveWrReceipt = new TrnsSalesSaveWrReceipt();
 * trnsSalesSaveWrReceipt.setRcptPbctDt(vsdcDate);// VSDC generated
 * trnsSalesSaveWrReceipt.setIntrlData(vsdcInternalData);
 * trnsSalesSaveWrReceipt.setRcptSign(vsdcSignature);
 * trnsSalesSaveWrReceipt.setJrnl(journal);
 * trnsSalesSaveWrReceipt.setTrdeNm(tradeName);
 * trnsSalesSaveWrReceipt.setAdrs(tradeAddress);
 * trnsSalesSaveWrReceipt.setTopMsg(topMessage);
 * trnsSalesSaveWrReceipt.setBtmMsg(bottomMessage);
 * trnsSalesSaveWrReceipt.setPrchrAcptcYn(receivedReceipt);
 * trnsSalesSaveWrReceipt.setCustMblNo(customerPhone);
 * trnsSalesSaveWrReceipt.setCustTin(customerTin); return
 * trnsSalesSaveWrReceipt; }
 * 
 * public static TrnsSalesSaveWrReq finalizeInvoiceData(TrnsSalesSaveWrReq
 * trnsSalesSaveWrReq, List<TrnsSalesSaveWrItem> itemList,
 * TrnsSalesSaveWrReceipt receipt) { trnsSalesSaveWrReq.setItemList(itemList);
 * trnsSalesSaveWrReq.setReceipt(receipt); return trnsSalesSaveWrReq; }
 * 
 * }
 */