package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;


import rw.mangatek.ebm2.core.domain.CompanyMonitor;

public interface ICompanyMonitorService {
	public CompanyMonitor saveEntity(CompanyMonitor companyMonitor);
	public List<CompanyMonitor> findAllAsList(Pageable pageable);


}
