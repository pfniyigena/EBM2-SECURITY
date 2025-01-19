package rw.mangatek.ebm2.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import rw.mangatek.ebm2.core.domain.EbmStockMove;

public interface IEbmStockMoveRepository extends JpaRepository<EbmStockMove, String> {
	public List<EbmStockMove> findBySentServer(Boolean sentServer);
	

}
