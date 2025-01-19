package rw.mangatek.ebm2.core.enns;

public enum CompanyStatus {
	PENDING("1"), ACTIVE("2"), LOCKED("3"), DISABLED("4");

	private String status;

	CompanyStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

}
