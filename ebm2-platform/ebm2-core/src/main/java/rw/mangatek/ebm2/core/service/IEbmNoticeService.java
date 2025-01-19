package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmNotice;

public interface IEbmNoticeService {

	public void saveEntity(EbmNotice type);

	public void saveEntities(List<EbmNotice> types);

	public Iterable<EbmNotice> findAll(Pageable pageable);

	public List<EbmNotice> findAllAsList(Pageable pageable);

}
