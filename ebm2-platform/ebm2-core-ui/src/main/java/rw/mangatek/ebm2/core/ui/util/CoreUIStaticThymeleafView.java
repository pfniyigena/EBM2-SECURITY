package rw.mangatek.ebm2.core.ui.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;

public class CoreUIStaticThymeleafView {
	private CoreUIStaticThymeleafView() {
		throw new IllegalStateException("Utility class");
	}

	// CODE TYPES
	public static final String CODE_TYPE_LIST = "code-types/list";
	// SYSTEM CODES
	public static final String SYSTEM_CODE_LIST = "system-codes/list";
	// BRANCHES
	public static final String BRANCH_LIST = "branches/list";
	public static final String BRANCH_ADD_FORM = "branches/add-form";
	public static final String ITEM_CLASSIFICATION_LIST = "item-classifications/list";
	// CUSTOMER
	public static final String CUSTOMER_LIST = "customers/list";
	public static final String CUSTOMER_ADD_FORM = "customers/add-form";
	public static final String CUSTOMER_UPDATE_FORM = "customers/update-form";
	// USER
	public static final String USER_LIST = "users/list";
	public static final String USER_ADD_FORM = "users/add-form";
	public static final String USER_UPDATE_FORM = "users/update-form";
	//INSURANCE
	public static final String INSURANCE_LIST = "insurances/list";
	public static final String INSURANCE_ADD_FORM = "insurances/add-form";
	public static final String INSURANCE_UPDATE_FORM = "insurances/update-form";
	//ITEMS
	public static final String ITEM_LIST = "items/list";
	
	//INVOICES
	public static final String INVOICE_LIST = "invoices/list";
	public static final String INVOICE_ADD_FORM = "invoices/add-form";
	public static final String INVOICE_DETAILS = "invoices/details";
	public static final String INVOICE_JOURNAL = "invoices/journal";
	
	//STOCK MOVES
	public static final String STOCK_MOVE_LIST = "stock-moves/list";
	public static final String STOCK_MOVE_ADD_FORM = "stock-moves/add-form";
	public static final String STOCK_MOVE_DETAILS = "stock-moves/details";
	
	// CODE TYPES
	public static final String SETTING_LIST = "settings/list";
	
	// IMPORTATION
	public static final String IMPORTATION_LIST = "importations/list";
	public static final String IMPORTATION_UPDATE_FORM = "importations/update-form";
	
	//PURCHASES
		public static final String PURCHASE_LIST = "purchases/list";
		public static final String PURCHASE_ADD_FORM = "purchases/add-form";
	public static PageRequest getPageRequest(int evalPage) {
		return PageRequest.of(evalPage, CoreUIStatiValue.PAGE_SIZE, Direction.DESC, "createdAt");

	}

}
