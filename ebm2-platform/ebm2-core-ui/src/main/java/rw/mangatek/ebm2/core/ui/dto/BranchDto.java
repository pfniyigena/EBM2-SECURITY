/*************************************************************************************************
 * Copyright (c) Mangatek Ltd(Owner) 2020 to Present. All Rights Reserved.
 * No part of this file may be reproduced or distributed in any form or by any means, 
 * or stored in a database or retrieval system, without the prior written permission of the owner.
 *************************************************************************************************/
package rw.mangatek.ebm2.core.ui.dto;

/**
 * @author Pierre Fourier NIYIGENA
 *
 */
public class BranchDto {
	private String id;
	private String branchId;

	private String branchName;

	private String branchStatusCode;

	private String provinceName;

	private String districtName;

	private String sectorName;

	private String locationDescription;

	private String headQuarter;

	private String managerName;

	private String managerContactNumber;

	private String manageEmail;

	private String taxpayerId;

	public BranchDto() {

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the branchStatusCode
	 */
	public String getBranchStatusCode() {
		return branchStatusCode;
	}

	/**
	 * @param branchStatusCode the branchStatusCode to set
	 */
	public void setBranchStatusCode(String branchStatusCode) {
		this.branchStatusCode = branchStatusCode;
	}

	/**
	 * @return the provinceName
	 */
	public String getProvinceName() {
		return provinceName;
	}

	/**
	 * @param provinceName the provinceName to set
	 */
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	/**
	 * @return the districtName
	 */
	public String getDistrictName() {
		return districtName;
	}

	/**
	 * @param districtName the districtName to set
	 */
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	/**
	 * @return the sectorName
	 */
	public String getSectorName() {
		return sectorName;
	}

	/**
	 * @param sectorName the sectorName to set
	 */
	public void setSectorName(String sectorName) {
		this.sectorName = sectorName;
	}

	/**
	 * @return the locationDescription
	 */
	public String getLocationDescription() {
		return locationDescription;
	}

	/**
	 * @param locationDescription the locationDescription to set
	 */
	public void setLocationDescription(String locationDescription) {
		this.locationDescription = locationDescription;
	}

	/**
	 * @return the headQuarter
	 */
	public String getHeadQuarter() {
		return headQuarter;
	}

	/**
	 * @param headQuarter the headQuarter to set
	 */
	public void setHeadQuarter(String headQuarter) {
		this.headQuarter = headQuarter;
	}

	/**
	 * @return the managerName
	 */
	public String getManagerName() {
		return managerName;
	}

	/**
	 * @param managerName the managerName to set
	 */
	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	/**
	 * @return the managerContactNumber
	 */
	public String getManagerContactNumber() {
		return managerContactNumber;
	}

	/**
	 * @param managerContactNumber the managerContactNumber to set
	 */
	public void setManagerContactNumber(String managerContactNumber) {
		this.managerContactNumber = managerContactNumber;
	}

	/**
	 * @return the manageEmail
	 */
	public String getManageEmail() {
		return manageEmail;
	}

	/**
	 * @param manageEmail the manageEmail to set
	 */
	public void setManageEmail(String manageEmail) {
		this.manageEmail = manageEmail;
	}

	/**
	 * @return the taxpayerId
	 */
	public String getTaxpayerId() {
		return taxpayerId;
	}

	/**
	 * @param taxpayerId the taxpayerId to set
	 */
	public void setTaxpayerId(String taxpayerId) {
		this.taxpayerId = taxpayerId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((branchName == null) ? 0 : branchName.hashCode());
		result = prime * result + ((branchStatusCode == null) ? 0 : branchStatusCode.hashCode());
		result = prime * result + ((districtName == null) ? 0 : districtName.hashCode());
		result = prime * result + ((headQuarter == null) ? 0 : headQuarter.hashCode());
		result = prime * result + ((locationDescription == null) ? 0 : locationDescription.hashCode());
		result = prime * result + ((manageEmail == null) ? 0 : manageEmail.hashCode());
		result = prime * result + ((managerContactNumber == null) ? 0 : managerContactNumber.hashCode());
		result = prime * result + ((managerName == null) ? 0 : managerName.hashCode());
		result = prime * result + ((provinceName == null) ? 0 : provinceName.hashCode());
		result = prime * result + ((sectorName == null) ? 0 : sectorName.hashCode());
		result = prime * result + ((taxpayerId == null) ? 0 : taxpayerId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BranchDto other = (BranchDto) obj;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (branchName == null) {
			if (other.branchName != null)
				return false;
		} else if (!branchName.equals(other.branchName))
			return false;
		if (branchStatusCode == null) {
			if (other.branchStatusCode != null)
				return false;
		} else if (!branchStatusCode.equals(other.branchStatusCode))
			return false;
		if (districtName == null) {
			if (other.districtName != null)
				return false;
		} else if (!districtName.equals(other.districtName))
			return false;
		if (headQuarter == null) {
			if (other.headQuarter != null)
				return false;
		} else if (!headQuarter.equals(other.headQuarter))
			return false;
		if (locationDescription == null) {
			if (other.locationDescription != null)
				return false;
		} else if (!locationDescription.equals(other.locationDescription))
			return false;
		if (manageEmail == null) {
			if (other.manageEmail != null)
				return false;
		} else if (!manageEmail.equals(other.manageEmail))
			return false;
		if (managerContactNumber == null) {
			if (other.managerContactNumber != null)
				return false;
		} else if (!managerContactNumber.equals(other.managerContactNumber))
			return false;
		if (managerName == null) {
			if (other.managerName != null)
				return false;
		} else if (!managerName.equals(other.managerName))
			return false;
		if (provinceName == null) {
			if (other.provinceName != null)
				return false;
		} else if (!provinceName.equals(other.provinceName))
			return false;
		if (sectorName == null) {
			if (other.sectorName != null)
				return false;
		} else if (!sectorName.equals(other.sectorName))
			return false;
		if (taxpayerId == null) {
			if (other.taxpayerId != null)
				return false;
		} else if (!taxpayerId.equals(other.taxpayerId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BranchDto [branchId=" + branchId + ", branchName=" + branchName + ", branchStatusCode="
				+ branchStatusCode + ", provinceName=" + provinceName + ", districtName=" + districtName
				+ ", sectorName=" + sectorName + ", locationDescription=" + locationDescription + ", headQuarter="
				+ headQuarter + ", managerName=" + managerName + ", managerContactNumber=" + managerContactNumber
				+ ", manageEmail=" + manageEmail + ", taxpayerId=" + taxpayerId + "]";
	}

}
