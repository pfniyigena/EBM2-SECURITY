package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmCodeType;
import rw.mangatek.ebm2.core.repository.IEbmCodeTypeRepository;

@Slf4j
@Service
public class EbmCodeTypeServiceImpl implements IEbmCodeTypeService {

	private final IEbmCodeTypeRepository ebmCodeTypeRepository;

	public EbmCodeTypeServiceImpl(IEbmCodeTypeRepository ebmCodeTypeRepository) {
		this.ebmCodeTypeRepository = ebmCodeTypeRepository;
	}

	@Override
	public void saveEntity(EbmCodeType type) {
		log.debug("------------saveEntity----------------");
		if (ebmCodeTypeRepository.getByCode(type.getCode()) == null)
			ebmCodeTypeRepository.save(type);

	}

	@Override
	public void saveEntities(List<EbmCodeType> types) {
		for (EbmCodeType type : types) {
			this.saveEntity(type);
		}

	}
	@Override
	public Iterable<EbmCodeType> findAll(Pageable pageable) {

		return ebmCodeTypeRepository.findAll(pageable) ;
	}
	@Override
	public List<EbmCodeType> findAllAsList(Pageable pageable) {

		return ebmCodeTypeRepository.findAll(pageable).getContent();
	}
}
