/**
 * 
 */
package rw.mangatek.ebm2.core.ui.dto;

import java.math.BigDecimal;

/**
 * @author pfniy
 *
 */
public class SaleCartDto {
	private String productId;
	private BigDecimal unitPrice;
	private BigDecimal quantity;
	private String taxLabel;
	private BigDecimal discount;
	private BigDecimal totalAmount;

	public SaleCartDto() {

	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the quantity
	 */
	public BigDecimal getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the unitPrice
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * @return the taxLabel
	 */
	public String getTaxLabel() {
		return taxLabel;
	}

	/**
	 * @param taxLabel the taxLabel to set
	 */
	public void setTaxLabel(String taxLabel) {
		this.taxLabel = taxLabel;
	}

	/**
	 * @return the discount
	 */
	public BigDecimal getDiscount() {
		return discount;
	}

	/**
	 * @param discount the discount to set
	 */
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	/**
	 * @return the totalAmount
	 */
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

}
