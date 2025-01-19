package rw.mangatek.ebm2.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmUser;
import rw.mangatek.ebm2.core.repository.IEbmUserRepository;

@Slf4j
@Service
public class EbmUserServiceImpl implements IEbmUserService {

	private final IEbmUserRepository ebmUserRepository;

	public EbmUserServiceImpl(IEbmUserRepository ebmCodeTypeRepository) {
		this.ebmUserRepository = ebmCodeTypeRepository;
	}

	@Override
	public EbmUser saveEntity(EbmUser customer) {
		EbmUser savedUser = null;
		log.debug("------------saveEntity----------------");
		savedUser = ebmUserRepository.getByAuthorityCode(customer.getAuthorityCode());
		if (savedUser == null)
			savedUser = ebmUserRepository.save(customer);
		return savedUser;
	}

	@Override
	public void saveEntities(List<EbmUser> customers) {
		for (EbmUser customer : customers) {
			this.saveEntity(customer);
		}

	}

	@Override
	public Iterable<EbmUser> findAll(Pageable pageable) {

		return ebmUserRepository.findAll(pageable);
	}

	@Override
	public List<EbmUser> findAllAsList(Pageable pageable) {

		return ebmUserRepository.findAll(pageable).getContent();
	}

	@Override
	public EbmUser updateEntityWithSentToServer(EbmUser user) {
		log.debug("------------updateEntityWithSentToServer----------------");
		EbmUser ebmUser = getById(user.getId());
		ebmUser.setSentServer(true);
		return ebmUserRepository.save(ebmUser);
	}

	@Override
	public EbmUser getById(String id) {
		Optional<EbmUser> value = ebmUserRepository.findById(id);

		return value.orElseThrow(null);
	}

	@Override
	public EbmUser updateEntity(EbmUser user) {
		EbmUser ebmUser = getById(user.getId());
		ebmUser.setUserAddress(user.getUserAddress());
		ebmUser.setUserPhone(user.getUserPhone());
		ebmUser.setSentServer(false);
		return ebmUserRepository.save(ebmUser);
	}
}
