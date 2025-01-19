package rw.mangatek.ebm2.core.ui.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmTaxpayerBranch;
import rw.mangatek.ebm2.core.service.IEbmTaxpayerBranchService;
import rw.mangatek.ebm2.core.ui.dto.BranchDto;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;

@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.BRANCHES_URL)
public class BranchesController {

	private static final String BRANCHES_LIST_URL = "redirect:" + CoreUrlValue.BRANCHES_URL + "/list";
	private static final String BRANCH_ADD_FORM = CoreUIStaticThymeleafView.BRANCH_ADD_FORM;
	private final IVsdcService vsdcService;
	private final IEbmTaxpayerBranchService ebmTaxpayerBranchService;

	public BranchesController(IVsdcService vsdcService, IEbmTaxpayerBranchService ebmTaxpayerBranchService) {

		this.vsdcService = vsdcService;
		this.ebmTaxpayerBranchService = ebmTaxpayerBranchService;

	}

	@GetMapping(path = "/list")
	public String listBranches(Model model) {

		List<EbmTaxpayerBranch> list = ebmTaxpayerBranchService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0));
		log.debug("--------------Calling listBranches-------------------" + list.size());
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.BRANCH_LIST;
	}

	@GetMapping(path = "/new")
	public String newBranch(Model model) {
		log.debug("--------------Calling newBranch----------------");
		model.addAttribute("branchDto", new BranchDto());
		return BRANCH_ADD_FORM;
	}

	@PostMapping(path = "/new")
	public String saveNewBranch(BranchDto academicCycleDto, RedirectAttributes redirectAttrs,
			BindingResult bindingResult, Model model) {

		log.debug(String.format("------calling saveAcademicCycle:{%s}", academicCycleDto));

		return BRANCHES_LIST_URL;
	}

	@GetMapping(path = "/download")
	public String downloadBranches(Model model) {
		log.debug("--------------Calling newBranchess--------------");
		vsdcService.selectBhfList();
		return BRANCHES_LIST_URL;
	}

	public String getBranchList() {

		return BRANCHES_LIST_URL;
	}
}
