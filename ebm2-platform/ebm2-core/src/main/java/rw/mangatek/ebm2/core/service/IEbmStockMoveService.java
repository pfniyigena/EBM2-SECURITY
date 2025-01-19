package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmStockMove;
import rw.mangatek.ebm2.core.domain.EbmStockMoveItem;

public interface IEbmStockMoveService {

	public void saveEntity(EbmStockMove type);

	public void saveEntities(List<EbmStockMove> types);
	public void saveEntity(List<EbmStockMoveItem> items,String branchId);
	public Iterable<EbmStockMove> findAll(Pageable pageable);

	public List<EbmStockMove> findAllAsList(Pageable pageable);
	public List<EbmStockMove> findBySentServer(Boolean sentServer);
	public EbmStockMove getById(String id);

	public void updateStockSent(EbmStockMove move);

}
