package rw.mangatek.ebm2.core.ui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmSetting;
import rw.mangatek.ebm2.core.service.IEbmSettingService;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;

@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.SETTINGS_URL)
public class SettingsController {
	 
	private static final String CODE_TYPES_LIST_URL = "redirect:" + CoreUrlValue.SETTINGS_URL + "/list";   
	
	private final IEbmSettingService ebmSettingService;
	private final IVsdcService vsdcService;

	public SettingsController(IVsdcService vsdcService,IEbmSettingService ebmSettingService) {
		
		this.vsdcService = vsdcService;
		this.ebmSettingService = ebmSettingService;

	}

	@GetMapping(path = "/list")
	public String listAllSettings(Model model) {
		 
		List<EbmSetting> list = ebmSettingService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0)) ;
		log.debug(String.format("--------------Calling listAllSettings-------------------:%s", list.size()));
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.SETTING_LIST;
	}

	@GetMapping(path = "/new")
	public String newCodeTypes(Model model) {
		log.debug("--------------Calling newCodeTypes--------------");
		vsdcService.codeExcute();
		return getCodeTypeList();
	}
	public String getCodeTypeList() {

		return CODE_TYPES_LIST_URL;
	}
}
