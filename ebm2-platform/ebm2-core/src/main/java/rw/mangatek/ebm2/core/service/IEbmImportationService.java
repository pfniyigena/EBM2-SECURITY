package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmImportation;

public interface IEbmImportationService {
	public EbmImportation saveEntity(EbmImportation ebmImportation);

	public EbmImportation getByDeclarationNumberAndHsCode(String declarationNumber,String hsCode);

	public List<EbmImportation> findAllAsList(Pageable pageable);

	public EbmImportation getById(String id);

	public void updateStatus(String id, String importStatus,String itemId);

	public void updateSentToServer(EbmImportation ebmImportation);

	public List<EbmImportation> findBySentServer(Boolean sentServer);

}
