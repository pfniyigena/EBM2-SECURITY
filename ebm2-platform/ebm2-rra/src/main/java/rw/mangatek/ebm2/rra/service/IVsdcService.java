package rw.mangatek.ebm2.rra.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import ebm.vsdc.excute.model.TrnsSalesSaveWrReq;
import ebm.vsdc.excute.model.TrnsSalesSaveWrRes;
import rw.mangatek.ebm2.core.domain.EbmCustomer;
import rw.mangatek.ebm2.core.domain.EbmImportation;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.domain.EbmPurchase;
import rw.mangatek.ebm2.core.domain.EbmStockMove;

public interface IVsdcService {
	// InitializeVsdcExcute
	public void initializeVsdc();

//selectCodeList
	public void codeExcute();

	public void selectItemClsList();

	public void selectCustomerList();

	public void skmmManager();

	public TrnsSalesSaveWrRes generateReceipt(TrnsSalesSaveWrReq req);

	public void selectBhfList();

	public void selectNoticeList();

	public void selectImportItemList();

	public void updateImportItem(List<EbmImportation> importations);

	public void selectItemList();

	public Map<String, Object> saveBhfCustomer(String customerAddress, String branchId, String customerName,
			String customerNumber, String customerTin, String customerEmail, String faxNo, String useYn,
			String registeredTin, String customerPone);

	public void saveBhfCustomer(List<EbmCustomer> ebmCustomers);

	public Map<String, Object> saveBhfUser(String username, String userId, String registeredTin, String password,
			String userPhone, String branchId, String authorityCode, String userAddress, String useYn);

	public Map<String, Object> saveBhfInsurance(String registeredTin, String branchId, String insuranceCode,
			String insuranceName, BigDecimal rate, String useYn);

	public void sendItemToServer(List<EbmItem> items);

	// STOCK CRUD

	public void insertStockIO(List<EbmStockMove> moves);

	public void selectStockMoveList();

	public void saveStockMaster(List<EbmItem> items);

	// PURCHASES
	public void selectTrnsPurchaseSalesList();

	public void updatePurchase(List<EbmPurchase> purchases);

}
