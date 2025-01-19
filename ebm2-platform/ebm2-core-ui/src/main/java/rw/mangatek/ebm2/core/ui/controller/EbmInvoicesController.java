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

import ebm.vsdc.excute.model.TrnsSalesSaveWrReq;
import ebm.vsdc.excute.model.TrnsSalesSaveWrRes;
import ebm.vsdc.excute.model.TrnsSalesSaveWrRes.TrnsSalesSaveWrResData;
import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmCustomer;
import rw.mangatek.ebm2.core.domain.EbmInvoice;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.domain.EbmSetting;
import rw.mangatek.ebm2.core.enns.SettingName;
import rw.mangatek.ebm2.core.service.IEbmCustomerService;
import rw.mangatek.ebm2.core.service.IEbmInvoiceService;
import rw.mangatek.ebm2.core.service.IEbmItemService;
import rw.mangatek.ebm2.core.service.IEbmSettingService;
import rw.mangatek.ebm2.core.ui.dto.InvoiceDto;
import rw.mangatek.ebm2.core.ui.dto.SaleCartDto;
import rw.mangatek.ebm2.core.ui.dto.VCISInvoiceDto;
import rw.mangatek.ebm2.core.ui.dto.VCISSaleDto;
import rw.mangatek.ebm2.core.ui.util.CoreUIStatiValue;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.core.ui.util.ParserData;
import rw.mangatek.ebm2.core.util.Ebm2StaticValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;
import rw.mangatek.ebm2.rra.util.VsdcRRACodeUtil;

@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.INVOICES_URL)
public class EbmInvoicesController {

	private static final String ITEMS_LIST_URL = "redirect:" + CoreUrlValue.INVOICES_URL + "/list";
	private final IEbmItemService ebmItemService;
	private final IEbmCustomerService ebmCustomerService;
	private final IEbmInvoiceService ebmInvoiceService;
	private final IEbmSettingService ebmSettingService;

	private final IVsdcService vsdcService;

	public EbmInvoicesController(IEbmInvoiceService ebmInvoiceService, IEbmItemService ebmItemService,
			IEbmCustomerService ebmCustomerService, IEbmSettingService ebmSettingService, IVsdcService vsdcService) {
		this.ebmItemService = ebmItemService;
		this.ebmInvoiceService = ebmInvoiceService;
		this.ebmCustomerService = ebmCustomerService;
		this.ebmSettingService = ebmSettingService;
		this.vsdcService = vsdcService;

	}

