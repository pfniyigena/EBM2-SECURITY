package rw.mangatek.ebm2.core.ui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.service.IEbmItemService;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;

@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.ITEMS_URL)
public class EbmItemsController {

	private static final String ITEMS_LIST_URL = "redirect:" + CoreUrlValue.ITEMS_URL + "/list";

	private final IVsdcService vsdcService;
	private final IEbmItemService ebmItemService;

	public EbmItemsController(IVsdcService vsdcService, IEbmItemService ebmItemService) {

		this.vsdcService = vsdcService;
		this.ebmItemService = ebmItemService;

	}

	@GetMapping(path = "/list")
	public String listItems(Model model) {

		List<EbmItem> list = ebmItemService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0));
		log.debug("--------------Calling listItems-------------------" + list.size());
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.ITEM_LIST;
	}

	@GetMapping(path = "/download")
	public String downloadItem(Model model) {
		log.debug("--------------Calling downloadItem--------------");
		vsdcService.selectItemList();
		return getItemList();
	}

	public String getItemList() {

		return ITEMS_LIST_URL;
	}
	
	/* ----- Autocomplete for Finding Products (autocomplete-products.js)----- */
	@GetMapping(value = "/load-products/{term}", produces = { "application/json" })
	public @ResponseBody List<EbmItem> loadProducts(@PathVariable String term) {
		return ebmItemService.findByItemName(term);
	}
}
