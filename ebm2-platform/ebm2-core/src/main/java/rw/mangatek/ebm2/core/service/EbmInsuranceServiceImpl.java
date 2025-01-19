package rw.mangatek.ebm2.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmInsurance;
import rw.mangatek.ebm2.core.repository.IEbmInsuranceRepository;

@Slf4j
@Service
public class EbmInsuranceServiceImpl implements IEbmInsuranceService {

	private final IEbmInsuranceRepository ebmInsuranceRepository;

	public EbmInsuranceServiceImpl(IEbmInsuranceRepository ebmInsuranceRepository) {
		this.ebmInsuranceRepository = ebmInsuranceRepository;
	}

	@Override
	public EbmInsurance saveEntity(EbmInsurance insurance) {
		log.debug("------------saveEntity----------------");
		EbmInsurance saved = ebmInsuranceRepository.getByInsuranceCode(insurance.getInsuranceCode());
		if ( saved== null)
			saved=ebmInsuranceRepository.save(insurance);
		return saved;
	}

	@Override
	public void saveEntities(List<EbmInsurance> insurances) {
		for (EbmInsurance insurance : insurances) {
			this.saveEntity(insurance);
		}

	}

	@Override
	public Iterable<EbmInsurance> findAll(Pageable pageable) {

		return ebmInsuranceRepository.findAll(pageable);
	}

	@Override
	public List<EbmInsurance> findAllAsList(Pageable pageable) {

		return ebmInsuranceRepository.findAll(pageable).getContent();
	}

	@Override
	public EbmInsurance updateEntityWithSentToServer(EbmInsurance user) {
		log.debug("------------updateEntityWithSentToServer----------------");
		EbmInsurance ebmUser = getById(user.getId());
		ebmUser.setSentServer(true);
		return ebmInsuranceRepository.save(ebmUser);
	}

	@Override
	public EbmInsurance getById(String id) {
		Optional<EbmInsurance> value = ebmInsuranceRepository.findById(id);

		return value.orElseThrow(null);
	}

	@Override
	public EbmInsurance updateEntity(EbmInsurance insurance) {
		EbmInsurance ebmUser = getById(insurance.getId());
		ebmUser.setInsuranceName(insurance.getInsuranceName());
		ebmUser.setSentServer(false);
		return ebmInsuranceRepository.save(ebmUser);
	}
}
