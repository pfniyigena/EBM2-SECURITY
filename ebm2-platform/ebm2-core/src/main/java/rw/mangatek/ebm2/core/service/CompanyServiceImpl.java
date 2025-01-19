package rw.mangatek.ebm2.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.Company;
import rw.mangatek.ebm2.core.enns.CompanyStatus;
import rw.mangatek.ebm2.core.repository.ICompanyRepository;

@Slf4j
@Service
public class CompanyServiceImpl implements ICompanyService {

	private final ICompanyRepository companyRepository;
	private final ISequenceNumberService sequenceNumberService;

	public CompanyServiceImpl(ICompanyRepository ebmImportationRepository,
			ISequenceNumberService sequenceNumberService) {
		this.companyRepository = ebmImportationRepository;
		this.sequenceNumberService = sequenceNumberService;
	}

	@Override
	public List<Company> findAllAsList(Pageable pageable) {

		return companyRepository.findAll(pageable).getContent();
	}

	@Override
	public Company saveEntity(Company company) {
		Company exist = getByName(company.getName());
		if (exist == null) {
			company.setCode(sequenceNumberService.findNextCompanyCode());
			exist = companyRepository.save(company);
		}
		return exist;
	}

	@Override
	public Company getById(String id) {
		Optional<Company> value = companyRepository.findById(id);

		return value.orElseThrow(null);
	}

	@Override
	public void updateStatus(String id, String importStatus) {
		log.debug(String.format("----------updating %s", importStatus));
		Company company = getById(id);
		company.setStatus(CompanyStatus.valueOf(importStatus));
		companyRepository.save(company);
	}

	@Override
	public Company getByCode(String code) {

		return companyRepository.getByCode(code);
	}

	@Override
	public Company getByName(String name) {

		return companyRepository.getByName(name);
	}

	@Override
	public void updateEntity(Company fromDto) {
		Company company = getById(fromDto.getId());
		company.setStatus(fromDto.getStatus());
		if (fromDto.getTinNumber() != null && !fromDto.getTinNumber().isEmpty()) {
			company.setTinNumber(fromDto.getTinNumber());
		}
		if (fromDto.getAnydesk() != null && !fromDto.getAnydesk().isEmpty()) {
			company.setAnydesk(fromDto.getAnydesk());
		}
		if (fromDto.getAnydeskPwd() != null && !fromDto.getAnydeskPwd().isEmpty()) {
			company.setAnydeskPwd(fromDto.getAnydeskPwd());
		}
		if (fromDto.getPreviousPaymentDate() != null) {
			company.setPreviousPaymentDate(fromDto.getPreviousPaymentDate());
		}
		if (fromDto.getNexPaymentDate() != null) {
			company.setNexPaymentDate(fromDto.getNexPaymentDate());
		}
		if (fromDto.getIpAddress() != null && !fromDto.getIpAddress().isEmpty()) {
			company.setIpAddress(fromDto.getIpAddress());
		}

		if (fromDto.getSyncTime() != null) {
			company.setSyncTime(fromDto.getSyncTime());
		}
		if (fromDto.getCisName() != null && !fromDto.getCisName().isEmpty()) {
			company.setCisName(fromDto.getCisName());
		}
		if (fromDto.getCisVersion() != null && !fromDto.getCisVersion().isEmpty()) {
			company.setCisVersion(fromDto.getCisVersion());
		}
		if (fromDto.getVsdcName() != null && !fromDto.getVsdcName().isEmpty()) {
			company.setVsdcName(fromDto.getVsdcName());
		}
		if (fromDto.getVsdcVersion() != null && !fromDto.getVsdcVersion().isEmpty()) {
			company.setVsdcVersion(fromDto.getVsdcVersion());
		}
		companyRepository.save(company);
	}

}
