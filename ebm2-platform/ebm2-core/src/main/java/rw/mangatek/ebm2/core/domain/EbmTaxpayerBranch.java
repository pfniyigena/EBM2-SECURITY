package rw.mangatek.ebm2.core.domain;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "ebm_taxpayer_branch")
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = true)
public class EbmTaxpayerBranch extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3096436149635316240L;
	@Column(name = "branch_id")
	private String branchId;
	@Column(name = "branch_name")
	private String branchName;
	@Column(name = "branch_status_code")
	private String branchStatusCode;
	@Column(name = "province_name")
	private String provinceName;
	@Column(name = "district_name")
	private String districtName;
	@Column(name = "sector_name")
	private String sectorName;
	@Column(name = "location_description")
	private String locationDescription;
	@Column(name = "head_quarter")
	private String headQuarter;
	@Column(name = "manager_name")
	private String managerName;
	@Column(name = "manager_contact_number")
	private String managerContactNumber;
	@Column(name = "manage_email")
	private String manageEmail;
	@ManyToOne
	@JoinColumn(name = "taxpayer_id", unique = false, nullable = true, insertable = true, updatable = true)
	private EbmTaxpayer taxpayer;

	public EbmTaxpayerBranch() {

	}

	public EbmTaxpayerBranch(String branchId, String branchName, String branchStatusCode, String provinceName,
			String districtName, String sectorName, String locationDescription, String headQuarter, String managerName,
			String managerContactNumber, String manageEmail, EbmTaxpayer taxpayer) {
		this.branchId = branchId;
		this.branchName = branchName;
		this.branchStatusCode = branchStatusCode;
		this.provinceName = provinceName;
		this.districtName = districtName;
		this.sectorName = sectorName;
		this.locationDescription = locationDescription;
		this.headQuarter = headQuarter;
		this.managerName = managerName;
		this.managerContactNumber = managerContactNumber;
		this.manageEmail = manageEmail;
		this.taxpayer = taxpayer;

	}

}
