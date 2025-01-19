package rw.mangatek.ebm2.core.ui.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.domain.EbmStockMove;
import rw.mangatek.ebm2.core.domain.EbmStockMoveItem;
import rw.mangatek.ebm2.core.service.IEbmCustomerService;
import rw.mangatek.ebm2.core.service.IEbmItemService;
import rw.mangatek.ebm2.core.service.IEbmStockMoveService;
import rw.mangatek.ebm2.core.service.IEbmTaxpayerBranchService;
import rw.mangatek.ebm2.core.ui.dto.InvoiceDto;
import rw.mangatek.ebm2.core.ui.dto.SaleCartDto;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;

@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.STOCK_MOVES_URL)
public class StockMovesController {

	private static final String STOCK_MOVES_LIST_URL = "redirect:" + CoreUrlValue.STOCK_MOVES_URL + "/list";

	private final IVsdcService vsdcService;
	private final IEbmItemService ebmItemService;
	private final IEbmCustomerService ebmCustomerService;
	private final IEbmStockMoveService ebmStockMoveService;
	private final IEbmTaxpayerBranchService ebmTaxpayerBranchService;

	public StockMovesController(IEbmItemService ebmItemService, IEbmCustomerService ebmCustomerService,
			IVsdcService vsdcService, IEbmStockMoveService ebmStockMoveService,
			IEbmTaxpayerBranchService ebmTaxpayerBranchService) {
		this.ebmItemService = ebmItemService;
		this.vsdcService = vsdcService;
		this.ebmCustomerService = ebmCustomerService;
		this.ebmStockMoveService = ebmStockMoveService;
		this.ebmTaxpayerBranchService = ebmTaxpayerBranchService;

	}

	@GetMapping(path = "/list")
	public String listAllStockMoves(Model model) {

		List<EbmStockMove> list = ebmStockMoveService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0));
		log.debug("--------------Calling listAllStockMoves-------------------" + list.size());
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.STOCK_MOVE_LIST;
	}

	@PostMapping(path = "/new")
	public String saveInvoice(InvoiceDto invoiceDto, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) String[] itemId,
			@RequestParam(name = "amount[]", required = false) BigDecimal[] amount, RedirectAttributes flash,
			SessionStatus status, Locale locale) {
		List<EbmStockMoveItem> moveItems = new ArrayList<>();
		
		int j = 1;
		for (int i = 0; i < itemId.length; i++) {
			EbmItem item = ebmItemService.getById(itemId[i]);

			moveItems.add(EbmStockMoveItem.builder().barcode(item.getBarcode()).itemName(item.getItemName())
					.itemCode(item.getItemCode()).itemClassificationCode(item.getItemClassificationCode())
					.packaging((amount[i])).quantity(amount[i]).saleUnitPrice(item.getDefaultUnitPrice())
					.purcaseUnitPrice(item.getPurcaseUnitPrice()).sequanceNumber(j)
					.quantityUnitCode(item.getQuantityUnitCode()).packageUnitCode(item.getPackagingUnitCode())
					.taxLabel(item.getTaxationTypeCode()).build());
			j++;
			log.debug(String.format("ID:%s,Quantity:%s ", itemId[i], amount[i]));
		}
		ebmStockMoveService.saveEntity(moveItems,invoiceDto.getBranchId());
		return getStockMoveList();
	}

	@GetMapping(path = "/new")
	public String newInvoice(InvoiceDto invoiceDto, Model model) {
		log.debug("--------------Calling Move--------------");
		setModel(model);
		
		invoiceDto.getSales().add(new SaleCartDto());
		return CoreUIStaticThymeleafView.STOCK_MOVE_ADD_FORM;
	}

	@GetMapping(value = "/view/{id}")
	public String viewDetails(@PathVariable("id") String id, Model model) {

		EbmStockMove move = ebmStockMoveService.getById(id);
		model.addAttribute("invoice", move);
		log.debug(String.format("Calling viewDetails {%s}", move.getMoveItems().size()));
		return CoreUIStaticThymeleafView.STOCK_MOVE_DETAILS;
	}

	@GetMapping(path = "/download")
	public String downloadStockMove(Model model) {
		log.debug("--------------Calling downloadStockMove--------------");
		vsdcService.selectStockMoveList();
		return getStockMoveList();
	}

	public String getStockMoveList() {

		return STOCK_MOVES_LIST_URL;
	}

	private void setModel(Model model) {
		model.addAttribute("items", ebmItemService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0)));
		model.addAttribute("customers", ebmCustomerService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0)));

		model.addAttribute("branches", ebmTaxpayerBranchService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0)));
	}
}
