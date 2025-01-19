package rw.mangatek.ebm2.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmSetting;
import rw.mangatek.ebm2.core.enns.SettingName;
import rw.mangatek.ebm2.core.repository.IEbmSettingRepository;

@Slf4j
@Service
public class EbmSettingServiceImpl implements IEbmSettingService {

	private final IEbmSettingRepository ebmEbmSettingRepository;

	public EbmSettingServiceImpl(IEbmSettingRepository ebmEbmSettingRepository) {
		this.ebmEbmSettingRepository = ebmEbmSettingRepository;
	}

	@Override
	public void saveEntity(EbmSetting type) {
		log.debug("------------saveEntity----------------");
		if (ebmEbmSettingRepository.getBySettingName(type.getSettingName()) == null)
			ebmEbmSettingRepository.save(type);

	}

	@Override
	public void saveEntities(List<EbmSetting> types) {
		for (EbmSetting type : types) {
			this.saveEntity(type);
		}

	}

	@Override
	public Iterable<EbmSetting> findAll(Pageable pageable) {

		return ebmEbmSettingRepository.findAll(pageable);
	}

	@Override
	public List<EbmSetting> findAllAsList(Pageable pageable) {

		return ebmEbmSettingRepository.findAll(pageable).getContent();
	}

	@Override
	public void initialize() {
		List<String> settingNames = getDefaultSettingNames();
		for (String name : settingNames) {

			saveEntity(EbmSetting.builder().settingName(name).settingValue("1").build());

		}

	}

	private List<String> getDefaultSettingNames() {
		List<String> permissionNames = new ArrayList<>();
		permissionNames.add(SettingName.INVOICE_NUMBER.name());
		permissionNames.add(SettingName.CURRENT_TAXPAYER_TIN.name());
		permissionNames.add(SettingName.PULL_DATE.name());
		permissionNames.add(SettingName.CURRENT_BRANCH.name());
		permissionNames.add(SettingName.CURRENT_MRC.name());
		permissionNames.add(SettingName.CURRENT_DEVICE.name());
		permissionNames.add(SettingName.CURRENT_TAXPAYER_NAME.name());
		permissionNames.add(SettingName.CURRENT_TAXPAYER_ADDRESS.name());
		permissionNames.add(SettingName.PURCHASE_NUMBER.name());
		
		return permissionNames;
	}

	@Override
	public EbmSetting getByName(String settingName) {

		return ebmEbmSettingRepository.getBySettingName(settingName);
	}

	@Override
	public void updateEntity(EbmSetting setting) {
		EbmSetting exits = getById(setting.getId());
		exits.setSettingValue(setting.getSettingValue());
		ebmEbmSettingRepository.save(exits);

	}

	@Override
	public EbmSetting getById(String id) {
		Optional<EbmSetting> value = ebmEbmSettingRepository.findById(id);

		return value.orElseThrow(null);
	}
}
