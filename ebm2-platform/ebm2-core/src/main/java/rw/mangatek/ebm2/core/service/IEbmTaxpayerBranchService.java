package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmTaxpayerBranch;

public interface IEbmTaxpayerBranchService {
	EbmTaxpayerBranch getByBranchIdAndTaxpayer(String branchId, String tin);

	public List<EbmTaxpayerBranch> findAllAsList(Pageable pageable);

	EbmTaxpayerBranch saveNewBranch(String taxpayerTin, String branchId, String branchName, String branchStatusCode,
			String provinceName, String districtName, String sectorName, String locationDescription, String headQuarter,
			String managerName, String managerContactNumber, String manageEmail);
}
