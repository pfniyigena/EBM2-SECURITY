package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmInsurance;

public interface IEbmInsuranceService {

	public EbmInsurance saveEntity(EbmInsurance insurance);

	public void saveEntities(List<EbmInsurance> insurances);

	public Iterable<EbmInsurance> findAll(Pageable pageable);

	public List<EbmInsurance> findAllAsList(Pageable pageable);

	public EbmInsurance getById(String id);

	public EbmInsurance updateEntity(EbmInsurance insurance);

	public EbmInsurance updateEntityWithSentToServer(EbmInsurance insurance);

}
