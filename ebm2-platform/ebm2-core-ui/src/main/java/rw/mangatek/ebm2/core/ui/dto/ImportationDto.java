package rw.mangatek.ebm2.core.ui.dto;

import java.time.LocalDate;

import rw.mangatek.ebm2.core.domain.EbmImportation;

public class ImportationDto {
	private String id;
	private String declarationNumber;
	private String importStatus;
	private LocalDate declarationDate;
	private String itemId;

	public ImportationDto() {

	}

	public ImportationDto(String id, String declarationNumber, String importStatus, LocalDate declarationDate,
			String itemId) {
		this.id = id;
		this.declarationNumber = declarationNumber;
		this.importStatus = importStatus;
		this.declarationDate = declarationDate;
		this.itemId = itemId;
	}

	public static ImportationDto fromEntity(EbmImportation importation) {
		if (importation == null)
			return null;
		return new ImportationDto(importation.getId(), importation.getDeclarationNumber(),
				importation.getImportStatus().getStatus(), importation.getDeclarationDate(),
				importation.getEbmItem() == null ? null : importation.getEbmItem().getId());
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
	 * @return the declarationNumber
	 */
	public String getDeclarationNumber() {
		return declarationNumber;
	}

	/**
	 * @param declarationNumber the declarationNumber to set
	 */
	public void setDeclarationNumber(String declarationNumber) {
		this.declarationNumber = declarationNumber;
	}

	/**
	 * @return the importStatus
	 */
	public String getImportStatus() {
		return importStatus;
	}

	/**
	 * @param importStatus the importStatus to set
	 */
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	/**
	 * @return the declarationDate
	 */
	public LocalDate getDeclarationDate() {
		return declarationDate;
	}

	/**
	 * @param declarationDate the declarationDate to set
	 */
	public void setDeclarationDate(LocalDate declarationDate) {
		this.declarationDate = declarationDate;
	}

	/**
	 * @return the itemId
	 */
	public String getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

}
