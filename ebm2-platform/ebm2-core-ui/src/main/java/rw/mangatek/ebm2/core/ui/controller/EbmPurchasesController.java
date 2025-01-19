package rw.mangatek.ebm2.core.ui.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import rw.mangatek.ebm2.core.domain.EbmCustomer;
import rw.mangatek.ebm2.core.domain.EbmInvoice;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.domain.EbmPurchase;
import rw.mangatek.ebm2.core.domain.EbmSetting;
import rw.mangatek.ebm2.core.enns.SettingName;
import rw.mangatek.ebm2.core.service.IEbmCustomerService;
import rw.mangatek.ebm2.core.service.IEbmInvoiceService;
import rw.mangatek.ebm2.core.service.IEbmItemService;
import rw.mangatek.ebm2.core.service.IEbmPurchaseService;
import rw.mangatek.ebm2.core.service.IEbmSettingService;
import rw.mangatek.ebm2.core.ui.dto.InvoiceDto;
import rw.mangatek.ebm2.core.ui.dto.SaleCartDto;
import rw.mangatek.ebm2.core.ui.dto.VCISPurchaseDto;
import rw.mangatek.ebm2.core.ui.dto.VCISPurchaseItemDto;
import rw.mangatek.ebm2.core.ui.util.CoreUIStatiValue;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.core.ui.util.ParserData;
import rw.mangatek.ebm2.core.util.Ebm2StaticValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;

@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.PURCASES_URL)
public class EbmPurchasesController {

	private static final String PURCHASE_LIST_URL = "redirect:" + CoreUrlValue.PURCASES_URL + "/list";
	private final IEbmItemService ebmItemService;
	private final IEbmCustomerService ebmCustomerService;
	private final IEbmInvoiceService ebmInvoiceService;
	private final IEbmSettingService ebmSettingService;
	private final IEbmPurchaseService ebmPurchaseService;
	private final IVsdcService vsdcService;

	public EbmPurchasesController(IEbmInvoiceService ebmInvoiceService, IEbmItemService ebmItemService,
			IEbmCustomerService ebmCustomerService, IEbmSettingService ebmSettingService, IVsdcService vsdcService,
			IEbmPurchaseService ebmPurchaseService) {
		this.ebmItemService = ebmItemService;
		this.ebmInvoiceService = ebmInvoiceService;
		this.ebmCustomerService = ebmCustomerService;
		this.ebmSettingService = ebmSettingService;
		this.vsdcService = vsdcService;
		this.ebmPurchaseService = ebmPurchaseService;

	}

