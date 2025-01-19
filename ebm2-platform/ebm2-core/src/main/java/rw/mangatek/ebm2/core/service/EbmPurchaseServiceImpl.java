package rw.mangatek.ebm2.core.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.domain.EbmPurchase;
import rw.mangatek.ebm2.core.domain.EbmPurchaseItem;
import rw.mangatek.ebm2.core.repository.IEbmPurchaseRepository;

@Slf4j
@Service
public class EbmPurchaseServiceImpl implements IEbmPurchaseService {

	private final IEbmPurchaseRepository ebmPurchaseRepository;
	private final IEbmItemService ebmItemService;

	public EbmPurchaseServiceImpl(IEbmPurchaseRepository ebmPurchaseRepository, IEbmItemService ebmItemService) {
		this.ebmPurchaseRepository = ebmPurchaseRepository;
		this.ebmItemService = ebmItemService;

	}

	@Override
	public EbmPurchase getById(String id) {
		Optional<EbmPurchase> value = ebmPurchaseRepository.findById(id);

		return value.orElseThrow(null);
	}

	@Override
	public EbmPurchase saveNewEbmPurchase(EbmPurchase ebmPurchase) {

		log.debug("------------saveNewEbmPurchase----------------:" + ebmPurchase.getPurchaseItems().size());
		EbmPurchase exist = getByInvoiceNumberAndSupplierBranchAndSupplierTinAndTotalAmount(
				ebmPurchase.getInvoiceNumber(), ebmPurchase.getSupplierBranch(), ebmPurchase.getSupplierTin(),
				ebmPurchase.getTotalAmount());
		if (exist == null) {
			exist = ebmPurchaseRepository.save(ebmPurchase);
			for (EbmPurchaseItem sale : exist.getPurchaseItems()) {
				EbmItem item = ebmItemService.getByItemCode(sale.getItemCode());
				BigDecimal currentStock = new BigDecimal("0.00");
				BigDecimal previousStock = new BigDecimal("0.00");
				// reduce stock
				if (item != null) {
					previousStock = item.getCurrentStock();
					if (previousStock == null) {
						previousStock = new BigDecimal("0.00");
					}
					currentStock = previousStock.add(sale.getQuantity());
					item.setCurrentStock(currentStock);
					item.setStockSent(false);
					ebmItemService.saveNewItem(item);
				}
				// create new item with -1 qte
				else {
					currentStock = previousStock.add(sale.getQuantity());
					EbmItem newItem = EbmItem.builder().itemName(sale.getItemName())
							.itemClassificationCode(sale.getItemClassificationCode()).itemCode(sale.getItemCode())
							.currentStock(currentStock).sentServer(false).stockSent(false).useYn("N").build();
					ebmItemService.saveNewItem(newItem);
				}
			}

		} else {
			log.debug("------------saveNewEbmPurchase exists----------------");
		}

		return exist;
	}

	@Override
	public EbmPurchase updateEbmPurchase(EbmPurchase ebmPurchase, boolean sentToServer) {
		ebmPurchase.setSentServer(sentToServer);
		return ebmPurchaseRepository.save(ebmPurchase);
	}

	@Override
	public List<EbmPurchase> findAllAsList(Pageable pageable) {

		return ebmPurchaseRepository.findAll(pageable).getContent();
	}

	@Override
	public EbmPurchase getByInvoiceNumberAndSupplierBranchAndSupplierTinAndTotalAmount(Long invoiceNumber,
			String supplierBranch, String supplierTin, BigDecimal totalAmount) {

		return ebmPurchaseRepository.getByInvoiceNumberAndSupplierBranchAndSupplierTinAndTotalAmount(invoiceNumber,
				supplierBranch, supplierTin, totalAmount);
	}

	@Override
	public List<EbmPurchase> findBySentServer(boolean b) {

		return ebmPurchaseRepository.findBySentServer(b);
	}

}
