package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmItem;

public interface IEbmItemService {

	public List<EbmItem> findAllAsList(Pageable pageable);

	public void saveList(List<EbmItem> list);

	public EbmItem saveNewItem(EbmItem classification);

	public List<EbmItem> findBySentServer(boolean sentServer);
	public List<EbmItem> findByStockSent(boolean stockSent);
	public EbmItem getByItemCode(String itemCode);

	public void updateSentItemToSever(EbmItem ebmItem);
	
	public List<EbmItem> findByItemName(String term);
	public EbmItem getById(String id);

	public void updateStock(List<EbmItem> items);

	public void updateSentStockToSever(EbmItem ebmItem);
}
