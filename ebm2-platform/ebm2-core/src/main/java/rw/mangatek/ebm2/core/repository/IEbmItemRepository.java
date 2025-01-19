package rw.mangatek.ebm2.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;



import rw.mangatek.ebm2.core.domain.EbmItem;

public interface IEbmItemRepository extends JpaRepository<EbmItem, String> {
	@Query("select p from EbmItem p where p.itemName like %?1%")
	public List<EbmItem> findByItemName(String term);
	public EbmItem getByItemCode(String itemCode);
	public EbmItem getById(String id);
	public List<EbmItem> findBySentServer(Boolean sentServer);
	
	public List<EbmItem> findByStockSent(Boolean stockSent);
}
