package rw.mangatek.ebm2.core.domain;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "ebm_customer")
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = true)
public class EbmCustomer extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3096436149635316240L;
	@Column(name = "registered_tin")
	private String registeredTin;
	@Column(name = "branch_id")
	private String branchId;	
	@Column(name = "customer_number")
	private String customerNumber;
	@Column(name = "customer_tin", unique = true)
	private String customerTin;
	@Column(name = "customer_name")
	private String customerName;
	@Column(name = "customer_address")
	private String customerAddress;
	@Column(name = "customer_phone")
	private String customerPhone;
	@Column(name = "customer_email")
	private String customerEmail;
	@Column(name = "fax_no")
	private String faxNo;
	@Column(name = "use_yn")
	private String useYn;
	@Column(name = "sent_server")
	private Boolean sentServer;
	
	public EbmCustomer() {

	}

	public EbmCustomer(String registeredTin,String branchId,String customerNumber,String customerTin,String customerName,String customerAddress,String customerPhone,String customerEmail, String faxNo,String useYn,Boolean sentServer) {
		this.registeredTin = registeredTin;
		this.branchId = branchId;
		this.customerNumber = customerNumber;
		this.customerTin = customerTin;
		this.customerName = customerName;
		this.customerPhone = customerAddress;
		this.customerPhone = customerPhone;
		this.customerEmail = customerEmail;
		this.faxNo = faxNo;
		this.useYn = useYn;
		this.sentServer = sentServer;
		
	}

}
