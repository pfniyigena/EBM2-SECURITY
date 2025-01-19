package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmSetting;

public interface IEbmSettingService {

	public void saveEntity(EbmSetting type);

	public void saveEntities(List<EbmSetting> types);

	public Iterable<EbmSetting> findAll(Pageable pageable);

	public List<EbmSetting> findAllAsList(Pageable pageable);
	
	public void initialize();
	
	public EbmSetting getByName(String settingName);

	public void updateEntity(EbmSetting setting);
	
	public EbmSetting getById(String id);

}
