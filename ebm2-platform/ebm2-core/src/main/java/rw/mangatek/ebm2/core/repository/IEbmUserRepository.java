package rw.mangatek.ebm2.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import rw.mangatek.ebm2.core.domain.EbmUser;

public interface IEbmUserRepository extends JpaRepository<EbmUser, String> {

	EbmUser getByUsername(String username);
	EbmUser getByAuthorityCode(String authorityCode);
	

}
