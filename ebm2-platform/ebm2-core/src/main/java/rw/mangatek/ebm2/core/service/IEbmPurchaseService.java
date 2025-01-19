package rw.mangatek.ebm2.core.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmPurchase;

public interface IEbmPurchaseService {

	public EbmPurchase saveNewEbmPurchase(EbmPurchase ebmPurchase);

	public EbmPurchase updateEbmPurchase(EbmPurchase ebmPurchase, boolean sentToServer);

	public List<EbmPurchase> findAllAsList(Pageable pageable);

	public EbmPurchase getById(String id);

	public EbmPurchase getByInvoiceNumberAndSupplierBranchAndSupplierTinAndTotalAmount(Long invoiceNumber,
			String supplierBranch, String supplierTin, BigDecimal totalAmount);

	public List<EbmPurchase> findBySentServer(boolean b);
}
