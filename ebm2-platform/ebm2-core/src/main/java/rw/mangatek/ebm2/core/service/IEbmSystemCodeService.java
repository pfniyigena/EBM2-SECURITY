package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmSystemCode;

public interface IEbmSystemCodeService {

 public void saveEntity(EbmSystemCode codes);
 
 public void saveEntities(List<EbmSystemCode> codes);

 public List<EbmSystemCode> findAllAsList(Pageable pageable);

}
