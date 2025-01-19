package rw.mangatek.ebm2.core.ui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmImportation;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.service.IEbmImportationService;
import rw.mangatek.ebm2.core.service.IEbmItemService;
import rw.mangatek.ebm2.core.ui.dto.ImportationDto;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;

@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.IMPORTATIONS_URL)
public class ImportationsController {

	private static final String IMPORTATIONS_LIST_URL = "redirect:" + CoreUrlValue.IMPORTATIONS_URL + "/list";

	private final IVsdcService vsdcService;
	private final IEbmImportationService ebmImportationService;
	private final IEbmItemService ebmItemService;

	public ImportationsController(IVsdcService vsdcService, IEbmItemService ebmItemService,
			IEbmImportationService ebmImportationService) {
		this.vsdcService = vsdcService;
		this.ebmItemService = ebmItemService;
		this.ebmImportationService = ebmImportationService;

	}

	@GetMapping(path = "/list")
	public String listImportation(Model model) {

		List<EbmImportation> list = ebmImportationService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0));
		log.debug(String.format("--------------Calling listImportation-------------------size:%s", list.size()));
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.IMPORTATION_LIST;
	}

	@GetMapping(path = "/update/{id}")
	public String getImportationById(@PathVariable String id, Model model) {
		EbmImportation importation = ebmImportationService.getById(id);

		model.addAttribute("importationDto", ImportationDto.fromEntity(importation));
		setModel(model);
		return CoreUIStaticThymeleafView.IMPORTATION_UPDATE_FORM;
	}

	@PostMapping(path = "/update")
	public String updateImportation(ImportationDto importationDto, RedirectAttributes redirectAttrs,
			BindingResult bindingResult, Model model) {

		log.debug(String.format("------calling updateImportation---------:%s", importationDto.getImportStatus()));
		ebmImportationService.updateStatus(importationDto.getId(), importationDto.getImportStatus(),importationDto.getItemId());
		return IMPORTATIONS_LIST_URL;
	}

	@GetMapping(path = "/download")
	public String downloadImportations(Model model,RedirectAttributes redirectAttrs) {
		log.debug("--------------Calling downloadImportations--------------");
		redirectAttrs.addFlashAttribute("success", "DOWNLOAD SUCCESSFUL");
		vsdcService.selectImportItemList();
		return IMPORTATIONS_LIST_URL;
	}

	public String getBranchList() {

		return IMPORTATIONS_LIST_URL;
	}

	private void setModel(Model model) {
		List<EbmItem> items = ebmItemService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0));
		model.addAttribute("items", items);
	}
}
