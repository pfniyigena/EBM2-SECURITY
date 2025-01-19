/*************************************************************************************************
 * Copyright (c) Mangatek Ltd(Owner) 2020 to Present. All Rights Reserved.
 * No part of this file may be reproduced or distributed in any form or by any means, 
 * or stored in a database or retrieval system, without the prior written permission of the owner.
 *************************************************************************************************/
package rw.mangatek.ebm2.core.ui.dto;

import java.math.BigDecimal;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;

public class VCISPurchaseItemDto {
	@XmlAttribute(required = true)
	private String productCode;
	@XmlAttribute(required = true)
	private String productName;
	@XmlAttribute(required = true)
	private String unspcCode;
	@XmlAttribute(required = true)
	private String barcode;
	@XmlAttribute(required = true)

	private BigDecimal unitPrice;
	@XmlAttribute(required = true)

	private BigDecimal quantity;
	@XmlAttribute(required = true)
	private String taxLabel;
	@XmlAttribute(required = true)

	private BigDecimal taxableAmount;
	@XmlAttribute(required = true)

	private BigDecimal taxAmount;

	private BigDecimal discoutRate;

	private BigDecimal discountAmount;
	@XmlAttribute(required = true)

	private BigDecimal totalAmount;
	private int itemSeq;
	private String packageUnit;

	private BigDecimal packaging;
	private String quantityUnitCode;
	private String insuranceCode;
	private String insuranceName;

	private BigDecimal premiumRate;

	private BigDecimal insuranceAmount;

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return Objects.isNull(productCode) ?  ""  : productCode;
		 
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * @return the productName
	 */
	public String getProductName() {
		return Objects.isNull(productName) ?  ""  : productName;
		 
	}

	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * @return the unspcCode
	 */
	public String getUnspcCode() {
		return Objects.isNull(unspcCode) ?  ""  : unspcCode;
		 
	}

	/**
	 * @param unspcCode the unspcCode to set
	 */
	public void setUnspcCode(String unspcCode) {
		this.unspcCode = unspcCode;
	}

	/**
	 * @return the barcode
	 */
	public String getBarcode() {
		return Objects.isNull(barcode) ?  ""  : barcode;
		 
	}

	/**
	 * @param barcode the barcode to set
	 */
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return Objects.isNull(unitPrice) ? new BigDecimal("0.00") : unitPrice;
		 
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return Objects.isNull(quantity) ? new BigDecimal("0.00") : quantity;
		 
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the taxLabel
	 */
	public String getTaxLabel() {
		return Objects.isNull(taxLabel) ?  "B"  : taxLabel;
		 
	}

	/**
	 * @param taxLabel the taxLabel to set
	 */
	public void setTaxLabel(String taxLabel) {
		this.taxLabel = taxLabel;
	}

	/**
	 * @return the taxableAmount
	 */
	public BigDecimal getTaxableAmount() {
		return Objects.isNull(taxableAmount) ? new BigDecimal("0.00") : taxableAmount;
		 
	}

	/**
	 * @param taxableAmount the taxableAmount to set
	 */
	public void setTaxableAmount(BigDecimal taxableAmount) {
		this.taxableAmount = taxableAmount;
	}

	/**
	 * @return the taxAmount
	 */
	public BigDecimal getTaxAmount() {
		return Objects.isNull(taxAmount) ? new BigDecimal("0.00") : taxAmount;
		 
	}

	/**
	 * @param taxAmount the taxAmount to set
	 */
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 * @return the discoutRate
	 */
	public BigDecimal getDiscoutRate() {
		return Objects.isNull(discoutRate) ? new BigDecimal("0.00") : discoutRate;
		 
	}

	/**
	 * @param discoutRate the discoutRate to set
	 */
	public void setDiscoutRate(BigDecimal discoutRate) {
		this.discoutRate = discoutRate;
	}

	/**
	 * @return the discoutnAmount
	 */
	public BigDecimal getDiscountAmount() {
		return Objects.isNull(discountAmount) ? new BigDecimal("0.00") : discountAmount;
		 
	}

	/**
	 * @param discoutnAmount the discoutnAmount to set
	 */
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
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
	 * @return the itemSeq
	 */
	public int getItemSeq() {
		return itemSeq;
	}

	/**
	 * @param itemSeq the itemSeq to set
	 */
	public void setItemSeq(int itemSeq) {
		this.itemSeq = itemSeq;
	}

	/**
	 * @return the packageUnit
	 */
	public String getPackageUnit() {
		return Objects.isNull(packageUnit) ?  "NT"  : packageUnit;
	}

	/**
	 * @param packageUnit the packageUnit to set
	 */
	public void setPackageUnit(String packageUnit) {
		
		this.packageUnit =packageUnit ;
	}

	/**
	 * @return the packaging
	 */
	public BigDecimal getPackaging() {
		return Objects.isNull(packaging) ? new BigDecimal("1.00") : packaging;
		 
	}

	/**
	 * @param packaging the packaging to set
	 */
	public void setPackaging(BigDecimal packaging) {
		this.packaging = packaging;
	}

	/**
	 * @return the quantityUnitCode
	 */
	public String getQuantityUnitCode() {
		return Objects.isNull(quantityUnitCode) ?  "U"  : quantityUnitCode;
		 
	}

	/**
	 * @param quantityUnitCode the quantityUnitCode to set
	 */
	public void setQuantityUnitCode(String quantityUnitCode) {
		this.quantityUnitCode = quantityUnitCode;
	}

	/**
	 * @return the insuranceCode
	 */
	public String getInsuranceCode() {
		return Objects.isNull(insuranceCode) ?  ""  : insuranceCode;
		 
	}

	/**
	 * @param insuranceCode the insuranceCode to set
	 */
	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}

	/**
	 * @return the insuranceName
	 */
	public String getInsuranceName() {
		return Objects.isNull(insuranceName) ? "" : insuranceName;

	}

	/**
	 * @param insuranceName the insuranceName to set
	 */
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	/**
	 * @return the premiumRate
	 */
	public BigDecimal getPremiumRate() {
		return Objects.isNull(premiumRate) ? new BigDecimal("0.00") : premiumRate;

	}

	/**
	 * @param premiumRate the premiumRate to set
	 */
	public void setPremiumRate(BigDecimal premiumRate) {
		this.premiumRate = premiumRate;
	}

	/**
	 * @return the insuranceAmount
	 */
	public BigDecimal getInsuranceAmount() {
		return Objects.isNull(insuranceAmount) ? new BigDecimal("0.00") : insuranceAmount;

	}

	/**
	 * @param insuranceAmount the insuranceAmount to set
	 */
	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	@Override
	public String toString() {
		return "VCISSaleDto [productCode=" + productCode + ", productName=" + productName + ", unspcCode=" + unspcCode
				+ ", barcode=" + barcode + ", unitPrice=" + unitPrice + ", quantity=" + quantity + ", taxLabel="
				+ taxLabel + ", taxableAmount=" + taxableAmount + ", taxAmount=" + taxAmount + ", discoutRate="
				+ discoutRate + ", discoutnAmount=" + discountAmount + ", totalAmount=" + totalAmount + ", itemSeq="
				+ itemSeq + ", packageUnit=" + packageUnit + ", packaging=" + packaging + ", quantityUnitCode="
				+ quantityUnitCode + ", insuranceCode=" + insuranceCode + ", insuranceName=" + insuranceName
				+ ", premiumRate=" + premiumRate + ", insuranceAmount=" + insuranceAmount + "]";
	}

}