	@GetMapping(path = "/list")
	public String listInvoices(Model model) {

		List<EbmInvoice> list = ebmInvoiceService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0));
		log.debug("--------------Calling listItems-------------------" + list.size());
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.INVOICE_LIST;
	}

	@GetMapping(path = "/new")
	public String newInvoice(InvoiceDto invoiceDto, Model model) {
		log.debug("--------------Calling newInvoice--------------");
		setModel(model);

		invoiceDto.getSales().add(new SaleCartDto());
		return CoreUIStaticThymeleafView.INVOICE_ADD_FORM;
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

	@GetMapping(value = "/journal/{id}")
	public String viewJournal(@PathVariable("id") String id, Model model) {

		EbmInvoice invoice = ebmInvoiceService.getById(id);

		String ej = "";
		if (invoice != null && invoice.getEbmReceipt().getJournal() != null) {
			ej = invoice.getEbmReceipt().getJournal().replaceAll("\n", "<br>");
		}

		model.addAttribute("journal", ej);
		return CoreUIStaticThymeleafView.INVOICE_JOURNAL;
	}

	@PostMapping(path = "/new")
	public String saveInvoice(InvoiceDto invoiceDto, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) String[] itemId,
			@RequestParam(name = "amount[]", required = false) BigDecimal[] amount, RedirectAttributes flash,
			SessionStatus status, Locale locale) {
		EbmCustomer customer = null;
		EbmSetting settingInvoiceNumber = ebmSettingService.getByName(SettingName.INVOICE_NUMBER.name());
		if (invoiceDto.getCustomerId() != null && !invoiceDto.getCustomerId().isEmpty()) {
			customer = ebmCustomerService.getById(invoiceDto.getCustomerId());
		}
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		Integer invoiceNumber = Integer.parseInt(settingInvoiceNumber.getSettingValue());
		VCISInvoiceDto cisInvoiceDto = parseFromSaleCart(itemId, amount, invoiceNumber, registeredTin.getSettingValue(),
				customer);
		log.debug(String.format("--------------saveInvoice--------------:{%s}", cisInvoiceDto));
		Map<String, Object> result1 = getSignature(cisInvoiceDto);
		String error = (String) result1.get(CoreUIStatiValue.ERROR_KEY);
		if (error == null) {
			invoiceNumber = invoiceNumber + 1;
			settingInvoiceNumber.setSettingValue(invoiceNumber.toString());
			ebmSettingService.updateEntity(settingInvoiceNumber);
			return ITEMS_LIST_URL;
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

	private VCISInvoiceDto parseFromSaleCart(String[] itemId, BigDecimal[] amount, Integer invoiceNumber,
			String registeredTin, EbmCustomer customer) {
		VCISInvoiceDto cisInvoiceDto = new VCISInvoiceDto();
		List<VCISSaleDto> sales = new ArrayList<>();
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
			VCISSaleDto sale = new VCISSaleDto();
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
		cisInvoiceDto.setSales(sales);
		cisInvoiceDto.setTransactionType("S");
		cisInvoiceDto.setInvoiceType("N");
		cisInvoiceDto.setInvoiceNumber(invoiceNumber);
		log.debug(String.format("parseCisStringDateToVsdcStringDateTime:%s", parseCisStringDateToVsdcStringDateTime()));
		cisInvoiceDto.setCisDate(parseCisStringDateToVsdcStringDateTime());
		cisInvoiceDto.setRegisteredTin(registeredTin);
		if (customer != null) {
			cisInvoiceDto.setClientName(customer.getCustomerName());
			cisInvoiceDto.setClientTin(customer.getCustomerTin());
		}
		return cisInvoiceDto;

	}

	private Map<String, Object> getSignature(VCISInvoiceDto cisInvoice) {
		Map<String, Object> result = new HashMap<>();
		TrnsSalesSaveWrResData responseSignature = null;

		TrnsSalesSaveWrRes vsdcResponse = null;
		EbmSetting mrcSetting = ebmSettingService.getByName(SettingName.CURRENT_MRC.name());
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		EbmSetting registeredAddress = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_ADDRESS.name());
		EbmSetting registeredName = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_NAME.name());

		log.debug(String.format("------------GENERATING INVOICE FROM UI-----TIN:{%s},MRC:{%s},NAME:{%s},BRANCH:{%s}",
				registeredTin.getSettingValue(), mrcSetting.getSettingValue(), registeredName.getSettingValue(),
				registeredBranch.getSettingValue()));

		Map<String, Object> parseDataModel = ParserData.generateReceiptDataFromVCISInvoiceDto(cisInvoice,
				registeredTin.getSettingValue(), registeredBranch.getSettingValue(), registeredName.getSettingValue(),
				registeredAddress.getSettingValue(), mrcSetting.getSettingValue());
		EbmInvoice ebmInvoice = (EbmInvoice) parseDataModel.get(CoreUIStatiValue.EBM_INVOICE);
		log.debug("------------ebmInvoice.getCisDate----------------" + ebmInvoice.getCisDate());
		EbmInvoice savedEbmInvoice = ebmInvoiceService.saveNewInvoice(ebmInvoice);

		boolean sentToServer = false;
		if (savedEbmInvoice == null) {
			result.put(CoreUIStatiValue.ERROR_KEY, "ERROR WHILE SAVING INVOICE");
			result.put(CoreUIStatiValue.OBJECT_KEY, null);
			return result;
		}

		TrnsSalesSaveWrReq req = (TrnsSalesSaveWrReq) parseDataModel.get(CoreUIStatiValue.RRA_INVOICE);
		vsdcResponse = vsdcService.generateReceipt(req);

		if (vsdcResponse == null) {
			result.put(CoreUIStatiValue.ERROR_KEY, "ERROR WHILE GENERATING SIGNATURE");
			result.put(CoreUIStatiValue.OBJECT_KEY, null);
			return result;
		}
		if (vsdcResponse.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {
			sentToServer = true;
		}
		responseSignature = vsdcResponse.getData();
		if (responseSignature == null) {
			result.put(CoreUIStatiValue.ERROR_KEY, "ERROR WHILE GENERATING SIGNATURE" + vsdcResponse.getResultMsg());
			result.put(CoreUIStatiValue.OBJECT_KEY, null);
			return result;
		}
		if ((responseSignature.getIntrlData().isEmpty()
				&& cisInvoice.getInvoiceType().equals(Ebm2StaticValue.NORMAL_RECEIPT))
				|| (responseSignature.getIntrlData().isEmpty()
						&& cisInvoice.getInvoiceType().equals(Ebm2StaticValue.COPY_RECEIPT))) {
			result.put(CoreUIStatiValue.ERROR_KEY, "ERROR WHILE GENERATING SIGNATURE" + vsdcResponse.getResultMsg());
			result.put(CoreUIStatiValue.OBJECT_KEY, null);
			return result;

		}
		EbmInvoice updatedInvoice = ParserData.updateEbmInvoiceWithVsdcResponse(savedEbmInvoice, responseSignature);
		ebmInvoiceService.updateInvoice(updatedInvoice, sentToServer);
		result.put(CoreUIStatiValue.ERROR_KEY, null);
		result.put(CoreUIStatiValue.OBJECT_KEY, responseSignature.getIntrlData());
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
