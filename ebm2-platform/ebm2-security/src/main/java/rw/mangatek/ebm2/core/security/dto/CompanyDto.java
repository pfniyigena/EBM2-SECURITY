package rw.mangatek.ebm2.core.security.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import rw.mangatek.ebm2.core.domain.Company;
import rw.mangatek.ebm2.core.enns.CompanyStatus;

public class CompanyDto {
	private String id;
	private String name;
	private String tinNumber;
	private String status;
	private String anydesk;
	private String anydeskPwd;
	private String previousDate;
	private String nextDate;

	private String cisVersion;

	private String cisName;

	private String vsdcVersion;

	private String vsdcName;

	public CompanyDto() {

	}

	public CompanyDto(String id, String name, String tinNumber,String status, String anydesk, String anydeskPwd, String previousDate,
			String nextDate, String cisVersion, String cisName, String vsdcVersion, String vsdcName) {
		this.id = id;
		this.name = name;
		this.tinNumber=tinNumber;
		this.status = status;
		this.anydesk = anydesk;
		this.anydeskPwd = anydeskPwd;
		this.previousDate = previousDate;
		this.nextDate = nextDate;
		this.cisVersion = cisVersion;
		this.cisName = cisName;
		this.vsdcVersion = vsdcVersion;
		this.vsdcName = vsdcName;

	}

	public static CompanyDto fromEntity(Company company) {
		if (company == null)
			return null;
		return new CompanyDto(company.getId(), company.getName(),company.getTinNumber(), company.getStatus().getStatus(), company.getAnydesk(),
				company.getAnydeskPwd(),
				company.getPreviousPaymentDate() == null ? LocalDate.now().toString()
						: company.getPreviousPaymentDate().toString(),
				company.getNexPaymentDate() == null ? LocalDate.now().toString()
						: company.getNexPaymentDate().toString(),
				company.getCisVersion(), company.getCisName(), company.getVsdcVersion(), company.getVsdcName());
	}

	public static Company fromDto(CompanyDto companyDto) {
		if (companyDto == null)
			return null;
		Company company = new Company();
		company.setId(companyDto.getId());
		company.setName(companyDto.getName());
		company.setTinNumber(companyDto.getTinNumber());
		company.setStatus(CompanyStatus.valueOf(companyDto.getStatus()));
		company.setAnydesk(companyDto.getAnydesk());
		company.setAnydeskPwd(companyDto.getAnydeskPwd());
		company.setPreviousPaymentDate(parseDate(companyDto.getPreviousDate()));
		company.setNexPaymentDate(parseDate(companyDto.getNextDate()));
		company.setCisVersion(companyDto.getCisVersion());
		company.setCisName(companyDto.getCisName());
		company.setVsdcVersion(companyDto.getVsdcVersion());
		company.setVsdcName(companyDto.getVsdcName());
		return company;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the anydesk
	 */
	public String getAnydesk() {
		return anydesk;
	}

	/**
	 * @param anydesk the anydesk to set
	 */
	public void setAnydesk(String anydesk) {
		this.anydesk = anydesk;
	}

	/**
	 * @return the anydeskPwd
	 */
	public String getAnydeskPwd() {
		return anydeskPwd;
	}

	/**
	 * @param anydeskPwd the anydeskPwd to set
	 */
	public void setAnydeskPwd(String anydeskPwd) {
		this.anydeskPwd = anydeskPwd;
	}

	/**
	 * @return the previousDate
	 */
	public String getPreviousDate() {
		return previousDate;
	}

	/**
	 * @param previousDate the previousDate to set
	 */
	public void setPreviousDate(String previousDate) {
		this.previousDate = previousDate;
	}

	/**
	 * @return the nextDate
	 */
	public String getNextDate() {
		return nextDate;
	}

	/**
	 * @param nextDate the nextDate to set
	 */
	public void setNextDate(String nextDate) {
		this.nextDate = nextDate;
	}

	private static LocalDate parseDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		if (date == null || date.isEmpty())
			return LocalDate.now();
		// convert String to LocalDate
		return LocalDate.parse(date, formatter);
	}

	/**
	 * @return the cisVersion
	 */
	public String getCisVersion() {
		return cisVersion;
	}

	/**
	 * @param cisVersion the cisVersion to set
	 */
	public void setCisVersion(String cisVersion) {
		this.cisVersion = cisVersion;
	}

	/**
	 * @return the cisName
	 */
	public String getCisName() {
		return cisName;
	}

	/**
	 * @param cisName the cisName to set
	 */
	public void setCisName(String cisName) {
		this.cisName = cisName;
	}

	/**
	 * @return the vsdcVersion
	 */
	public String getVsdcVersion() {
		return vsdcVersion;
	}

	/**
	 * @param vsdcVersion the vsdcVersion to set
	 */
	public void setVsdcVersion(String vsdcVersion) {
		this.vsdcVersion = vsdcVersion;
	}

	/**
	 * @return the vsdcName
	 */
	public String getVsdcName() {
		return vsdcName;
	}

	/**
	 * @param vsdcName the vsdcName to set
	 */
	public void setVsdcName(String vsdcName) {
		this.vsdcName = vsdcName;
	}

	/**
	 * @return the tinNumber
	 */
	public String getTinNumber() {
		return tinNumber;
	}

	/**
	 * @param tinNumber the tinNumber to set
	 */
	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}

}
