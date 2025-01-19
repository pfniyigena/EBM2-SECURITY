package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmNotice;
import rw.mangatek.ebm2.core.repository.IEbmNoticeRepository;

@Slf4j
@Service
public class EbmNoticeServiceImpl implements IEbmNoticeService {

	private final IEbmNoticeRepository ebmNoticeRepository;

	public EbmNoticeServiceImpl(IEbmNoticeRepository ebmNoticeRepository) {
		this.ebmNoticeRepository = ebmNoticeRepository;
	}

	@Override
	public void saveEntity(EbmNotice type) {
		log.debug("------------saveEntity----------------");
		ebmNoticeRepository.save(type);
			

	}

	@Override
	public void saveEntities(List<EbmNotice> types) {
		for (EbmNotice type : types) {
			this.saveEntity(type);
		}

	}
	@Override
	public Iterable<EbmNotice> findAll(Pageable pageable) {

		return ebmNoticeRepository.findAll(pageable) ;
	}
	@Override
	public List<EbmNotice> findAllAsList(Pageable pageable) {

		return ebmNoticeRepository.findAll(pageable).getContent();
	}
}
