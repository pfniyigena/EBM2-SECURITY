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
import rw.mangatek.ebm2.core.domain.EbmUser;
import rw.mangatek.ebm2.core.service.IEbmUserService;
import rw.mangatek.ebm2.core.ui.dto.UserDto;
import rw.mangatek.ebm2.core.ui.util.CoreUIStatiValue;
import rw.mangatek.ebm2.core.ui.util.CoreUIStaticThymeleafView;
import rw.mangatek.ebm2.core.ui.util.CoreUrlValue;
import rw.mangatek.ebm2.rra.service.IVsdcService;

@Slf4j
@Controller
@RequestMapping(value = CoreUrlValue.USERS_URL)
public class UsersController {

	private static final String USERS_LIST_URL = "redirect:" + CoreUrlValue.USERS_URL + "/list";
	private final IEbmUserService ebmUserService;
	private final IVsdcService vsdcService;

	public UsersController(IEbmUserService ebmUserService, IVsdcService vsdcService) {
		this.ebmUserService = ebmUserService;
		this.vsdcService = vsdcService;

	}

	@GetMapping(path = "/list")
	public String listAllUsers(Model model) {

		List<EbmUser> list = ebmUserService.findAllAsList(CoreUIStaticThymeleafView.getPageRequest(0));
		log.debug(String.format("--------------Calling listAllUsers-------------------:{%s}", list.size()));
		model.addAttribute("lists", list);
		return CoreUIStaticThymeleafView.USER_LIST;
	}

	@GetMapping(path = "/new")
	public String newUser(Model model) {
		log.debug("--------------Calling newUsers--------------");

		model.addAttribute("userDto", new UserDto());
		return CoreUIStaticThymeleafView.USER_ADD_FORM;
	}

	@PostMapping(path = "/new")
	public String saveNewUser(@Valid UserDto customerDto, BindingResult bindingResult) {
		log.debug(String.format("------calling saveAcademicCycle:{%s}", customerDto));
		if (bindingResult.hasErrors()) {
			log.debug("Errors:" + bindingResult.getAllErrors());
			return CoreUIStaticThymeleafView.USER_ADD_FORM;
		}
		customerDto.setRegisteredTin(CoreUIStatiValue.REGISTERED_TIN);
		customerDto.setBranchId(CoreUIStatiValue.REGISTERED_BRANCH);

		EbmUser ebmUser = UserDto.fromUserDto(customerDto);
		EbmUser savedEbmUser = ebmUserService.saveEntity(ebmUser);
		if (savedEbmUser != null) {
			Map<String, Object> result = vsdcService.saveBhfUser(savedEbmUser.getUsername(), savedEbmUser.getUserId(),
					savedEbmUser.getRegisteredTin(), savedEbmUser.getPassword(), savedEbmUser.getUserPhone(),
					savedEbmUser.getBranchId(), savedEbmUser.getAuthorityCode(), savedEbmUser.getUserAddress(),
					savedEbmUser.getUseYn());
			if (result.get(CoreUIStatiValue.OBJECT_KEY) != null) {
				ebmUserService.updateEntityWithSentToServer(savedEbmUser);
			}
		}
		log.debug(String.format("------calling saveAcademicCycle:{%s}", customerDto));

		return USERS_LIST_URL;
	}

	@GetMapping(path = "/edit/{id}")
	public String getUserById(@PathVariable String id, Model model) {
		EbmUser academicCycle = ebmUserService.getById(id);
		model.addAttribute("userDto", UserDto.fromUserModel(academicCycle));
		return CoreUIStaticThymeleafView.USER_UPDATE_FORM;
	}

	@PostMapping(path = "/update")
	public String updateAcademicCycle(@Valid UserDto customerDto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			log.debug("Errors:" + bindingResult.getAllErrors());
			return CoreUIStaticThymeleafView.CUSTOMER_ADD_FORM;
		}
		EbmUser ebmUser = UserDto.fromUserDto(customerDto);
		EbmUser savedEbmUser = ebmUserService.updateEntity(ebmUser);
		if (savedEbmUser != null) {
			Map<String, Object> result = vsdcService.saveBhfUser(savedEbmUser.getUsername(), savedEbmUser.getUserId(),
					savedEbmUser.getRegisteredTin(), savedEbmUser.getPassword(), savedEbmUser.getUserPhone(),
					savedEbmUser.getBranchId(), savedEbmUser.getAuthorityCode(), savedEbmUser.getUserAddress(),
					savedEbmUser.getUseYn());
			if (result.get(CoreUIStatiValue.OBJECT_KEY) != null) {
				ebmUserService.updateEntityWithSentToServer(savedEbmUser);
			}
		}
		log.debug(String.format("------calling saveAcademicCycle:{%s}", customerDto));

		return USERS_LIST_URL;

	}

	@GetMapping(path = "/download")
	public String downloadUser(Model model) {
		log.debug("--------------Calling newUsers--------------");
		vsdcService.codeExcute();
		return CoreUIStaticThymeleafView.CUSTOMER_ADD_FORM;
	}

	public String getUserList() {

		return USERS_LIST_URL;
	}
}
