package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmItemClassification;

public interface IEbmItemClassificationService {

	public List<EbmItemClassification> findAllAsList(Pageable pageable);

	public void saveList(List<EbmItemClassification> list);

	public EbmItemClassification saveNewClassification(EbmItemClassification classification);
}
