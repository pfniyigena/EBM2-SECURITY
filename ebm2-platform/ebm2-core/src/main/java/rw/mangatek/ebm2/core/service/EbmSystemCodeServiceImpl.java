package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmCodeType;
import rw.mangatek.ebm2.core.domain.EbmSystemCode;
import rw.mangatek.ebm2.core.repository.IEbmCodeTypeRepository;
import rw.mangatek.ebm2.core.repository.IEbmSystemCodeRepository;

@Slf4j
@Service
public class EbmSystemCodeServiceImpl implements IEbmSystemCodeService {

	private final IEbmCodeTypeRepository ebmCodeTypeRepository;
	private final IEbmSystemCodeRepository ebmSystemCodeRepository;

	public EbmSystemCodeServiceImpl(IEbmCodeTypeRepository ebmCodeTypeRepository,
			IEbmSystemCodeRepository ebmSystemCodeRepository) {
		this.ebmCodeTypeRepository = ebmCodeTypeRepository;
		this.ebmSystemCodeRepository = ebmSystemCodeRepository;
	}

	@Override
	public void saveEntity(EbmSystemCode code) {
		log.debug("------------saveEntity----------------");
		EbmCodeType type = ebmCodeTypeRepository.getByCode(code.getTypeCode());
		if (ebmSystemCodeRepository.getByCode(code.getCode()) == null) {
			code.setType(type);
			ebmSystemCodeRepository.save(code);
		}

	}

	@Override
	public void saveEntities(List<EbmSystemCode> codes) {
		for (EbmSystemCode code : codes) {
			saveEntity(code);
		}

	}
	 
	@Override
	public List<EbmSystemCode> findAllAsList(Pageable pageable) {

		return ebmSystemCodeRepository.findAll(pageable).getContent();
	}
}
