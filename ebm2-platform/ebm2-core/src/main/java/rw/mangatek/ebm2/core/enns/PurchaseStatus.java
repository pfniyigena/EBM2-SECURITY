package rw.mangatek.ebm2.core.enns;

public enum PurchaseStatus {
	PENDING("02"), APPROVED("03"), REJECTED("04");

	private String status;

	PurchaseStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

}
