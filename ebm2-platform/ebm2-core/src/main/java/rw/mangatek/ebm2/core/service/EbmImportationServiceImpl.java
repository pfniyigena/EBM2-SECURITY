package rw.mangatek.ebm2.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmImportation;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.enns.ImportStatus;
import rw.mangatek.ebm2.core.repository.IEbmImportationRepository;

@Slf4j
@Service
public class EbmImportationServiceImpl implements IEbmImportationService {

	private final IEbmImportationRepository ebmImportationRepository;
	private final IEbmItemService ebmItemService;
	public EbmImportationServiceImpl(IEbmImportationRepository ebmImportationRepository,IEbmItemService ebmItemService) {
		this.ebmImportationRepository = ebmImportationRepository;
		this.ebmItemService = ebmItemService;
	}

	@Override
	public EbmImportation getByDeclarationNumberAndHsCode(String declarationNumber, String hsCode) {
		log.debug(String.format(
				"------------getByDeclarationNumberAndHsCode---------------DeclarationNumber:%s,hsCode:%s",
				declarationNumber, hsCode));
		return ebmImportationRepository.getByDeclarationNumberAndHsCode(declarationNumber, hsCode);
	}

	@Override
	public List<EbmImportation> findAllAsList(Pageable pageable) {

		return ebmImportationRepository.findAll(pageable).getContent();
	}

	@Override
	public EbmImportation saveEntity(EbmImportation ebmImportation) {
		EbmImportation exist = getByDeclarationNumberAndHsCode(ebmImportation.getDeclarationNumber(),
				ebmImportation.getHsCode());
		if (exist == null) {
			exist = ebmImportationRepository.save(ebmImportation);
		}
		return exist;
	}

	@Override
	public EbmImportation getById(String id) {
		Optional<EbmImportation> value = ebmImportationRepository.findById(id);

		return value.orElseThrow(null);
	}

	@Override
	public void updateStatus(String id, String importStatus,String itemId) {
		EbmImportation ebmImportation = getById(id);
        EbmItem item=ebmItemService.getById(itemId);
        ebmImportation.setEbmItem(item);
		ebmImportation.setImportStatus(ImportStatus.valueOf(importStatus));
		ebmImportation.setSentServer(false);
		ebmImportationRepository.save(ebmImportation);
	}

	@Override
	public void updateSentToServer(EbmImportation ebmImportation) {
		ebmImportation.setSentServer(true);
		ebmImportationRepository.save(ebmImportation);

	}

	@Override
	public List<EbmImportation> findBySentServer(Boolean sentServer) {
		return ebmImportationRepository.findBySentServer(sentServer);
	}
}
