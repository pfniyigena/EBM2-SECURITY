/*************************************************************************************************
 * Copyright (c) Mangatek Ltd(Owner) 2020 to Present. All Rights Reserved.
 * No part of this file may be reproduced or distributed in any form or by any means, 
 * or stored in a database or retrieval system, without the prior written permission of the owner.
 *************************************************************************************************/
package rw.mangatek.ebm2.core.ui.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CisInvoiceDto")
public class VCISInvoiceDto {

	// Owner
	@XmlAttribute(required = true)
	private String sdcId;
	@XmlAttribute(required = true)
	private String mrc;
	@XmlAttribute(required = true)
	private String registeredTin;
	// Client

	private String clientTin;

	private String clientName;

	private String clientPhone;

	private String clientAddress;
	// SDC
	@XmlAttribute(required = true)
	private Integer invoiceNumber;
	@XmlAttribute(required = true)
	private String invoiceType;
	@XmlAttribute(required = true)
	private String transactionType;
	@XmlAttribute(required = true)
	private BigDecimal taxableAmountA;
	@XmlAttribute(required = true)
	private BigDecimal taxableAmountB;
	@XmlAttribute(required = true)
	private BigDecimal taxableAmountC;
	@XmlAttribute(required = true)
	private BigDecimal taxableAmountD;
	@XmlAttribute(required = true)
	private BigDecimal taxRateA;
	@XmlAttribute(required = true)
	private BigDecimal taxRateB;
	@XmlAttribute(required = true)
	private BigDecimal taxRateC;
	@XmlAttribute(required = true)
	private BigDecimal taxRateD;
	@XmlAttribute(required = true)
	private BigDecimal taxAmountA;
	@XmlAttribute(required = true)
	private BigDecimal taxAmountB;
	@XmlAttribute(required = true)
	private BigDecimal taxAmountC;
	@XmlAttribute(required = true)
	private BigDecimal taxAmountD;
	@XmlAttribute(required = true)
	private BigDecimal totalAmount;
	@XmlAttribute(required = true)
	private BigDecimal totalTaxAmount;
	@XmlAttribute(required = true)
	private BigDecimal totalDiscountAmount;
	@XmlAttribute(required = true)
	private String cisDate;
	private Integer refundRef;
	private String journal;
	@XmlAttribute(required = true)
	private List<VCISSaleDto> sales;
	private String operator;
	private String branchCode;
	private String paymentCode;
	private String invoiceStatusCode;
	private String validatedDate;
	private String stockReleasedDate;
	private String cancelReqeuestedDate;
	private String canceledDate;
	private String refundedReasonCode;

	private String purchaseAccept;

	private String remark;

	private String userId;

	private String topMessage;

	private String bottomMessage;

	public VCISInvoiceDto() {
	}

	/**
	 * @return the sdcId
	 */
	public String getSdcId() {
		return Objects.isNull(sdcId) ? "" : sdcId;

	}

	/**
	 * @param sdcId the sdcId to set
	 */
	public void setSdcId(String sdcId) {
		this.sdcId = sdcId;
	}

	/**
	 * @return the mrc
	 */
	public String getMrc() {
		return Objects.isNull(mrc) ? "" : mrc;

	}

	/**
	 * @param mrc the mrc to set
	 */
	public void setMrc(String mrc) {
		this.mrc = mrc;
	}

	/**
	 * @return the registeredTin
	 */
	public String getRegisteredTin() {
		return Objects.isNull(registeredTin) ? "" : registeredTin;

	}

	/**
	 * @param registeredTin the registeredTin to set
	 */
	public void setRegisteredTin(String registeredTin) {
		this.registeredTin = registeredTin;
	}

	/**
	 * @return the clientTin
	 */
	public String getClientTin() {
		return Objects.isNull(clientTin) ? "" : clientTin;

	}

	/**
	 * @param clientTin the clientTin to set
	 */
	public void setClientTin(String clientTin) {
		this.clientTin = clientTin;
	}

	/**
	 * @return the clientName
	 */
	public String getClientName() {
		return Objects.isNull(clientName) ? "" : clientName;

	}

	/**
	 * @param clientName the clientName to set
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * @return the clientPhone
	 */
	public String getClientPhone() {
		return Objects.isNull(clientPhone) ? "" : clientPhone;

	}

	/**
	 * @param clientPhone the clientPhone to set
	 */
	public void setClientPhone(String clientPhone) {
		this.clientPhone = clientPhone;
	}

	/**
	 * @return the clientAddress
	 */
	public String getClientAddress() {
		return Objects.isNull(clientAddress) ? "" : clientAddress;

	}

