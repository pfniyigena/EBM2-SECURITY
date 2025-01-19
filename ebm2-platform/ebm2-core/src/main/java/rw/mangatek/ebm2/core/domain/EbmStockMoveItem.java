package rw.mangatek.ebm2.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ebm_stock_move_item")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EbmStockMoveItem extends AbstractEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 530062113789110936L;
	@Column(name = "barcode")
	private String barcode;
	@Column(name = "item_name")
	private String itemName;
	@Column(name = "item_Code")
	private String itemCode;
	@Column(name = "item_classification_code")
	private String itemClassificationCode;
	@Column(name = "expiration_date")
	private LocalDate expirationDate;
	@Column(name = "sequance_number")
	private int sequanceNumber;
	@Column(name = "packaging")
	private  BigDecimal packaging;
	@Column(name = "package_unit_code")
	private String packageUnitCode;
	@Column(name = "sale_unit_price")
	private  BigDecimal saleUnitPrice;
	@Column(name = "quantity")
	private  BigDecimal quantity;
	@Column(name = "quantity_unit_code")
	private String quantityUnitCode;
	@Column(name = "purcase_unit_price")
	private  BigDecimal purcaseUnitPrice;
	@Column(name = "tax_amount")
	private  BigDecimal taxAmount;
	@Column(name = "tax_label")
	private String taxLabel;
	@Column(name = "discount_amount")
	private  BigDecimal discountAmount;
	@Column(name = "taxable_amount")
	private  BigDecimal taxableAmount;
	@Column(name = "total_amount")
	private  BigDecimal totalAmount;
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ebm_stock_move_id", unique = false, nullable = true, insertable = true, updatable = true)
	private EbmStockMove stockMove;
	

}
