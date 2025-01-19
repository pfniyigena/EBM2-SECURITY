package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.CompanyMonitor;
import rw.mangatek.ebm2.core.repository.ICompanyMonitoringRepository;

@Slf4j
@Service
public class CompanyMonitorServiceImpl implements ICompanyMonitorService {

	private final ICompanyMonitoringRepository companyMonitoringRepository;

	public CompanyMonitorServiceImpl(ICompanyMonitoringRepository companyMonitoringRepository) {

		this.companyMonitoringRepository = companyMonitoringRepository;
	}

	@Override
	public CompanyMonitor saveEntity(CompanyMonitor companyMonitor) {
		log.debug("saveEntity");
		return companyMonitoringRepository.save(companyMonitor);
	}

	@Override
	public List<CompanyMonitor> findAllAsList(Pageable pageable) {
		return companyMonitoringRepository.findAll(pageable).getContent();
	}

}
