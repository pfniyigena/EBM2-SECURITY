package rw.mangatek.ebm2.core.ui.dto;

import java.math.BigDecimal;

import rw.mangatek.ebm2.core.domain.EbmInsurance;

public class InsuranceDto {
	private String id;
	private String registeredTin;
	private String branchId;
	private String insuranceCode;
	private String insuranceName;
	private BigDecimal premiumRate;
	private String useYn;
	private boolean sentServer;

	public InsuranceDto() {

	}

	public InsuranceDto(String id, String registeredTin, String branchId, String insuranceCode, String insuranceName,
			BigDecimal premiumRate, String useYn,
			boolean sentServer) {
		this.id = id;
		this.registeredTin = registeredTin;
		this.branchId = branchId;
		this.insuranceCode = insuranceCode;
		this.insuranceName = insuranceName;
		this.premiumRate = premiumRate;
		this.useYn = useYn;
		this.sentServer = sentServer;

	}

	public static InsuranceDto fromInsuranceModel(EbmInsurance insurance) {

		InsuranceDto dto = new InsuranceDto();
		dto.setId(insurance.getId());
		dto.setBranchId(insurance.getBranchId());
		dto.setRegisteredTin(insurance.getRegisteredTin());
		dto.setInsuranceCode(insurance.getInsuranceCode());
		dto.setInsuranceName(insurance.getInsuranceName());
		dto.setPremiumRate(insurance.getPremiumRate());
		dto.setSentServer(insurance.isSentServer());
		dto.setUseYn(insurance.getUseYn());
		return dto;
	}

	public static EbmInsurance fromInsuranceDto(InsuranceDto insuranceDto) {
		EbmInsurance insurance = new EbmInsurance();
		insurance.setId(insuranceDto.getId());
		insurance.setBranchId(insuranceDto.getBranchId());
		insurance.setRegisteredTin(insuranceDto.getRegisteredTin());
		insurance.setInsuranceCode(insuranceDto.getInsuranceCode());
		insurance.setInsuranceName(insuranceDto.getInsuranceName());
		insurance.setPremiumRate(insuranceDto.getPremiumRate());
		insurance.setSentServer(insuranceDto.isSentServer());
		insurance.setUseYn(insuranceDto.getUseYn());
		return insurance;
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
	 * @return the insuranceCode
	 */
	public String getInsuranceCode() {
		return insuranceCode;
	}

	/**
	 * @param insuranceCode the insuranceCode to set
	 */
	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}

	/**
	 * @return the insuranceName
	 */
	public String getInsuranceName() {
		return insuranceName;
	}

	/**
	 * @param insuranceName the insuranceName to set
	 */
	public void setInsuranceName(String insuranceName) {
		this.insuranceName = insuranceName;
	}

	/**
	 * @return the premiumRate
	 */
	public BigDecimal getPremiumRate() {
		return premiumRate;
	}

	/**
	 * @param premiumRate the premiumRate to set
	 */
	public void setPremiumRate(BigDecimal premiumRate) {
		this.premiumRate = premiumRate;
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
	public String toString() {
		return "InsuranceDto [id=" + id + ", registeredTin=" + registeredTin + ", branchId=" + branchId
				+ ", insuranceCode=" + insuranceCode + ", insuranceName=" + insuranceName + ", premiumRate="
				+ premiumRate + ", useYn=" + useYn + ", sentServer=" + sentServer + ", getId()=" + getId()
				+ ", getRegisteredTin()=" + getRegisteredTin() + ", getBranchId()=" + getBranchId()
				+ ", getInsuranceCode()=" + getInsuranceCode() + ", getInsuranceName()=" + getInsuranceName()
				+ ", getPremiumRate()=" + getPremiumRate() + ", getUseYn()=" + getUseYn() + ", isSentServer()="
				+ isSentServer() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
