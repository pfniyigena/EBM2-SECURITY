package rw.mangatek.ebm2.core.security.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

public class SecurityStaticThymeleafView {
	private SecurityStaticThymeleafView() {
		throw new IllegalStateException("Utility class");
	}

	// COMPANY
	public static final String COMPANY_URL = "companies";
	public static final String COMPANY_LIST = "companies/list";
	public static final String COMPANY_ADD_FORM = "companies/add-form";
	public static final String COMPANY_UPDATE_FORM = "companies/update-form";
	 
	public static PageRequest getPageRequest(int evalPage) {
		return PageRequest.of(evalPage, CoreUIStatiValue.PAGE_SIZE, Direction.DESC, "createdDate");

	}

}
