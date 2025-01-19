package rw.mangatek.ebm2.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import rw.mangatek.ebm2.core.domain.EbmTaxpayer;
import rw.mangatek.ebm2.core.domain.EbmTaxpayerBranch;

public interface IEbmTaxpayerBranchRepository extends JpaRepository<EbmTaxpayerBranch, String> {

	EbmTaxpayerBranch getByBranchIdAndTaxpayer(String branchId,EbmTaxpayer taxpayer);
	
	
}
