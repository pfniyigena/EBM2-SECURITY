package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmUser;

public interface IEbmUserService {

	public EbmUser saveEntity(EbmUser user);
	public EbmUser updateEntity(EbmUser user);
	public void saveEntities(List<EbmUser> users);

	public Iterable<EbmUser> findAll(Pageable pageable);

	public List<EbmUser> findAllAsList(Pageable pageable);
	public EbmUser getById(String id);
	EbmUser updateEntityWithSentToServer(EbmUser user);

}
