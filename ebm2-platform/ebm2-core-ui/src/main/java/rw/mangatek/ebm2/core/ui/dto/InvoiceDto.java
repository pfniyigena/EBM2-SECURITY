/**
 * 
 */
package rw.mangatek.ebm2.core.ui.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pfniy
 *
 */
public class InvoiceDto {
	private String id;
	private String customerId;
	private String supplierBranchId;
	private String branchId;
	private List<SaleCartDto> sales = new ArrayList<>();

	public InvoiceDto() {
	}

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the sales
	 */
	public List<SaleCartDto> getSales() {
		return sales;
	}

	/**
	 * @param sales the sales to set
	 */
	public void setSales(List<SaleCartDto> sales) {
		this.sales = sales;
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
	 * @return the supplierBranchId
	 */
	public String getSupplierBranchId() {
		return supplierBranchId;
	}

	/**
	 * @param supplierBranchId the supplierBranchId to set
	 */
	public void setSupplierBranchId(String supplierBranchId) {
		this.supplierBranchId = supplierBranchId;
	}

}
