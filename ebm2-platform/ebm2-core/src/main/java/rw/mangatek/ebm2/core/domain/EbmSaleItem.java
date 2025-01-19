package rw.mangatek.ebm2.core.domain;

 
import java.math.BigDecimal;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ebm_sale_item")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EbmSaleItem extends AbstractEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 530062113789110936L;
	@Column(name = "item_Code")
	private String itemCode;
	@Column(name = "item_classification_code")
	private String itemClassificationCode;
	@Column(name = "item_name")
	private String itemName;
	@Column(name = "item_sequance")
	private int itemSequance;
	@Column(name = "barcode")
	private String barcode;
	@Column(name = "package_unit")
	private String packageUnit;
	@Column(name = "packaging")
	private  BigDecimal packaging;
	@Column(name = "quantity_unit_code")
	private String quantityUnitCode;
	@Column(name = "quantity")
	private  BigDecimal quantity;
	@Column(name = "sale_unit_price")
	private  BigDecimal saleUnitPrice;
	@Column(name = "purchase_unit_price")
	private  BigDecimal purchaseUnitPrice;
	@Column(name = "discount_rate")
	private  BigDecimal discountRate;
	@Column(name = "discount_amount")
	private  BigDecimal discountAmount;
	@Column(name = "taxable_amount")
	private  BigDecimal taxableAmount;
	@Column(name = "tax_amount")
	private  BigDecimal taxAmount;
	@Column(name = "insurance_Code")
	private String insuranceCode;
	@Column(name = "insurance_name")
	private String insuranceName;
	@Column(name = "premium_rate")
	private  BigDecimal premiumRate;
	@Column(name = "insurance_amount")
	private  BigDecimal insuranceAmount;
	@Column(name = "tax_label")
	private String taxLabel;
	@Column(name = "total_amount")
	private  BigDecimal totalAmount;
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ebm_invoice_id", unique = false, nullable = true, insertable = true, updatable = true)
	private EbmInvoice ebmInvoice;

}
