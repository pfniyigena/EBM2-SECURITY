package rw.mangatek.ebm2.core.domain;

import java.math.BigDecimal;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "ebm_insurance")
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = true)
public class EbmInsurance extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3096436149635316240L;

	@Column(name = "registered_tin")
	private String registeredTin;
	@Column(name = "branch_id")
	private String branchId;
	@Column(name = "insurance_code", unique = true)
	private String insuranceCode;
	@Column(name = "insurance_name")
	private String insuranceName;
	@Column(name = "premium_rate")
	private BigDecimal premiumRate;
	@Column(name = "use_yn")
	private String useYn;
	@Column(name = "sent_server")
	private boolean sentServer;

	public EbmInsurance() {

	}

	public EbmInsurance(String registeredTin, String branchId,   String insuranceCode, String insuranceName,
			BigDecimal premiumRate, String useYn, boolean sentServer) {
		this.registeredTin = registeredTin;
		this.branchId = branchId;
		this.insuranceCode = insuranceCode;
		this.insuranceName = insuranceName;
		this.premiumRate = premiumRate;
		  
		this.useYn = useYn;
		this.sentServer = sentServer;

	}

}
