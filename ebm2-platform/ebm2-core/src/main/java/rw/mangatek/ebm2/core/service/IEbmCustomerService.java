package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmCustomer;

public interface IEbmCustomerService {

	public EbmCustomer saveEntity(EbmCustomer customer);
	public EbmCustomer updateEntity(EbmCustomer customer);
	public void saveEntities(List<EbmCustomer> customers);

	public Iterable<EbmCustomer> findAll(Pageable pageable);

	public List<EbmCustomer> findAllAsList(Pageable pageable);
	public EbmCustomer getById(String id);
	EbmCustomer updateEntityWithSentToServer(EbmCustomer customer);
	public void saveCustomer(String customerName, String customerTin, String customerPhone,String registeredTin,String branchId);
	public List<EbmCustomer> findBySentServer(Boolean sentServer);

}