	/**
	 * @param clientAddress the clientAddress to set
	 */
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	/**
	 * @return the invoiceNumber
	 */
	public Integer getInvoiceNumber() {
		return Objects.isNull(invoiceNumber) ? 0 : invoiceNumber;

	}

	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the invoiceType
	 */
	public String getInvoiceType() {
		return Objects.isNull(invoiceType) ? "" : invoiceType;

	}

	/**
	 * @param invoiceType the invoiceType to set
	 */
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return Objects.isNull(transactionType) ? "" : transactionType;

	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return the taxableAmountA
	 */
	public BigDecimal getTaxableAmountA() {
		return Objects.isNull(taxableAmountA) ? new BigDecimal("0.00") : taxableAmountA;

	}

	/**
	 * @param taxableAmountA the taxableAmountA to set
	 */
	public void setTaxableAmountA(BigDecimal taxableAmountA) {
		this.taxableAmountA = taxableAmountA;
	}

	/**
	 * @return the taxableAmountB
	 */
	public BigDecimal getTaxableAmountB() {
		return Objects.isNull(taxableAmountB) ? new BigDecimal("0.00") : taxableAmountB;

	}

	/**
	 * @param taxableAmountB the taxableAmountB to set
	 */
	public void setTaxableAmountB(BigDecimal taxableAmountB) {
		this.taxableAmountB = taxableAmountB;
	}

	/**
	 * @return the taxableAmountC
	 */
	public BigDecimal getTaxableAmountC() {
		return Objects.isNull(taxableAmountC) ? new BigDecimal("0.00") : taxableAmountC;

	}

	/**
	 * @param taxableAmountC the taxableAmountC to set
	 */
	public void setTaxableAmountC(BigDecimal taxableAmountC) {
		this.taxableAmountC = taxableAmountC;
	}

	/**
	 * @return the taxableAmountD
	 */
	public BigDecimal getTaxableAmountD() {
		return Objects.isNull(taxableAmountD) ? new BigDecimal("0.00") : taxableAmountD;

	}

	/**
	 * @param taxableAmountD the taxableAmountD to set
	 */
	public void setTaxableAmountD(BigDecimal taxableAmountD) {
		this.taxableAmountD = taxableAmountD;
	}

	/**
	 * @return the taxRateA
	 */
	public BigDecimal getTaxRateA() {
		return Objects.isNull(taxRateA) ? new BigDecimal("0.00") : taxRateA;

	}

	/**
	 * @param taxRateA the taxRateA to set
	 */
	public void setTaxRateA(BigDecimal taxRateA) {
		this.taxRateA = taxRateA;
	}

	/**
	 * @return the taxRateB
	 */
	public BigDecimal getTaxRateB() {
		return Objects.isNull(taxRateB) ? new BigDecimal("0.00") : taxRateB;

	}

	/**
	 * @param taxRateB the taxRateB to set
	 */
	public void setTaxRateB(BigDecimal taxRateB) {
		this.taxRateB = taxRateB;
	}

	/**
	 * @return the taxRateC
	 */
	public BigDecimal getTaxRateC() {
		return Objects.isNull(taxRateC) ? new BigDecimal("0.00") : taxRateC;
		 
	}

	/**
	 * @param taxRateC the taxRateC to set
	 */
	public void setTaxRateC(BigDecimal taxRateC) {
		this.taxRateC = taxRateC;
	}

	/**
	 * @return the taxRateD
	 */
	public BigDecimal getTaxRateD() {
		return Objects.isNull(taxRateD) ? new BigDecimal("0.00") : taxRateD;
		 
	}

	/**
	 * @param taxRateD the taxRateD to set
	 */
	public void setTaxRateD(BigDecimal taxRateD) {
		this.taxRateD = taxRateD;
	}

	/**
	 * @return the taxAmountA
	 */
	public BigDecimal getTaxAmountA() {
		return Objects.isNull(taxAmountA) ? new BigDecimal("0.00") : taxAmountA;
		 
	}

	/**
	 * @param taxAmountA the taxAmountA to set
	 */
	public void setTaxAmountA(BigDecimal taxAmountA) {
		this.taxAmountA = taxAmountA;
	}

	/**
	 * @return the taxAmountB
	 */
	public BigDecimal getTaxAmountB() {
		return Objects.isNull(taxAmountB) ? new BigDecimal("0.00") : taxAmountB;
		 
	}

	/**
	 * @param taxAmountB the taxAmountB to set
	 */
	public void setTaxAmountB(BigDecimal taxAmountB) {
		this.taxAmountB = taxAmountB;
	}

