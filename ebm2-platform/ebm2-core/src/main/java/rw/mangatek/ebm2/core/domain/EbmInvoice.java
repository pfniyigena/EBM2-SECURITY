package rw.mangatek.ebm2.core.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "ebm_invoice")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)

public class EbmInvoice extends AbstractEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 530062113789110936L;

	@Column(name = "registered_tin")
	private String registeredTin;
	@Column(name = "branch_id")
	private String branchId;
	@Column(name = "invoice_number")
	private Long invoiceNumber;
	@Column(name = "orginal_invoice")
	private Long orginalInvoice;
	@Column(name = "customer_tin")
	private String customerTin;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "customer_phone")
	private String customerPhone;
	@Column(name = "receipt_type")
	private String receiptType;
	@Column(name = "transaction_type")
	private String transactionType;
	@Column(name = "payment_code")
	private String paymentCode;
	@Column(name = "invoice_status")
	private String invoiceStatus;
	@Column(name = "validated_date")
	private LocalDate validatedDate;
	@Column(name = "cis_date")
	private LocalDateTime cisDate;
	@Column(name = "stock_released_date")
	private LocalDate stockReleasedDate;
	@Column(name = "cancel_reqeuested_date")
	private LocalDate cancelReqeuestedDate;
	@Column(name = "canceled_date")
	private LocalDate canceledDate;
	@Column(name = "refunded_Reason_code")
	private String refundedReasonCode;
	@Column(name = "item_number")
	private int itemNumber;
	@Column(name = "taxable_amount_a")
	private BigDecimal taxableAmountA;
	@Column(name = "taxable_amount_b")
	private BigDecimal taxableAmountB;
	@Column(name = "taxable_amount_c")
	private BigDecimal taxableAmountC;
	@Column(name = "taxable_amount_d")
	private BigDecimal taxableAmountD;
	@Column(name = "tax_rate_a")
	private BigDecimal taxRateA;
	@Column(name = "tax_rate_b")
	private BigDecimal taxRateB;
	@Column(name = "tax_rate_c")
	private BigDecimal taxRateC;
	@Column(name = "tax_rate_d")
	private BigDecimal taxRateD;
	@Column(name = "tax_amount_a")
	private BigDecimal taxAmountA;
	@Column(name = "tax_amount_b")
	private BigDecimal taxAmountB;
	@Column(name = "tax_amount_c")
	private BigDecimal taxAmountC;
	@Column(name = "tax_amount_d")
	private BigDecimal taxAmountD;
	@Column(name = "total_tax_amount")
	private BigDecimal totalTaxAmount;
	@Column(name = "total_amount")
	private BigDecimal totalAmount;
	@Column(name = "purchase_accept")
	private String purchaseAccept;
	@Column(name = "remark")
	private String remark;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "registration_name")
	private String registrationName;
	@Column(name = "registration_id")
	private String registrationId;
	@Column(name = "registration_address")
	private String registrationAddress;
	@Column(name = "registration_phone")
	private String registrationPhone;
	@Column(name = "registration_email")
	private String registrationEmail;
	@ToString.Exclude
	@OneToMany(mappedBy = "ebmInvoice", cascade = { CascadeType.ALL }, fetch = FetchType.LAZY,orphanRemoval = true)
	private List<EbmSaleItem> saleItems;
	@ToString.Exclude
	@OneToOne(mappedBy = "ebmInvoice",cascade = { CascadeType.ALL }, fetch = FetchType.LAZY,orphanRemoval = true)
	private EbmReceipt ebmReceipt;
	@Column(name = "sent_server")
	private Boolean sentServer;
}
