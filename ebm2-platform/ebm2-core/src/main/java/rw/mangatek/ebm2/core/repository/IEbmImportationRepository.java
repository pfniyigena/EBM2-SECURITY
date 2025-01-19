package rw.mangatek.ebm2.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import rw.mangatek.ebm2.core.domain.EbmImportation;

public interface IEbmImportationRepository extends JpaRepository<EbmImportation, String> {

	public EbmImportation getByDeclarationNumberAndHsCode(String declarationNumber,String hsCode);
	public List<EbmImportation> findBySentServer(Boolean sentServer);
}
