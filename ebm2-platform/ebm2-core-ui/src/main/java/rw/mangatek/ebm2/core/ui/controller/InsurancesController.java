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
import rw.mangatek.ebm2.core.domain.EbmInsurance;
import rw.mangatek.ebm2.core.service.IEbmInsuranceService;
import rw.mangatek.ebm2.core.ui.dto.InsuranceDto;
import rw.mangatek.ebm2.core.ui.util.CoreUIStatiValue;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;

@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.INSURANCES_URL)
public class InsurancesController {

	private static final String INSURANCES_LIST_URL = "redirect:" + CoreUrlValue.INSURANCES_URL + "/list";
	private final IEbmInsuranceService ebmInsuranceService;
	private final IVsdcService vsdcService;

	public InsurancesController(IEbmInsuranceService ebmInsuranceService, IVsdcService vsdcService) {
		this.ebmInsuranceService = ebmInsuranceService;
		this.vsdcService = vsdcService;

	}

	@GetMapping(path = "/list")
	public String listAllInsurances(Model model) {

		List<EbmInsurance> list = ebmInsuranceService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0));
		log.debug(String.format("--------------Calling listAllInsurances-------------------:{%s}", list.size()));
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.INSURANCE_LIST;
	}

	@GetMapping(path = "/new")
	public String newInsurance(Model model) {
		log.debug("--------------Calling newInsurances--------------");

		model.addAttribute("insuranceDto", new InsuranceDto());
		return CoreUIStaticThymeleafView.INSURANCE_ADD_FORM;
	}

	@PostMapping(path = "/new")
	public String saveNewInsurance(@Valid InsuranceDto customerDto, BindingResult bindingResult) {
		log.debug(String.format("------calling saveAcademicCycle:{%s}", customerDto));
		if (bindingResult.hasErrors()) {
			log.debug("Errors:" + bindingResult.getAllErrors());
			return CoreUIStaticThymeleafView.INSURANCE_ADD_FORM;
		}
		customerDto.setRegisteredTin(CoreUIStatiValue.REGISTERED_TIN);
		customerDto.setBranchId(CoreUIStatiValue.REGISTERED_BRANCH);

		EbmInsurance ebmInsurance = InsuranceDto.fromInsuranceDto(customerDto);
		EbmInsurance savedEbmInsurance = ebmInsuranceService.saveEntity(ebmInsurance);
		if (savedEbmInsurance != null) {
			Map<String, Object> result = vsdcService.saveBhfInsurance(savedEbmInsurance.getRegisteredTin(),
					savedEbmInsurance.getBranchId(), savedEbmInsurance.getInsuranceCode(),
					savedEbmInsurance.getInsuranceName(), savedEbmInsurance.getPremiumRate(),
					savedEbmInsurance.getUseYn());
			if (result.get(CoreUIStatiValue.OBJECT_KEY) != null) {
				ebmInsuranceService.updateEntityWithSentToServer(savedEbmInsurance);
			}
		}
		log.debug(String.format("------calling saveAcademicCycle:{%s}", customerDto));

		return INSURANCES_LIST_URL;
	}

	@GetMapping(path = "/edit/{id}")
	public String getInsuranceById(@PathVariable String id, Model model) {
		EbmInsurance academicCycle = ebmInsuranceService.getById(id);
		model.addAttribute("insuranceDto", InsuranceDto.fromInsuranceModel(academicCycle));
		return CoreUIStaticThymeleafView.INSURANCE_UPDATE_FORM;
	}

	@PostMapping(path = "/update")
	public String updateAcademicCycle(@Valid InsuranceDto insuranceDto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			log.debug("Errors:" + bindingResult.getAllErrors());
			return CoreUIStaticThymeleafView.INSURANCE_ADD_FORM;
		}
		EbmInsurance ebmInsurance = InsuranceDto.fromInsuranceDto(insuranceDto);
		EbmInsurance savedEbmInsurance = ebmInsuranceService.updateEntity(ebmInsurance);
		if (savedEbmInsurance != null) {
			Map<String, Object> result = vsdcService.saveBhfInsurance(savedEbmInsurance.getRegisteredTin(),
					savedEbmInsurance.getBranchId(), savedEbmInsurance.getInsuranceCode(),
					savedEbmInsurance.getInsuranceName(), savedEbmInsurance.getPremiumRate(),
					savedEbmInsurance.getUseYn());
			if (result.get(CoreUIStatiValue.OBJECT_KEY) != null) {
				ebmInsuranceService.updateEntityWithSentToServer(savedEbmInsurance);
			}
		}
		log.debug(String.format("------calling saveAcademicCycle:{%s}", insuranceDto));

		return INSURANCES_LIST_URL;

	}

	@GetMapping(path = "/download")
	public String downloadInsurance(Model model) {
		log.debug("--------------Calling newInsurances--------------");
		vsdcService.codeExcute();
		return CoreUIStaticThymeleafView.INSURANCE_ADD_FORM;
	}

	public String getInsuranceList() {

		return INSURANCES_LIST_URL;
	}
}
