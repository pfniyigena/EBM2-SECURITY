package rw.mangatek.ebm2.core.ui.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmCustomer;
import rw.mangatek.ebm2.core.service.IEbmCustomerService;
import rw.mangatek.ebm2.core.ui.dto.CustomerDto;
import rw.mangatek.ebm2.core.ui.util.CoreUIStatiValue;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;

@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.CUSTOMERS_URL)
public class CustomersController {

	private static final String CUSTOMERS_LIST_URL = "redirect:" + CoreUrlValue.CUSTOMERS_URL + "/list";
	private final IEbmCustomerService ebmCustomerService;
	private final IVsdcService vsdcService;

	public CustomersController(IEbmCustomerService ebmCustomerService, IVsdcService vsdcService) {
		this.ebmCustomerService = ebmCustomerService;
		this.vsdcService = vsdcService;

	}

	@GetMapping(path = "/list")
	public String listAllCustomers(Model model) {

		List<EbmCustomer> list = ebmCustomerService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0));
		log.debug(String.format("--------------Calling listAllCustomers-------------------:{%s}", list.size()));
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.CUSTOMER_LIST;
	}

	@GetMapping(path = "/new")
	public String newCustomer(Model model) {
		log.debug("--------------Calling newCustomers--------------");

		model.addAttribute("customerDto", new CustomerDto());
		return CoreUIStaticThymeleafView.CUSTOMER_ADD_FORM;
	}

	@PostMapping(path = "/new")
	public String saveNewCustomer(@Valid CustomerDto customerDto, BindingResult bindingResult) {
		log.debug(String.format("------calling saveAcademicCycle:{%s}", customerDto));
		if (bindingResult.hasErrors()) {
			log.debug("Errors:" + bindingResult.getAllErrors());
			return CoreUIStaticThymeleafView.CUSTOMER_ADD_FORM;
		}
		customerDto.setRegisteredTin(CoreUIStatiValue.REGISTERED_TIN);
		customerDto.setBranchId(CoreUIStatiValue.REGISTERED_BRANCH);

		EbmCustomer ebmCustomer = CustomerDto.fromCustomerDto(customerDto);
		EbmCustomer savedEbmCustomer = ebmCustomerService.saveEntity(ebmCustomer);
		if (savedEbmCustomer != null) {
			Map<String, Object> result = vsdcService.saveBhfCustomer(savedEbmCustomer.getCustomerAddress(),
					savedEbmCustomer.getBranchId(), savedEbmCustomer.getCustomerName(),
					savedEbmCustomer.getCustomerNumber(), savedEbmCustomer.getCustomerTin(),
					savedEbmCustomer.getCustomerEmail(), savedEbmCustomer.getFaxNo(), savedEbmCustomer.getUseYn(),
					savedEbmCustomer.getRegisteredTin(), savedEbmCustomer.getCustomerPhone());
			if (result.get(CoreUIStatiValue.OBJECT_KEY) != null) {
				ebmCustomerService.updateEntityWithSentToServer(savedEbmCustomer);
			}
		}
		log.debug(String.format("------calling saveAcademicCycle:{%s}", customerDto));

		return CUSTOMERS_LIST_URL;
	}

	@GetMapping(path = "/edit/{id}")
	public String getCustomerById(@PathVariable String id, Model model) {
		EbmCustomer academicCycle = ebmCustomerService.getById(id);
		model.addAttribute("customerDto", CustomerDto.fromCustomerModel(academicCycle));
		return CoreUIStaticThymeleafView.CUSTOMER_UPDATE_FORM;
	}

	@PostMapping(path = "/update")
	public String updateAcademicCycle(@Valid CustomerDto customerDto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			log.debug("Errors:" + bindingResult.getAllErrors());
			return CoreUIStaticThymeleafView.CUSTOMER_ADD_FORM;
		}
		EbmCustomer ebmCustomer = CustomerDto.fromCustomerDto(customerDto);
		EbmCustomer savedEbmCustomer = ebmCustomerService.updateEntity(ebmCustomer);
		if (savedEbmCustomer != null) {
			Map<String, Object> result = vsdcService.saveBhfCustomer(savedEbmCustomer.getCustomerAddress(),
					savedEbmCustomer.getBranchId(), savedEbmCustomer.getCustomerName(),
					savedEbmCustomer.getCustomerNumber(), savedEbmCustomer.getCustomerTin(),
					savedEbmCustomer.getCustomerEmail(), savedEbmCustomer.getFaxNo(), savedEbmCustomer.getUseYn(),
					savedEbmCustomer.getRegisteredTin(), savedEbmCustomer.getCustomerPhone());
			if (result.get(CoreUIStatiValue.OBJECT_KEY) != null) {
				ebmCustomerService.updateEntityWithSentToServer(savedEbmCustomer);
			}
		}
		log.debug(String.format("------calling saveAcademicCycle:{%s}", customerDto));

		return CUSTOMERS_LIST_URL;

	}

	@GetMapping(path = "/download")
	public String downloadCustomer(Model model) {
		log.debug("--------------Calling newCustomers--------------");
		vsdcService.selectCustomerList();
		return CUSTOMERS_LIST_URL;
	}

	public String getCustomerList() {

		return CUSTOMERS_LIST_URL;
	}
}
