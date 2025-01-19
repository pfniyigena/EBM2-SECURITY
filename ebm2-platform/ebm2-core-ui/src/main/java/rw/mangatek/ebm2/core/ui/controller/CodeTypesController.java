package rw.mangatek.ebm2.core.ui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmCodeType;
import rw.mangatek.ebm2.core.service.IEbmCodeTypeService;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;

@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.CODE_TYPES_URL)
public class CodeTypesController {
	 
	private static final String CODE_TYPES_LIST_URL = "redirect:" + CoreUrlValue.CODE_TYPES_URL + "/list";   
	private final IEbmCodeTypeService ebmCodeTypeService;
	private final IVsdcService vsdcService;

	public CodeTypesController(IEbmCodeTypeService ebmCodeTypeService,IVsdcService vsdcService) {
		this.ebmCodeTypeService = ebmCodeTypeService;
		this.vsdcService = vsdcService;

	}

	@GetMapping(path = "/list")
	public String listAllCodeTypes(Model model) {
		log.debug("--------------Calling listAllCodeTypes-------------------");
		List<EbmCodeType> list = ebmCodeTypeService.findAllAsList( CoreUIStaticThymeleafView.getPageRequest(0)) ;
		log.debug("--------------Calling listAllCodeTypes-------------------"+list.size());
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.CODE_TYPE_LIST;
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
