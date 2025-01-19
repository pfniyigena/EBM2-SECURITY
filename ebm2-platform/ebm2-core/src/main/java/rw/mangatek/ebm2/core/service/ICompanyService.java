package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.Company;

public interface ICompanyService {
	public Company saveEntity(Company ebmImportation);

	public Company getByCode(String code);
	public Company getByName(String name);

	public List<Company> findAllAsList(Pageable pageable);

	public Company getById(String id);

	public void updateStatus(String id, String status);

	public void updateEntity(Company fromDto);


}
