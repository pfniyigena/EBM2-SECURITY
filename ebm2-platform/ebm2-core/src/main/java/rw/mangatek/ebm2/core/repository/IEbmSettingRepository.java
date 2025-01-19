package rw.mangatek.ebm2.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
 
import rw.mangatek.ebm2.core.domain.EbmSetting;

public interface IEbmSettingRepository extends JpaRepository<EbmSetting, String> {

	EbmSetting getBySettingName(String settingName);

}
