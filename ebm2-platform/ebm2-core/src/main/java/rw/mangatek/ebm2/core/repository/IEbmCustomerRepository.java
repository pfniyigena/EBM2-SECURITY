package rw.mangatek.ebm2.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import rw.mangatek.ebm2.core.domain.EbmCustomer;

public interface IEbmCustomerRepository extends JpaRepository<EbmCustomer, String> {

	EbmCustomer getByCustomerTin(String customerTin);

	List<EbmCustomer> findBySentServer(Boolean sentServer);

}
