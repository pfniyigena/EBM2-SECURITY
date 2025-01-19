package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmItemClassification;
import rw.mangatek.ebm2.core.repository.IEbmItemClassificationRepository;

@Slf4j
@Service
public class EbmItemClassificationServiceImpl implements IEbmItemClassificationService {
	private final IEbmItemClassificationRepository ebmItemClassificationRepository;

	public EbmItemClassificationServiceImpl(IEbmItemClassificationRepository ebmItemClassificationRepository) {
		this.ebmItemClassificationRepository = ebmItemClassificationRepository;
	}

	@Override
	public List<EbmItemClassification> findAllAsList(Pageable pageable) {
		log.debug("------------findAllAsList----------------");
		return ebmItemClassificationRepository.findAll(pageable).getContent();
	}

	@Override
	public void saveList(List<EbmItemClassification> list) {
		log.debug("------------saveList----------------");
		for (EbmItemClassification classification : list) {
			saveNewClassification(classification);
		}

	}

	@Override
	public EbmItemClassification saveNewClassification(EbmItemClassification classification) {
		log.debug("------------saveNewClassification----------------");
		EbmItemClassification newClass = null;
		if (ebmItemClassificationRepository.getByCode(classification.getCode()) == null) {
			newClass = ebmItemClassificationRepository.save(classification);
		}else {
			log.debug("------------Classification exists----------------");
		}
		return newClass;
	}

}
