package rw.mangatek.ebm2.core.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
 

@Entity
@Table(name = "ebm_receipt")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString  
@EqualsAndHashCode(callSuper = true)
public class EbmReceipt extends AbstractEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 530062113789110936L;
	@Column(name = "mrc" )
	private String mrc;
	@Column(name = "sdc_id" )
	private String sdcId;
	@Column(name = "vsdc_receipt_number" )
	private Long vsdcReceiptNumber;
	@Column(name = "vsdc_total_receipt_number" )
	private Long vsdcTotalReceiptNumber;
	@Column(name = "vsdc_date" )
	private LocalDateTime vsdcDate;
	@Column(name = "vsdc_internal_data" )
	private String vsdcInternalData;
	@Column(name = "vsdc_signature" )
	private String vsdcSignature;
	@Column(name = "journal",length = 10240 )
	private String journal;
	@Column(name = "trade_name" )
	private String tradeName;
	@Column(name = "trade_address" )
	private String tradeAddress;
	@Column(name = "top_message" )
	private String topMessage;
	@Column(name = "bottom_message" )
	private String bottomMessage;
	@Column(name = "received_receipt" )
	private String receivedReceipt;
	@Column(name = "customer_phone" )
	private String customerPhone;
	@Column(name = "customer_tin" )
	private String customerTin;
	@ToString.Exclude
	@OneToOne
	@JoinColumn(name = "ebm_invoice_id", unique = false, nullable = true, insertable = true, updatable = true)
	private EbmInvoice ebmInvoice;

}