	@GetMapping(path = "/list")
	public String listPurchases(Model model) {

		List<EbmPurchase> list = ebmPurchaseService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0));
		log.debug("--------------Calling listPurchases-------------------" + list.size());
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.PURCHASE_LIST;
	}

	@GetMapping(path = "/download")
	public String downloadPurchases(Model model, RedirectAttributes flash) {
		log.debug("--------------Calling downloadPurchases--------------");
		vsdcService.selectTrnsPurchaseSalesList();
		flash.addFlashAttribute(CoreUIStatiValue.SUCCESS, CoreUIStatiValue.SUCCESS_DOWNLOAD);
		return PURCHASE_LIST_URL;
	}

	@GetMapping(path = "/new")
	public String newPurchse(InvoiceDto invoiceDto, Model model) {
		log.debug("--------------Calling newInvoice--------------");
		setModel(model);

		invoiceDto.getSales().add(new SaleCartDto());
		return CoreUIStaticThymeleafView.PURCHASE_ADD_FORM;
	}

	@PostMapping(path = "/add", params = { "addSale" })
	public String addSale(InvoiceDto invoiceDto, BindingResult result) {
		log.debug(String.format("--------------addSale--------------:{%s}", "addSale"));
		invoiceDto.getSales().add(new SaleCartDto());

		return CoreUIStaticThymeleafView.INVOICE_ADD_FORM;
	}

	@GetMapping(value = "/view/{id}")
	public String viewDetails(@PathVariable("id") String id, Model model) {

		EbmInvoice invoice = ebmInvoiceService.getById(id);
		model.addAttribute("invoice", invoice);
		log.debug("Calling viewDetails Attendance" + invoice.getSaleItems().size());
		return CoreUIStaticThymeleafView.INVOICE_DETAILS;
	}

	@PostMapping(path = "/new")
	public String savePurchase(InvoiceDto invoiceDto, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) String[] itemId,
			@RequestParam(name = "amount[]", required = false) BigDecimal[] amount, RedirectAttributes flash,
			SessionStatus status, Locale locale) {
		EbmCustomer customer = null;
		EbmSetting settingPurchaseNumber = ebmSettingService.getByName(SettingName.PURCHASE_NUMBER.name());
		if (invoiceDto.getCustomerId() != null && !invoiceDto.getCustomerId().isEmpty()) {
			customer = ebmCustomerService.getById(invoiceDto.getCustomerId());
		}
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		Integer invoiceNumber = Integer.parseInt(settingPurchaseNumber.getSettingValue());
		VCISPurchaseDto cisInvoiceDto = parseFromSaleCart(itemId, amount, invoiceNumber,
				registeredTin.getSettingValue(), customer, invoiceDto.getSupplierBranchId());
		log.debug(String.format("--------------savePurchase--------------:{%s}", cisInvoiceDto));
		Map<String, Object> result1 = getSignature(cisInvoiceDto);
		String error = (String) result1.get(CoreUIStatiValue.ERROR_KEY);
		if (error == null) {
			invoiceNumber = invoiceNumber + 1;
			settingPurchaseNumber.setSettingValue(invoiceNumber.toString());
			ebmSettingService.updateEntity(settingPurchaseNumber);
			flash.addFlashAttribute(CoreUIStatiValue.SUCCESS, CoreUIStatiValue.SUCCESS_SAVE);
			return PURCHASE_LIST_URL;
		} else {
			model.addAttribute("error", error);
			setModel(model);

			return CoreUIStaticThymeleafView.INVOICE_ADD_FORM;
		}

	}

	private void setModel(Model model) {
		model.addAttribute("items", ebmItemService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0)));
		model.addAttribute("customers", ebmCustomerService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0)));
	}

	private VCISPurchaseDto parseFromSaleCart(String[] itemId, BigDecimal[] amount, Integer invoiceNumber,
			String registeredTin, EbmCustomer customer, String supplierBranchId) {
		VCISPurchaseDto cisInvoiceDto = new VCISPurchaseDto();
		List<VCISPurchaseItemDto> sales = new ArrayList<>();
		BigDecimal taxableAmountA = new BigDecimal("0.00");
		BigDecimal taxableAmountB = new BigDecimal("0.00");
		BigDecimal taxableAmountC = new BigDecimal("0.00");
		BigDecimal taxableAmountD = new BigDecimal("0.00");
		BigDecimal taxAmountA = new BigDecimal("0.00");
		BigDecimal taxAmountB = new BigDecimal("0.00");
		BigDecimal taxAmountC = new BigDecimal("0.00");
		BigDecimal taxAmountD = new BigDecimal("0.00");
		BigDecimal totalTaxAmount = new BigDecimal("0.00");
		BigDecimal totalAmount = new BigDecimal("0.00");
		BigDecimal taxRateA = new BigDecimal("0.00");
		BigDecimal taxRateB = new BigDecimal("18.00");
		BigDecimal taxRateC = new BigDecimal("0.00");
		BigDecimal taxRateD = new BigDecimal("0.00");

		for (int i = 0; i < itemId.length; i++) {
			VCISPurchaseItemDto sale = new VCISPurchaseItemDto();
			log.debug(String.format("ID:%s,Quantity:%s ", itemId[i], amount[i]));
			EbmItem item = ebmItemService.getById(itemId[i]);
			BigDecimal quantity = amount[i];
			BigDecimal preTaxAmount = null;
			BigDecimal taxAmount = null;
			BigDecimal grossAmount = quantity.multiply(item.getDefaultUnitPrice());
			String taxLabel = item.getTaxationTypeCode();
			if (taxLabel == null || taxLabel.isEmpty()) {
				taxLabel = "A";
			}
			if (taxLabel.equals("A")) {
				if (taxRateA.intValue() > 0) {
					preTaxAmount = taxRateA.divide(new BigDecimal("100.00"));
					taxAmount = grossAmount.subtract(
							grossAmount.divide((new BigDecimal("1.00").add(preTaxAmount)), 2, RoundingMode.HALF_UP));
					taxAmountA = taxAmountA.add(taxAmount);
					totalTaxAmount = totalTaxAmount.add(taxAmount);
				}
				taxableAmountA = taxableAmountA.add(grossAmount);
				totalAmount = totalAmount.add(grossAmount);
			}
			if (taxLabel.equals("B")) {
				if (taxRateB.intValue() > 0) {
					preTaxAmount = taxRateB.divide(new BigDecimal("100.00"));

					taxAmount = grossAmount.subtract(
							grossAmount.divide((new BigDecimal("1.00").add(preTaxAmount)), 2, RoundingMode.HALF_UP));
					taxAmountB = taxAmountB.add(taxAmount);
					totalTaxAmount = totalTaxAmount.add(taxAmount);
				}
				taxableAmountB = taxableAmountB.add(grossAmount);
				totalAmount = totalAmount.add(grossAmount);
			}
			if (taxLabel.equals("C")) {
				if (taxRateC.intValue() > 0) {
					preTaxAmount = taxRateC.divide(new BigDecimal("100.00"));
					taxAmount = grossAmount.subtract(
							grossAmount.divide((new BigDecimal("1.00").add(preTaxAmount)), 2, RoundingMode.HALF_UP));
					taxAmountC = taxAmountC.add(taxAmount);
					totalTaxAmount = totalTaxAmount.add(taxAmount);
				}
				taxableAmountC = taxableAmountC.add(grossAmount);
				totalAmount = totalAmount.add(grossAmount);
			}
			if (taxLabel.equals("D")) {
				if (taxRateD.intValue() > 0) {
					preTaxAmount = taxRateD.divide(new BigDecimal("100.00"));
					taxAmount = grossAmount.subtract(
							grossAmount.divide((new BigDecimal("1.00").add(preTaxAmount)), 2, RoundingMode.HALF_UP));
					taxAmountD = taxAmountD.add(taxAmount);
					totalTaxAmount = totalTaxAmount.add(taxAmount);
				}
				taxableAmountD = taxableAmountD.add(grossAmount);
				totalAmount = totalAmount.add(grossAmount);
			}
			sale.setBarcode(item.getBarcode());
			sale.setDiscountAmount(new BigDecimal("0.00"));
			sale.setDiscoutRate(new BigDecimal("0.00"));
			sale.setInsuranceAmount(new BigDecimal("0.00"));
			sale.setInsuranceCode("0.00");
			sale.setInsuranceName("0.00");
			sale.setItemSeq(i++);
			sale.setPackageUnit(item.getPackagingUnitCode());
			sale.setPackaging(null);
			sale.setPremiumRate(new BigDecimal("0.00"));
			sale.setProductCode(item.getItemCode());
			sale.setProductName(item.getItemName());
			sale.setQuantity(quantity);
			sale.setQuantityUnitCode(item.getQuantityUnitCode());
			sale.setTaxableAmount(grossAmount);
			sale.setTaxAmount(new BigDecimal("0.00"));
			sale.setTaxLabel(taxLabel);
			sale.setTotalAmount(grossAmount);
			sale.setUnitPrice(item.getDefaultUnitPrice());
			sale.setUnspcCode(item.getItemClassificationCode());
			sales.add(sale);
		}
		cisInvoiceDto.setTaxableAmountA(taxableAmountA.setScale(2, RoundingMode.HALF_UP));
		cisInvoiceDto.setTaxableAmountB(taxableAmountB.setScale(2, RoundingMode.HALF_UP));
		cisInvoiceDto.setTaxableAmountC(taxableAmountC.setScale(2, RoundingMode.HALF_UP));
		cisInvoiceDto.setTaxableAmountD(taxableAmountD.setScale(2, RoundingMode.HALF_UP));
		cisInvoiceDto.setTaxAmountA(taxAmountA.setScale(2, RoundingMode.HALF_UP));
		cisInvoiceDto.setTaxAmountB(taxAmountB.setScale(2, RoundingMode.HALF_UP));
		cisInvoiceDto.setTaxAmountC(taxAmountC.setScale(2, RoundingMode.HALF_UP));
		cisInvoiceDto.setTaxAmountD(taxAmountD.setScale(2, RoundingMode.HALF_UP));
		cisInvoiceDto.setTaxRateA(taxRateA);
		cisInvoiceDto.setTaxRateB(taxRateB);
		cisInvoiceDto.setTaxRateC(taxRateC);
		cisInvoiceDto.setTaxRateD(taxRateD);
		cisInvoiceDto.setTotalAmount(totalAmount);
		cisInvoiceDto.setTotalTaxAmount(totalTaxAmount);
		cisInvoiceDto.setPurchases(sales);
		cisInvoiceDto.setTransactionType(Ebm2StaticValue.PURCHASE_TYPE);
		cisInvoiceDto.setInvoiceType(Ebm2StaticValue.NORMAL_RECEIPT);
		cisInvoiceDto.setInvoiceNumber(invoiceNumber);
		log.debug(String.format("-------parseCisStringDateToVsdcStringDateTime:%s", parseCisStringDateToVsdcStringDateTime()));
		cisInvoiceDto.setCisDate(parseCisStringDateToVsdcStringDateTime());
		cisInvoiceDto.setRegisteredTin(registeredTin);
		if (customer != null) {
			cisInvoiceDto.setSupplierName(customer.getCustomerName());
			cisInvoiceDto.setSupplierTin(customer.getCustomerTin());
		}
		cisInvoiceDto.setSupplierBranchId(supplierBranchId);

		return cisInvoiceDto;

	}

	private Map<String, Object> getSignature(VCISPurchaseDto cisInvoice) {
		Map<String, Object> result = new HashMap<>();

		EbmSetting mrcSetting = ebmSettingService.getByName(SettingName.CURRENT_MRC.name());
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		EbmSetting registeredAddress = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_ADDRESS.name());
		EbmSetting registeredName = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_NAME.name());

		log.debug(String.format("------------GENERATING PURCHASE FROM UI-----TIN:{%s},MRC:{%s},NAME:{%s},BRANCH:{%s}",
				registeredTin.getSettingValue(), mrcSetting.getSettingValue(), registeredName.getSettingValue(),
				registeredBranch.getSettingValue()));

		EbmPurchase ebmPurchase = ParserData.generatePurchaseDataFromVCISPurchaseDto(cisInvoice,
				registeredTin.getSettingValue(), registeredBranch.getSettingValue(), registeredName.getSettingValue(),
				registeredAddress.getSettingValue(), mrcSetting.getSettingValue());

		log.debug("------------ebmInvoice.getCisDate----------------:" + ebmPurchase.getCisDate());
		EbmPurchase savedEbmInvoice = ebmPurchaseService.saveNewEbmPurchase(ebmPurchase);

		if (savedEbmInvoice == null) {
			result.put(CoreUIStatiValue.ERROR_KEY, "ERROR WHILE SAVING INVOICE");
			result.put(CoreUIStatiValue.OBJECT_KEY, null);
			return result;
		}

		result.put(CoreUIStatiValue.ERROR_KEY, null);
		result.put(CoreUIStatiValue.OBJECT_KEY, savedEbmInvoice);
		return result;

	}

	private String parseCisStringDateToVsdcStringDateTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.US);
		String dateFormat = "";
		LocalDateTime localDate = LocalDateTime.now();

		dateFormat = dtf.format(localDate);
		return dateFormat;
	}
}