	/**
	 * @return the taxAmountC
	 */
	public BigDecimal getTaxAmountC() {
		return Objects.isNull(taxAmountC) ? new BigDecimal("0.00") : taxAmountC;
		 
	}

	/**
	 * @param taxAmountC the taxAmountC to set
	 */
	public void setTaxAmountC(BigDecimal taxAmountC) {
		this.taxAmountC = taxAmountC;
	}

	/**
	 * @return the taxAmountD
	 */
	public BigDecimal getTaxAmountD() {
		return Objects.isNull(taxAmountD) ? new BigDecimal("0.00") : taxAmountD;
		 
	}

	/**
	 * @param taxAmountD the taxAmountD to set
	 */
	public void setTaxAmountD(BigDecimal taxAmountD) {
		this.taxAmountD = taxAmountD;
	}

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return Objects.isNull(totalAmount) ? new BigDecimal("0.00") : totalAmount;
		 
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the totalTaxAmount
	 */
	public BigDecimal getTotalTaxAmount() {
		return Objects.isNull(totalTaxAmount) ? new BigDecimal("0.00") : totalTaxAmount;
		 
	}

	/**
	 * @param totalTaxAmount the totalTaxAmount to set
	 */
	public void setTotalTaxAmount(BigDecimal totalTaxAmount) {
		this.totalTaxAmount = totalTaxAmount;
	}

	/**
	 * @return the totalDiscountAmount
	 */
	public BigDecimal getTotalDiscountAmount() {
		return Objects.isNull(totalDiscountAmount) ? new BigDecimal("0.00") : totalDiscountAmount;
		 
	}

	/**
	 * @param totalDiscountAmount the totalDiscountAmount to set
	 */
	public void setTotalDiscountAmount(BigDecimal totalDiscountAmount) {
		this.totalDiscountAmount = totalDiscountAmount;
	}

	/**
	 * @return the cisDate
	 */
	public String getCisDate() {
		return Objects.isNull(cisDate) ?  " "  : cisDate;
		 
	}

	/**
	 * @param cisDate the cisDate to set
	 */
	public void setCisDate(String cisDate) {
		this.cisDate = cisDate;
	}

	/**
	 * @return the refundRef
	 */
	public Integer getRefundRef() {
		return Objects.isNull(refundRef) ?  0  : refundRef;
		 
	}

	/**
	 * @param refundRef the refundRef to set
	 */
	public void setRefundRef(Integer refundRef) {
		this.refundRef = refundRef;
	}

	/**
	 * @return the journal
	 */
	public String getJournal() {
		return Objects.isNull(journal) ?  "INOICE\ns "  : journal;
		 
	}

	/**
	 * @param journal the journal to set
	 */
	public void setJournal(String journal) {
		this.journal = journal;
	}

	/**
	 * @return the sales
	 */
	public List<VCISSaleDto> getSales() {
		return Objects.isNull(sales) ?  new ArrayList<>()  : sales;
		 
	}

