package rw.mangatek.ebm2.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ebm_stock_move")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EbmStockMove extends AbstractEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 530062113789110936L;
	@Column(name = "customer_branch_id")
	private String customerBranchId;
	@Column(name = "customer_tin")
	private String customerTin;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "registration_type_Code")
	private String registrationTypeCode;
	@Column(name = "stored_released_type_code")
	private String storedReleasedTypeCode;
	@Column(name = "occurred_date")
	private LocalDate occurredDate;
	@Column(name = "remark")
	private String remark;
	@Column(name = "stored_released_number")
	private Long storedReleasedNumber;
	@Column(name = "total_amount")
	private BigDecimal totalAmount;
	@Column(name = "total_tax_amount")
	private BigDecimal totalTaxAmount;
	@Column(name = "total_taxable_amount")
	private BigDecimal totalTaxableAmount;
	@Column(name = "total_item_number")
	private int totalItemNumber;
	@ToString.Exclude
	@OneToMany(mappedBy = "stockMove", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<EbmStockMoveItem> moveItems;
	@Column(name = "sent_server")
	private Boolean sentServer;

}
