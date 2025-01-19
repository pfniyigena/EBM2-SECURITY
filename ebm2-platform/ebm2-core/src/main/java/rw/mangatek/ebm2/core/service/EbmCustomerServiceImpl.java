package rw.mangatek.ebm2.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmCustomer;
import rw.mangatek.ebm2.core.repository.IEbmCustomerRepository;
import rw.mangatek.ebm2.core.util.Ebm2StaticValue;

@Slf4j
@Service
public class EbmCustomerServiceImpl implements IEbmCustomerService {

	private final IEbmCustomerRepository ebmCustomerRepository;

	public EbmCustomerServiceImpl(IEbmCustomerRepository ebmCodeTypeRepository) {
		this.ebmCustomerRepository = ebmCodeTypeRepository;
	}

	@Override
	public EbmCustomer saveEntity(EbmCustomer customer) {
		EbmCustomer savedCustomer = null;
		log.debug("------------saveEntity----------------");
		savedCustomer = ebmCustomerRepository.getByCustomerTin(customer.getCustomerTin());
		if (savedCustomer == null)
			savedCustomer = ebmCustomerRepository.save(customer);
		return savedCustomer;
	}

	@Override
	public void saveEntities(List<EbmCustomer> customers) {
		for (EbmCustomer customer : customers) {
			this.saveEntity(customer);
		}

	}

	@Override
	public Iterable<EbmCustomer> findAll(Pageable pageable) {

		return ebmCustomerRepository.findAll(pageable);
	}

	@Override
	public List<EbmCustomer> findAllAsList(Pageable pageable) {

		return ebmCustomerRepository.findAll(pageable).getContent();
	}

	@Override
	public EbmCustomer updateEntityWithSentToServer(EbmCustomer customer) {
		log.debug("------------updateEntityWithSentToServer----------------");
		
		customer.setSentServer(true);
		return ebmCustomerRepository.save(customer);
	}

	@Override
	public EbmCustomer getById(String id) {
		Optional<EbmCustomer> value = ebmCustomerRepository.findById(id);

		return value.orElseThrow(null);
	}

	@Override
	public EbmCustomer updateEntity(EbmCustomer customer) {
		EbmCustomer ebmCustomer = getById(customer.getId());
		ebmCustomer.setCustomerEmail(customer.getCustomerEmail());
		ebmCustomer.setSentServer(false);
		return ebmCustomerRepository.save(ebmCustomer);
	}

	@Override
	public void saveCustomer(String customerName, String customerTin, String customerPhone, String registeredTin,
			String branchId) {
		try {
			if (customerTin != null && !customerTin.isEmpty()) {
				EbmCustomer savedCustomer = ebmCustomerRepository.getByCustomerTin(customerTin);
				if (savedCustomer == null) {
					ebmCustomerRepository.save(EbmCustomer.builder().customerTin(customerTin)
							.customerPhone(customerPhone).customerName(customerName).sentServer(false).faxNo("")
							.useYn(Ebm2StaticValue.USE_YES_NO).registeredTin(registeredTin).branchId(branchId).build());
				}
			}
		} catch (Exception e) {
			log.debug(e.toString());
			e.printStackTrace();

		}

	}

	@Override
	public List<EbmCustomer> findBySentServer(Boolean sentServer) {

		return ebmCustomerRepository.findBySentServer(sentServer);
	}

}