	/**
	 * @param sales the sales to set
	 */
	public void setSales(List<VCISSaleDto> sales) {
		this.sales = sales;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return Objects.isNull(operator) ? ""  : operator;
		 
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * @return the branchCode
	 */
	public String getBranchCode() {
		return Objects.isNull(branchCode) ? ""  : branchCode;
		 
	}

	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the paymentCode
	 */
	public String getPaymentCode() {
		return Objects.isNull(paymentCode) ? "01"  : paymentCode;
		 
	}

	/**
	 * @param paymentCode the paymentCode to set
	 */
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	/**
	 * @return the invoiceStatus
	 */
	public String getInvoiceStatus() {
		return Objects.isNull(invoiceStatusCode) ? "02"  : invoiceStatusCode;
		 
	}

	/**
	 * @param invoiceStatus the invoiceStatus to set
	 */
	public void setInvoiceStatusCode(String invoiceStatusCode) {
		this.invoiceStatusCode = invoiceStatusCode;
	}

	/**
	 * @return the validatedDate
	 */
	public String getValidatedDate() {
		return Objects.isNull(validatedDate) ? ""  : validatedDate;
		 
	}

	/**
	 * @param validatedDate the validatedDate to set
	 */
	public void setValidatedDate(String validatedDate) {
		this.validatedDate = validatedDate;
	}

	/**
	 * @return the stockReleasedDate
	 */
	public String getStockReleasedDate() {
		return Objects.isNull(stockReleasedDate) ? ""  : stockReleasedDate;
		 
	}

	/**
	 * @param stockReleasedDate the stockReleasedDate to set
	 */
	public void setStockReleasedDate(String stockReleasedDate) {
		this.stockReleasedDate = stockReleasedDate;
	}

	/**
	 * @return the cancelReqeuestedDate
	 */
	public String getCancelReqeuestedDate() {
		return Objects.isNull(cancelReqeuestedDate) ? ""  : cancelReqeuestedDate;
		 
	}

	/**
	 * @param cancelReqeuestedDate the cancelReqeuestedDate to set
	 */
	public void setCancelReqeuestedDate(String cancelReqeuestedDate) {
		this.cancelReqeuestedDate = cancelReqeuestedDate;
	}

	/**
	 * @return the canceledDate
	 */
	public String getCanceledDate() {
		return Objects.isNull(canceledDate) ? ""  : canceledDate;
		 
	}

	/**
	 * @param canceledDate the canceledDate to set
	 */
	public void setCanceledDate(String canceledDate) {
		this.canceledDate = canceledDate;
	}

	/**
	 * @return the refundedReasonCode
	 */
	public String getRefundedReasonCode() {
		return Objects.isNull(refundedReasonCode) ? ""  : refundedReasonCode;
		 
	}

	/**
	 * @param refundedReasonCode the refundedReasonCode to set
	 */
	public void setRefundedReasonCode(String refundedReasonCode) {
		this.refundedReasonCode = refundedReasonCode;
	}

	/**
	 * @return the purchaseAccept
	 */
	public String getPurchaseAccept() {
		return Objects.isNull(purchaseAccept) ? "N"  : purchaseAccept;
		 
	}

	/**
	 * @param purchaseAccept the purchaseAccept to set
	 */
	public void setPurchaseAccept(String purchaseAccept) {
		this.purchaseAccept = purchaseAccept;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return Objects.isNull(remark) ? " "  : remark;
		 
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return Objects.isNull(userId) ? "Fourier"  : userId;
		 
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the topMessage
	 */
	public String getTopMessage() {
		return Objects.isNull(topMessage) ? "WELCOME" : topMessage;

	}

	/**
	 * @param topMessage the topMessage to set
	 */
	public void setTopMessage(String topMessage) {
		this.topMessage = topMessage;
	}

	/**
	 * @return the bottomMessage
	 */
	public String getBottomMessage() {
		return Objects.isNull(bottomMessage) ? "THANKS" : bottomMessage;

	}

	/**
	 * @param bottomMessage the bottomMessage to set
	 */
	public void setBottomMessage(String bottomMessage) {
		this.bottomMessage = bottomMessage;
	}

	@Override
	public String toString() {
		return "VCISInvoiceDto [sdcId=" + sdcId + ", mrc=" + mrc + ", registeredTin=" + registeredTin + ", clientTin="
				+ clientTin + ", clientName=" + clientName + ", clientPhone=" + clientPhone + ", clientAddress="
				+ clientAddress + ", invoiceNumber=" + invoiceNumber + ", invoiceType=" + invoiceType
				+ ", transactionType=" + transactionType + ", taxableAmountA=" + taxableAmountA + ", taxableAmountB="
				+ taxableAmountB + ", taxableAmountC=" + taxableAmountC + ", taxableAmountD=" + taxableAmountD
				+ ", taxRateA=" + taxRateA + ", taxRateB=" + taxRateB + ", taxRateC=" + taxRateC + ", taxRateD="
				+ taxRateD + ", taxAmountA=" + taxAmountA + ", taxAmountB=" + taxAmountB + ", taxAmountC=" + taxAmountC
				+ ", taxAmountD=" + taxAmountD + ", totalAmount=" + totalAmount + ", totalTaxAmount=" + totalTaxAmount
				+ ", totalDiscountAmount=" + totalDiscountAmount + ", cisDate=" + cisDate + ", refundRef=" + refundRef
				+ ", journal=" + journal + ", sales=" + sales + ", operator=" + operator + ", branchCode=" + branchCode
				+ ", paymentCode=" + paymentCode + ", invoiceStatusCode=" + invoiceStatusCode + ", validatedDate="
				+ validatedDate + ", stockReleasedDate=" + stockReleasedDate + ", cancelReqeuestedDate="
				+ cancelReqeuestedDate + ", canceledDate=" + canceledDate + ", refundedReasonCode=" + refundedReasonCode
				+ ", purchaseAccept=" + purchaseAccept + ", remark=" + remark + ", userId=" + userId + ", topMessage="
				+ topMessage + ", bottomMessage=" + bottomMessage + "]";
	}

}
