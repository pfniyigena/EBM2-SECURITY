package rw.mangatek.ebm2.core.domain;

import java.math.BigDecimal;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "ebm_item")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EbmItem extends AbstractEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 4221274247598747503L;
	@Column(name = "registred_tin")
	private String registredTin;
	@Column(name = "regist_branch_id")
	private String registBranchId;
	@Column(name = "rra_modif")
	private String rraModif;
	@Column(name = "item_name")
	private String itemName;
	@Column(name = "item_code", unique = true)
	private String itemCode;
	@Column(name = "barcode")
	private String barcode;
	@Column(name = "batch_number")
	private String batchNumber;
	@Column(name = "item_classification_code")
	private String itemClassificationCode;
	@Column(name = "item_standard_name")
	private String itemStandardName;
	@Column(name = "item_type_code")
	private String itemTypeCode;
	@Column(name = "origin_place_code")
	private String originPlaceCode;
	@Column(name = "packaging_unit_code")
	private String packagingUnitCode;
	@Column(name = "quantity_unit_code")
	private String quantityUnitCode;
	@Column(name = "default_unit_price")
	private BigDecimal defaultUnitPrice;
	@Column(name = "purcase_unit_price")
	private  BigDecimal purcaseUnitPrice;
	@Column(name = "group_unit_price1")
	private BigDecimal groupUnitPrice1;
	@Column(name = "group_unit_price2")
	private BigDecimal groupUnitPrice2;
	@Column(name = "group_unit_price3")
	private BigDecimal groupUnitPrice3;
	@Column(name = "group_unit_price4")
	private BigDecimal groupUnitPrice4;
	@Column(name = "group_unit_price5")
	private BigDecimal groupUnitPrice5;
	@Column(name = "safty_quantity")
	private BigDecimal saftyQuantity;
	@Column(name = "taxation_type_code")
	private String taxationTypeCode;
	@Column(name = "insurance_applicable")
	private String insuranceApplicable;
	@Column(name = "registration_name")
	private String registrationName;
	@Column(name = "additional_information")
	private String additionalInformation;
	@Column(name = "use_yn")
	private String useYn;	
	@Column(name = "current_stock")
	private BigDecimal currentStock;
	@Column(name = "sent_server")
	private Boolean sentServer;
	@Column(name = "stock_sent")
	private Boolean stockSent;
}
