package rw.mangatek.ebm2.core.enns;

public enum ImportStatus {
	PENDING("2"), APPROVED("3"), REJECTED("4");

	private String status;

	ImportStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

}
