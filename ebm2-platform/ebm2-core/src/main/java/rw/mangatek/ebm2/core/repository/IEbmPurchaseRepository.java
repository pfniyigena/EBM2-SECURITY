package rw.mangatek.ebm2.core.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import rw.mangatek.ebm2.core.domain.EbmPurchase;

public interface IEbmPurchaseRepository extends JpaRepository<EbmPurchase, String> {

	EbmPurchase getByInvoiceNumberAndSupplierBranchAndSupplierTinAndTotalAmount(Long invoiceNumber,
			String supplierBranch, String supplierTin, BigDecimal totalAmount);

	public List<EbmPurchase> findBySentServer(Boolean sentServer);

}
