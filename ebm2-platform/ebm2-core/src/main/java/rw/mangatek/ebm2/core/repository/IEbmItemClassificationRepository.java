package rw.mangatek.ebm2.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import rw.mangatek.ebm2.core.domain.EbmItemClassification;

public interface IEbmItemClassificationRepository extends JpaRepository<EbmItemClassification, String> {

	EbmItemClassification getByCode(String code);

}
