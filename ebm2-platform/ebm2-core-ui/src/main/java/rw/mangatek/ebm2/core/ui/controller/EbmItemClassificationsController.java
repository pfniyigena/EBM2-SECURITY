package rw.mangatek.ebm2.core.ui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmItemClassification;
import rw.mangatek.ebm2.core.service.IEbmItemClassificationService;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;

@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.ITEM_CLASSIFICATIONS_URL)
public class EbmItemClassificationsController {

	private static final String ITEM_CLASSIFICATIONS_LIST_URL = "redirect:" + CoreUrlValue.ITEM_CLASSIFICATIONS_URL + "/list";

	private final IVsdcService vsdcService;
	private final IEbmItemClassificationService ebmItemClassificationService;

	public EbmItemClassificationsController(IVsdcService vsdcService, IEbmItemClassificationService ebmItemClassificationService) {

		this.vsdcService = vsdcService;
		this.ebmItemClassificationService = ebmItemClassificationService;

	}

	@GetMapping(path = "/list")
	public String listItemClassifications(Model model) {

		List<EbmItemClassification> list = ebmItemClassificationService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0));
		log.debug("--------------Calling listItemClassifications-------------------" + list.size());
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.ITEM_CLASSIFICATION_LIST;
	}

	@GetMapping(path = "/new")
	public String newItemClassification(Model model) {
		log.debug("--------------Calling newItemClassification--------------");
		vsdcService.selectItemClsList();
		return getBranchList();
	}

	public String getBranchList() {

		return ITEM_CLASSIFICATIONS_LIST_URL;
	}
}
