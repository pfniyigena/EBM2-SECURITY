package rw.mangatek.ebm2.core.ui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmSystemCode;
import rw.mangatek.ebm2.core.service.IEbmSystemCodeService;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;

/**
 * @author Pierre Fourier NIYIGENA
 *
 */
@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.SYSTEM_CODES_URL)
public class SystemCodesController {
	 
	private static final String SYSTEM_CODE_LIST_URL = "redirect:" + CoreUrlValue.SYSTEM_CODES_URL + "/list";   
	private final IEbmSystemCodeService ebmSystemCodeService;
	private final IVsdcService vsdcService;

	public SystemCodesController(IEbmSystemCodeService ebmSystemCodeService,IVsdcService vsdcService) {
		this.ebmSystemCodeService = ebmSystemCodeService;
		this.vsdcService = vsdcService;

	}

	@GetMapping(path = "/list")
	public String listAllSystemCodes(Model model) {
		 
		List<EbmSystemCode> list = ebmSystemCodeService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0)) ;
		log.debug("--------------Calling listAllSystemCodes-------------------"+list.size());
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.SYSTEM_CODE_LIST;
	}

	@GetMapping(path = "/new")
	public String newSystemCodes(Model model) {
		log.debug("--------------Calling newSystemCodes--------------");
		vsdcService.codeExcute();
		return getSystemCodeList();
	}
	public String getSystemCodeList() {

		return SYSTEM_CODE_LIST_URL;
	}
}
