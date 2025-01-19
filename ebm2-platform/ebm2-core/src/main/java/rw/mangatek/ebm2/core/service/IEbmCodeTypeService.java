package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmCodeType;

public interface IEbmCodeTypeService {

	public void saveEntity(EbmCodeType type);

	public void saveEntities(List<EbmCodeType> types);

	public Iterable<EbmCodeType> findAll(Pageable pageable);

	public List<EbmCodeType> findAllAsList(Pageable pageable);

}
