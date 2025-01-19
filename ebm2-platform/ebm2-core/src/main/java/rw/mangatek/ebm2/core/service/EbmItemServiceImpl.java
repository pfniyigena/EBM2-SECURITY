package rw.mangatek.ebm2.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.repository.IEbmItemRepository;

@Slf4j
@Service
public class EbmItemServiceImpl implements IEbmItemService {
	private final IEbmItemRepository ebmItemRepository;

	public EbmItemServiceImpl(IEbmItemRepository ebmItemRepository) {
		this.ebmItemRepository = ebmItemRepository;
	}

	@Override
	public List<EbmItem> findAllAsList(Pageable pageable) {
		log.debug("------------findAllAsList----------------");
		return ebmItemRepository.findAll(pageable).getContent();
	}

	@Override
	public void saveList(List<EbmItem> list) {
		log.debug("------------saveList----------------");
		for (EbmItem classification : list) {
			saveNewItem(classification);
		}

	}

	@Override
	public EbmItem saveNewItem(EbmItem classification) {
		log.debug("------------saveNew----------------");
		EbmItem newClass = null;
		newClass = ebmItemRepository.getByItemCode(classification.getItemCode());
		if (newClass == null) {
			newClass = ebmItemRepository.save(classification);
		} else {
			log.debug("------------ exists----------------:" + classification.getItemName());
			newClass.setSentServer(classification.getSentServer());
			ebmItemRepository.save(newClass);
		}
		return newClass;
	}

	@Override
	public List<EbmItem> findBySentServer(boolean sentServer) {
		log.debug("------------findBySentServer----------------:" + sentServer);
		return ebmItemRepository.findBySentServer(sentServer);
	}

	@Override
	public EbmItem getByItemCode(String itemCode) {

		return ebmItemRepository.getByItemCode(itemCode);
	}
	@Override
	public void updateStock(List<EbmItem> items) {
		ebmItemRepository.saveAll(items);
	}

	@Override
	public void updateSentItemToSever(EbmItem ebmItem) {
		EbmItem item = getByItemCode(ebmItem.getItemCode());
		item.setSentServer(true);

		ebmItemRepository.save(item);
	}
	@Override
	public void updateSentStockToSever(EbmItem ebmItem) {
		log.debug("------------updateSentStockToSever----------------");
		EbmItem item = getByItemCode(ebmItem.getItemCode());
		item.setStockSent(true);

		ebmItemRepository.save(item);
	}
	@Override
	public List<EbmItem> findByItemName(String term) {

		return ebmItemRepository.findByItemName(term);
	}

	@Override
	public EbmItem getById(String id) {

		Optional<EbmItem> value = ebmItemRepository.findById(id);

		return value.orElseThrow(null);

	}

	@Override
	public List<EbmItem> findByStockSent(boolean stockSent) {
		log.debug("------------findByStockSent----------------:" + stockSent);
		return ebmItemRepository.findByStockSent(stockSent);
	}

}
