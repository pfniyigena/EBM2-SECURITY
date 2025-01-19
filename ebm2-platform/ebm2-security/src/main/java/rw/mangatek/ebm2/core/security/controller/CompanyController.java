package rw.mangatek.ebm2.core.security.controller;

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
import rw.mangatek.ebm2.core.domain.Company;
import rw.mangatek.ebm2.core.security.dto.CompanyDto;
import rw.mangatek.ebm2.core.security.util.SecurityStaticThymeleafView;
import rw.mangatek.ebm2.core.service.ICompanyService;

@Slf4j
@Controller
@RequestMapping(value = SecurityStaticThymeleafView.COMPANY_URL)
public class CompanyController {

	private static final String BRANCHES_LIST_URL = "redirect:/companies/list";
	private static final String BRANCH_ADD_FORM = SecurityStaticThymeleafView.COMPANY_ADD_FORM;

	private final ICompanyService companyService;

	public CompanyController(ICompanyService companyService) {

		this.companyService = companyService;

	}

	@GetMapping(path = "/list")
	public String listCompanies(Model model) {

		List<Company> list = companyService.findAllAsList(SecurityStaticThymeleafView.getPageRequest(0));
		log.debug("--------------Calling listCompanies-------------------" + list.size());
		model.addAttribute("lists", list);
		return SecurityStaticThymeleafView.COMPANY_LIST;
	}

	@GetMapping(path = "/new")
	public String newCompany(Model model) {
		log.debug("--------------Calling newBranch----------------");
		model.addAttribute("companyDto", new CompanyDto());
		return BRANCH_ADD_FORM;
	}

	@PostMapping(path = "/new")
	public String saveNewCompany(CompanyDto companyDto, RedirectAttributes redirectAttrs, BindingResult bindingResult,
			Model model) {

		log.debug(String.format("------calling saveAcademicCycle:{%s}", companyDto));
		companyService.saveEntity(CompanyDto.fromDto(companyDto));
		return BRANCHES_LIST_URL;
	}

	@GetMapping(path = "/update/{id}")
	public String getImportationById(@PathVariable String id, Model model) {
		Company company = companyService.getById(id);

		model.addAttribute("companyDto", CompanyDto.fromEntity(company));

		return SecurityStaticThymeleafView.COMPANY_UPDATE_FORM;
	}

	@PostMapping(path = "/update")
	public String updateImportation(CompanyDto companyDto, RedirectAttributes redirectAttrs,
			BindingResult bindingResult, Model model) {

		log.debug(String.format("------calling updateImportation---------STATUS:%s,ANYDESK:%s", companyDto.getStatus(),
				companyDto.getAnydesk()));
		companyService.updateEntity(CompanyDto.fromDto(companyDto));
		return BRANCHES_LIST_URL;
	}

	public String getBranchList() {

		return BRANCHES_LIST_URL;
	}
}
