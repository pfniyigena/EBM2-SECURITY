package rw.mangatek.ebm2.core.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmInvoice;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.domain.EbmSaleItem;
import rw.mangatek.ebm2.core.repository.IEbmInvoiceRepository;

@Slf4j
@Service
public class EbmInvoiceServiceImpl implements IEbmInvoiceService {

	private final IEbmInvoiceRepository ebmInvoiceRepository;
	private final IEbmItemService ebmItemService;
	private final IEbmCustomerService ebmCustomerService;

	public EbmInvoiceServiceImpl(IEbmInvoiceRepository ebmInvoiceRepository, IEbmItemService ebmItemService,
			IEbmCustomerService ebmCustomerService) {
		this.ebmInvoiceRepository = ebmInvoiceRepository;
		this.ebmItemService = ebmItemService;
		this.ebmCustomerService = ebmCustomerService;
	}

	@Override
	public EbmInvoice saveNewInvoice(EbmInvoice ebmInvoice) {
		log.debug(String.format("------------saveNewInvoice----------------:%s", ebmInvoice));
		EbmInvoice savedInvoice = ebmInvoiceRepository.save(ebmInvoice);
		for (EbmSaleItem sale : savedInvoice.getSaleItems()) {
			EbmItem item = ebmItemService.getByItemCode(sale.getItemCode());
			BigDecimal currentStock = new BigDecimal("0.00");
			BigDecimal previousStock = new BigDecimal("0.00");
			// reduce stock
			if (item != null) {
				previousStock = item.getCurrentStock();
				if (previousStock == null) {
					previousStock = new BigDecimal("0.00");
				}
				currentStock = previousStock.subtract(sale.getQuantity());
				item.setCurrentStock(currentStock);
				item.setStockSent(false);
				ebmItemService.saveNewItem(item);
			}
			// create new item with -1 qte
			else {
				currentStock = previousStock.subtract(sale.getQuantity());
				EbmItem newItem = EbmItem.builder().itemName(sale.getItemName())
						.itemClassificationCode(sale.getItemClassificationCode()).itemCode(sale.getItemCode())
						.currentStock(currentStock).sentServer(false).stockSent(false).useYn("N").build();
				ebmItemService.saveNewItem(newItem);
			}
		}
		ebmCustomerService.saveCustomer(ebmInvoice.getCustomerName(), ebmInvoice.getCustomerTin(),
				ebmInvoice.getCustomerPhone(), ebmInvoice.getRegisteredTin(),ebmInvoice.getBranchId());
		return savedInvoice;
	}

	@Override
	public EbmInvoice updateInvoice(EbmInvoice ebmInvoice, boolean sentToServer) {
		log.debug(String.format("------------updateInvoice----------------:{%s}", sentToServer));
		ebmInvoice.setSentServer(sentToServer);
		return ebmInvoiceRepository.save(ebmInvoice);
	}

	@Override
	public List<EbmInvoice> findAllAsList(Pageable pageable) {
		log.debug("------------findAllAsList----------------");
		return ebmInvoiceRepository.findAll(pageable).getContent();
	}

	@Override
	public EbmInvoice getById(String id) {
		Optional<EbmInvoice> value = ebmInvoiceRepository.findById(id);

		return value.orElseThrow(null);
	}

}
