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
import rw.mangatek.ebm2.core.enns.ImportStatus;

@Entity
@Table(name = "ebm_importation")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EbmImportation extends AbstractEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 530062113789110936L;
	@Column(name = "declaration_number")
	private String declarationNumber;
	@Column(name = "declaration_date")
	private LocalDate declarationDate;
	@Column(name = "task_code")
	private String taskCode;
	@Column(name = "hs_code")
	private String hsCode;
	@Column(name = "item_name")
	private String itemName;
	@Column(name = "item_sequence")
	private int itemSequence;
	@Column(name = "import_status")
	@Enumerated(EnumType.STRING)
	private ImportStatus importStatus;
	@Column(name = "export_nation_code")
	private String exportNationCode;
	@Column(name = "orgine_nation_code")
	private String orgineNationCode;
	@Column(name = "packaging")
	private BigDecimal packaging;
	@Column(name = "packaging_unit_code")
	private String packagingUnitCode;
	@Column(name = "quantity")
	private BigDecimal quantity;
	@Column(name = "quantity_unit_code")
	private String quantityUnitCode;
	@Column(name = "gross_weight")
	private BigDecimal grossWeight;
	@Column(name = "net_weight")
	private BigDecimal netWeight;
	@Column(name = "supplier_name")
	private String supplierName;
	@Column(name = "agent_name")
	private String agentName;
	@Column(name = "foreign_currency_code")
	private String foreignCurrencyCode;
	@Column(name = "foreign_currency_amount")
	private BigDecimal foreignCurrencyAmount;
	@Column(name = "exchange_rate")
	private BigDecimal exchangeRate;
	@Column(name = "sent_server")
	private Boolean sentServer;
	@ToString.Exclude
	@OneToOne
	@JoinColumn(name = "ebm_item_id", unique = false, nullable = true, insertable = true, updatable = true)
	private EbmItem ebmItem;
}
