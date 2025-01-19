package rw.mangatek.ebm2.core.ui.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

import rw.mangatek.ebm2.core.domain.EbmCustomer;

public class CustomerDto {

	private String id;

	private String registeredTin;

	private String branchId;
	
	private String customerNumber;
	@Size(max=9,min=9)
	private String customerTin;

	private String customerName;

	private String customerAddress;

	private String customerPhone;
	@Email
	private String customerEmail;

	private String faxNo;

	private String useYn;

	private boolean sentServer;

	public CustomerDto() {

	}

	public CustomerDto(String id, String registeredTin, String branchId, String customerNumber, String customerTin,
			String customerName, String customerAddress, String customerPhone, String customerEmail, String faxNo,
			String useYn, boolean sentServer) {
		this.id = id;
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

	public static EbmCustomer fromCustomerDto(CustomerDto dto) {
		EbmCustomer ebmCustomer = new EbmCustomer();
		ebmCustomer.setBranchId(dto.getBranchId());
		ebmCustomer.setCustomerAddress(dto.getCustomerAddress());
		ebmCustomer.setCustomerEmail(dto.getCustomerEmail());
		ebmCustomer.setCustomerName(dto.getCustomerName());
		ebmCustomer.setCustomerNumber(dto.getCustomerNumber());
		ebmCustomer.setCustomerPhone(dto.getCustomerPhone());
		ebmCustomer.setCustomerTin(dto.getCustomerTin());
		ebmCustomer.setFaxNo(dto.getFaxNo());
		ebmCustomer.setId(dto.getId());
		ebmCustomer.setRegisteredTin(dto.getRegisteredTin());
		ebmCustomer.setSentServer(dto.isSentServer());
		ebmCustomer.setUseYn(dto.getUseYn());
		return ebmCustomer;

	}
	public static CustomerDto  fromCustomerModel(EbmCustomer customerModel) {
		CustomerDto ebmCustomer = new CustomerDto();
		ebmCustomer.setBranchId(customerModel.getBranchId());
		ebmCustomer.setCustomerAddress(customerModel.getCustomerAddress());
		ebmCustomer.setCustomerEmail(customerModel.getCustomerEmail());
		ebmCustomer.setCustomerName(customerModel.getCustomerName());
		ebmCustomer.setCustomerNumber(customerModel.getCustomerNumber());
		ebmCustomer.setCustomerPhone(customerModel.getCustomerPhone());
		ebmCustomer.setCustomerTin(customerModel.getCustomerTin());
		ebmCustomer.setFaxNo(customerModel.getFaxNo());
		ebmCustomer.setId(customerModel.getId());
		ebmCustomer.setRegisteredTin(customerModel.getRegisteredTin());
		ebmCustomer.setSentServer(customerModel.getSentServer());
		ebmCustomer.setUseYn(customerModel.getUseYn());
		return ebmCustomer;

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
	 * @return the registeredTin
	 */
	public String getRegisteredTin() {
		return registeredTin;
	}

	/**
	 * @param registeredTin the registeredTin to set
	 */
	public void setRegisteredTin(String registeredTin) {
		this.registeredTin = registeredTin;
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
	 * @return the customerNumber
	 */
	public String getCustomerNumber() {
		return customerNumber;
	}

	/**
	 * @param customerNumber the customerNumber to set
	 */
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	/**
	 * @return the customerTin
	 */
	public String getCustomerTin() {
		return customerTin;
	}

	/**
	 * @param customerTin the customerTin to set
	 */
	public void setCustomerTin(String customerTin) {
		this.customerTin = customerTin;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the customerAddress
	 */
	public String getCustomerAddress() {
		return customerAddress;
	}

	/**
	 * @param customerAddress the customerAddress to set
	 */
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	/**
	 * @return the customerPhone
	 */
	public String getCustomerPhone() {
		return customerPhone;
	}

	/**
	 * @param customerPhone the customerPhone to set
	 */
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	/**
	 * @return the customerEmail
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}

	/**
	 * @param customerEmail the customerEmail to set
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	/**
	 * @return the faxNo
	 */
	public String getFaxNo() {
		return faxNo;
	}

	/**
	 * @param faxNo the faxNo to set
	 */
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	/**
	 * @return the useYn
	 */
	public String getUseYn() {
		return useYn;
	}

	/**
	 * @param useYn the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	/**
	 * @return the sentServer
	 */
	public boolean isSentServer() {
		return sentServer;
	}

	/**
	 * @param sentServer the sentServer to set
	 */
	public void setSentServer(boolean sentServer) {
		this.sentServer = sentServer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchId == null) ? 0 : branchId.hashCode());
		result = prime * result + ((customerAddress == null) ? 0 : customerAddress.hashCode());
		result = prime * result + ((customerEmail == null) ? 0 : customerEmail.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((customerNumber == null) ? 0 : customerNumber.hashCode());
		result = prime * result + ((customerPhone == null) ? 0 : customerPhone.hashCode());
		result = prime * result + ((customerTin == null) ? 0 : customerTin.hashCode());
		result = prime * result + ((faxNo == null) ? 0 : faxNo.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((registeredTin == null) ? 0 : registeredTin.hashCode());
		result = prime * result + (sentServer ? 1231 : 1237);
		result = prime * result + ((useYn == null) ? 0 : useYn.hashCode());
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
		CustomerDto other = (CustomerDto) obj;
		if (branchId == null) {
			if (other.branchId != null)
				return false;
		} else if (!branchId.equals(other.branchId))
			return false;
		if (customerAddress == null) {
			if (other.customerAddress != null)
				return false;
		} else if (!customerAddress.equals(other.customerAddress))
			return false;
		if (customerEmail == null) {
			if (other.customerEmail != null)
				return false;
		} else if (!customerEmail.equals(other.customerEmail))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (customerNumber == null) {
			if (other.customerNumber != null)
				return false;
		} else if (!customerNumber.equals(other.customerNumber))
			return false;
		if (customerPhone == null) {
			if (other.customerPhone != null)
				return false;
		} else if (!customerPhone.equals(other.customerPhone))
			return false;
		if (customerTin == null) {
			if (other.customerTin != null)
				return false;
		} else if (!customerTin.equals(other.customerTin))
			return false;
		if (faxNo == null) {
			if (other.faxNo != null)
				return false;
		} else if (!faxNo.equals(other.faxNo))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (registeredTin == null) {
			if (other.registeredTin != null)
				return false;
		} else if (!registeredTin.equals(other.registeredTin))
			return false;
		if (sentServer != other.sentServer)
			return false;
		if (useYn == null) {
			if (other.useYn != null)
				return false;
		} else if (!useYn.equals(other.useYn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CustomerDto [id=" + id + ", registeredTin=" + registeredTin + ", branchId=" + branchId
				+ ", customerNumber=" + customerNumber + ", customerTin=" + customerTin + ", customerName="
				+ customerName + ", customerAddress=" + customerAddress + ", customerPhone=" + customerPhone
				+ ", customerEmail=" + customerEmail + ", faxNo=" + faxNo + ", useYn=" + useYn + ", sentServer="
				+ sentServer + "]";
	}

}
