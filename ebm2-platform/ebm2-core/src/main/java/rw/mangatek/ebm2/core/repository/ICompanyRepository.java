package rw.mangatek.ebm2.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import rw.mangatek.ebm2.core.domain.Company;

public interface ICompanyRepository extends JpaRepository<Company, String> {

	public Company getByCode(String code);

	public Company getByName(String name);

}
