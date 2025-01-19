package rw.mangatek.ebm2.rra.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmCodeType;
import rw.mangatek.ebm2.core.domain.EbmCustomer;
import rw.mangatek.ebm2.core.domain.EbmImportation;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.domain.EbmItemClassification;
import rw.mangatek.ebm2.core.domain.EbmNotice;
import rw.mangatek.ebm2.core.domain.EbmPurchase;
import rw.mangatek.ebm2.core.domain.EbmPurchaseItem;
import rw.mangatek.ebm2.core.domain.EbmSetting;
import rw.mangatek.ebm2.core.domain.EbmStockMove;
import rw.mangatek.ebm2.core.domain.EbmStockMoveItem;
import rw.mangatek.ebm2.core.domain.EbmSystemCode;
import rw.mangatek.ebm2.core.enns.PurchaseStatus;
import rw.mangatek.ebm2.core.enns.SettingName;
import rw.mangatek.ebm2.core.service.IEbmCodeTypeService;
import rw.mangatek.ebm2.core.service.IEbmCustomerService;
import rw.mangatek.ebm2.core.service.IEbmImportationService;
import rw.mangatek.ebm2.core.service.IEbmItemClassificationService;
import rw.mangatek.ebm2.core.service.IEbmItemService;
import rw.mangatek.ebm2.core.service.IEbmPurchaseService;
import rw.mangatek.ebm2.core.service.IEbmSettingService;
import rw.mangatek.ebm2.core.service.IEbmStockMoveService;
import rw.mangatek.ebm2.core.service.IEbmSystemCodeService;
import rw.mangatek.ebm2.core.service.IEbmTaxpayerBranchService;
import rw.mangatek.ebm2.core.util.DataFormatUtil;
import rw.mangatek.ebm2.core.util.Ebm2StaticValue;
import rw.mangatek.ebm2.rra.util.VsdcRRACodeUtil;
import rw.mangatek.ebm2.rra.util.VsdcRRAUtil;

@Slf4j
@Service("vsdcService")
public class VsdcServiceImpl implements IVsdcService {

	private final IEbmCodeTypeService ebmCodeTypeService;
	private final IEbmSystemCodeService ebmSystemCodeService;
	private final IEbmTaxpayerBranchService ebmTaxpayerBranchService;
	private final IEbmItemClassificationService ebmItemClassificationService;
	private final IEbmItemService ebmItemService;
	private final IEbmStockMoveService ebmStockMoveService;
	private final IEbmSettingService ebmSettingService;
	private final IEbmCustomerService ebmCustomerService;
	private final IEbmImportationService ebmImportationService;
	private final IEbmPurchaseService ebmPurchaseService;

	public VsdcServiceImpl(IEbmCodeTypeService ebmCodeTypeService, IEbmSystemCodeService ebmSystemCodeService,
			IEbmTaxpayerBranchService ebmTaxpayerBranchService,
			IEbmItemClassificationService ebmItemClassificationService, IEbmItemService ebmItemService,
			IEbmStockMoveService ebmStockMoveService, IEbmSettingService ebmSettingService,
			IEbmCustomerService ebmCustomerService, IEbmImportationService ebmImportationService,
			IEbmPurchaseService ebmPurchaseService) {
		this.ebmCodeTypeService = ebmCodeTypeService;
		this.ebmSystemCodeService = ebmSystemCodeService;
		this.ebmTaxpayerBranchService = ebmTaxpayerBranchService;
		this.ebmItemClassificationService = ebmItemClassificationService;
		this.ebmItemService = ebmItemService;
		this.ebmStockMoveService = ebmStockMoveService;
		this.ebmSettingService = ebmSettingService;
		this.ebmCustomerService = ebmCustomerService;
		this.ebmImportationService = ebmImportationService;
		this.ebmPurchaseService = ebmPurchaseService;

	}

	@Override
	public void initializeVsdc() {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		EbmSetting registeredDevice = ebmSettingService.getByName(SettingName.CURRENT_DEVICE.name());
		log.debug("------------initializeVsdc----------------");
		InitInfoReq initInfoReq = new InitInfoReq();
		initInfoReq.setTin(registeredTin.getSettingValue());
		initInfoReq.setBhfId(registeredBranch.getSettingValue());
		initInfoReq.setDvcSrlNo(registeredDevice.getSettingValue());
		InitializeVsdcExcute initializeVsdcExcute = new InitializeVsdcExcute();
		InitInfoRes initInfoRes = initializeVsdcExcute.selectInitInfo(initInfoReq);

		log.debug(initInfoRes.getResultCd());
		log.debug(initInfoRes.getResultDt());
		log.debug(initInfoRes.getResultMsg());

		InitInfoResData initInfoResData = initInfoRes.getData();
		if (initInfoResData != null) {
			// initInfoVO
			InitInfoVO initInfoVO = initInfoResData.getInfo();
			if (initInfoVO != null) {
				initInfoVO.getTin();
			}

			log.debug("InitInfoResData:" + initInfoResData.toString());

		}

	}

	@Override
	public void codeExcute() {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		EbmSetting pullDate = ebmSettingService.getByName(SettingName.PULL_DATE.name());
		CodeReq codeReq = new CodeReq();
		codeReq.setTin(registeredTin.getSettingValue());
		codeReq.setBhfId(registeredBranch.getSettingValue());
		codeReq.setLastReqDt(pullDate.getSettingValue());

		CodeExcute codeExcute = new CodeExcute();
		CodeRes codeRes = codeExcute.selectCodeList(codeReq);
		log.debug("------------codeExcute------getResultCd ----------:" + codeRes.getResultCd());
		log.debug("------------codeExcute------getResultCd ----------:" + codeRes.getResultDt());
		log.debug("------------codeExcute------ResultMsg ----------:" + codeRes.getResultMsg());

		CodeResData data = codeRes.getData();

		if (data != null) {
			List<CodeClassLVO> list = data.getClsList();
			List<EbmCodeType> types = new ArrayList<>();
			List<EbmSystemCode> systemCodes = new ArrayList<>();
			for (CodeClassLVO l : list) {

				EbmCodeType type = EbmCodeType.builder().code(l.getCdCls()).name(l.getCdClsNm())
						.description(l.getCdClsDesc()).userDfnCd1(l.getUserDfnNm1()).userDfnCd2(l.getUserDfnNm2())
						.userDfnCd3(l.getUserDfnNm3()).useYn(l.getUseYn()).build();
				types.add(type);
				List<CodeDtlLVO> codes = l.getDtlList();

				for (CodeDtlLVO code : codes) {
					EbmSystemCode systemCode = EbmSystemCode.builder().typeCode(l.getCdCls()).name(code.getCdNm())
							.code(code.getCd()).description(code.getCdDesc()).userDfnCd1(l.getUserDfnNm1())
							.userDfnCd2(l.getUserDfnNm2()).userDfnCd3(l.getUserDfnNm3()).useYn(l.getUseYn()).build();
					systemCodes.add(systemCode);

				}

			}
			ebmCodeTypeService.saveEntities(types);
			ebmSystemCodeService.saveEntities(systemCodes);
		}
	}

	@Override
	public void selectItemClsList() {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		EbmSetting pullDate = ebmSettingService.getByName(SettingName.PULL_DATE.name());
		ItemClsReq itemClsReq = new ItemClsReq();
		itemClsReq.setBhfId(registeredBranch.getSettingValue());
		itemClsReq.setLastReqDt(pullDate.getSettingValue());
		itemClsReq.setTin(registeredTin.getSettingValue());
		ItemClsExcute itemClsExcute = new ItemClsExcute();

		ItemClsRes itemClsRes = itemClsExcute.selectItemClsList(itemClsReq);
		List<EbmItemClassification> classifications = new ArrayList<>();
		if (itemClsRes.getData() != null) {
			List<ItemClassLVO> list = itemClsRes.getData().getItemClsList();

			for (ItemClassLVO itemCl : list) {
				classifications.add(EbmItemClassification.builder().majorTarget(itemCl.getMjrTgYn())
						.name(itemCl.getItemClsNm()).code(itemCl.getItemClsCd()).taxLabel(itemCl.getTaxTyCd())
						.classificationLevel(itemCl.getItemClsLvl()).useYn(itemCl.getUseYn()).build());
			}

		}
		ebmItemClassificationService.saveList(classifications);

	}

	@Override
	public void selectCustomerList() {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		EbmSetting pullDate = ebmSettingService.getByName(SettingName.PULL_DATE.name());
		CustomerExcute customerExcute = new CustomerExcute();
		CustPagedReq custReq = new CustPagedReq();
		custReq.setTin(registeredTin.getSettingValue());
		custReq.setBhfId(registeredBranch.getSettingValue());
		custReq.setLastReqDt(pullDate.getSettingValue());

		int i = 0;
		int j = 5000;
		int z = 500000;
		while (j < z) {
			custReq.setStartPageIndex(i);
			custReq.setEndPageIndex(j);

			CustRes custRes = customerExcute.selectCustomerPagedList(custReq);
			log.debug("------------selectCustomerList------getResultCd ----------:" + custRes.getResultCd());
			log.debug("------------selectCustomerList------getResultCd ----------:" + custRes.getResultDt());
			log.debug("------------selectCustomerList------ResultMsg ----------:" + custRes.getResultMsg());
			if (custRes.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {
				if (custRes.getData() != null) {
					List<EbmCustomer> ebmCustomers = new ArrayList<>();
					CustResData data = custRes.getData();
					for (Customer customer : data.getCustList()) {
						log.debug(String.format(
								"------------selectCustomerList------ResultMsg --------Customer name:{%s},Customer Tin:{%s},  Status:{%s}",
								customer.getTaxprNm(), customer.getTin(), customer.getTaxprSttsCd()));
						EbmCustomer ebmCustomer = EbmCustomer.builder().customerTin(customer.getTin())
								.customerName(customer.getTaxprNm()).branchId(registeredBranch.getSettingValue())
								.customerAddress(customer.getLocDesc()).customerNumber(customer.getTin())
								.customerPhone(null)
								.registeredTin(registeredTin.getSettingValue()).sentServer(true).build();
						ebmCustomers.add(ebmCustomer);
						ebmCustomerService.saveEntity(ebmCustomer);
					}

				}
				i = j + 1;
				j = j + 5000;

			} else {
				j = z;
			}

			
		}

	}

	@Override
	public Map<String, Object> saveBhfCustomer(String customerAddress, String branchId, String customerName,
			String customerNumber, String customerTin, String customerEmail, String faxNo, String useYn,
			String registeredTin, String customerPone) {
		Map<String, Object> result = new HashMap<>();
		BhfCustSaveReq bhfCustSaveReq = new BhfCustSaveReq();
		bhfCustSaveReq.setAdrs(customerAddress);
		bhfCustSaveReq.setBhfId(branchId);
		bhfCustSaveReq.setCustNm(customerName);
		bhfCustSaveReq.setCustNo(customerNumber);
		bhfCustSaveReq.setCustTin(customerTin);

		bhfCustSaveReq.setEmail(customerEmail);
		bhfCustSaveReq.setFaxNo(faxNo);
		bhfCustSaveReq.setModrId(VsdcRRAUtil.REGISTERED_USER);
		bhfCustSaveReq.setModrNm(VsdcRRAUtil.REGISTERED_USER);
		bhfCustSaveReq.setRegrId(VsdcRRAUtil.REGISTERED_USER);
		bhfCustSaveReq.setRegrNm(VsdcRRAUtil.REGISTERED_USER);
		bhfCustSaveReq.setRemark(VsdcRRAUtil.REGISTERED_USER);
		bhfCustSaveReq.setTelNo(customerPone);
		bhfCustSaveReq.setTin(registeredTin);
		bhfCustSaveReq.setUseYn(useYn);
		BhfExcute bhfExcute = new BhfExcute();

		BhfCustSaveRes bhfCustSaveRes = bhfExcute.saveBhfCustomer(bhfCustSaveReq);
		log.debug(bhfCustSaveRes.getResultCd());
		log.debug(bhfCustSaveRes.getResultDt());
		log.debug(bhfCustSaveRes.getResultMsg());

		if (bhfCustSaveRes.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {
			
			result.put(VsdcRRAUtil.OBJECT_KEY, bhfCustSaveRes.getResultMsg());
		} else {
			result.put(VsdcRRAUtil.ERROR_KEY, bhfCustSaveRes.getResultMsg());
			result.put(VsdcRRAUtil.OBJECT_KEY, null);
		}
		return result;
	}

	@Override
	public Map<String, Object> saveBhfUser(String username, String userId, String registeredTin, String password,
			String userPhone, String branchId, String authorityCode, String userAddress, String useYn) {
		Map<String, Object> result = new HashMap<>();
		BhfUserSaveReq bhfUserSaveReq = new BhfUserSaveReq();
		bhfUserSaveReq.setAdrs(userAddress);
		bhfUserSaveReq.setAuthCd(authorityCode);
		bhfUserSaveReq.setBhfId(branchId);
		bhfUserSaveReq.setCntc(userPhone);
		bhfUserSaveReq.setModrId(VsdcRRAUtil.REGISTERED_USER);
		bhfUserSaveReq.setModrNm(VsdcRRAUtil.REGISTERED_USER);
		bhfUserSaveReq.setPwd(password);
		bhfUserSaveReq.setRegrId(VsdcRRAUtil.REGISTERED_USER);
		bhfUserSaveReq.setRegrNm(VsdcRRAUtil.REGISTERED_USER);
		bhfUserSaveReq.setRemark(VsdcRRAUtil.REGISTERED_USER);
		bhfUserSaveReq.setTin(registeredTin);
		bhfUserSaveReq.setUserId(userId);
		bhfUserSaveReq.setUserNm(username);
		bhfUserSaveReq.setUseYn(useYn);
		BhfExcute bhfExcute = new BhfExcute();
		BhfUserSaveRes bhfUserSaveRes = bhfExcute.saveBhfUser(bhfUserSaveReq);
		log.debug(bhfUserSaveRes.getResultCd());
		log.debug(bhfUserSaveRes.getResultDt());
		log.debug(bhfUserSaveRes.getResultMsg());

		if (bhfUserSaveRes.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {
			
			result.put(VsdcRRAUtil.OBJECT_KEY, bhfUserSaveRes.getResultMsg());
		} else {
			result.put(VsdcRRAUtil.ERROR_KEY, bhfUserSaveRes.getResultMsg());
			result.put(VsdcRRAUtil.OBJECT_KEY, null);
		}
		return result;
	}

	@Override
	public Map<String, Object> saveBhfInsurance(String registeredTin, String branchId, String insuranceCode,
			String insuranceName, BigDecimal rate, String useYn) {
		Map<String, Object> result = new HashMap<>();
		BhfExcute bhfExcute = new BhfExcute();
		BhfInsuranceSaveReq bhfInsuranceSaveReq = new BhfInsuranceSaveReq();
		bhfInsuranceSaveReq.setBhfId(branchId);
		bhfInsuranceSaveReq.setIsrccCd(insuranceCode);
		bhfInsuranceSaveReq.setIsrccNm(insuranceName);
		bhfInsuranceSaveReq.setIsrcRt(rate);
		bhfInsuranceSaveReq.setModrId(VsdcRRAUtil.REGISTERED_USER);
		bhfInsuranceSaveReq.setModrNm(VsdcRRAUtil.REGISTERED_USER);
		bhfInsuranceSaveReq.setRegrId(VsdcRRAUtil.REGISTERED_USER);
		bhfInsuranceSaveReq.setRegrNm(VsdcRRAUtil.REGISTERED_USER);
		bhfInsuranceSaveReq.setTin(registeredTin);
		bhfInsuranceSaveReq.setUseYn(useYn);
		BhfInsuranceSaveRes bhfInsuranceSaveRes = bhfExcute.saveBhfInsurance(bhfInsuranceSaveReq);
		if (bhfInsuranceSaveRes.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {
			
			result.put(VsdcRRAUtil.OBJECT_KEY, bhfInsuranceSaveRes.getResultMsg());
		} else {
			result.put(VsdcRRAUtil.ERROR_KEY, bhfInsuranceSaveRes.getResultMsg());
			result.put(VsdcRRAUtil.OBJECT_KEY, null);
		}
		return result;
	}

	@Override
	public void selectBhfList() {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		EbmSetting pullDate = ebmSettingService.getByName(SettingName.PULL_DATE.name());
		BhfReq custReq = new BhfReq();
		custReq.setTin(registeredTin.getSettingValue());
		custReq.setBhfId(registeredBranch.getSettingValue());
		custReq.setLastReqDt(pullDate.getSettingValue());

		BhfExcute bhfExcute = new BhfExcute();

		BhfRes bhfRes = bhfExcute.selectBhfbList(custReq);

		log.debug("------------selectBhfList------getResultCd ----------:" + bhfRes.getResultCd());
		log.debug("------------selectBhfList------getResultCd ----------:" + bhfRes.getResultDt());
		log.debug("------------selectBhfList------ResultMsg ----------:" + bhfRes.getResultMsg());
		ebm.vsdc.excute.model.BhfRes.CodeResData data = bhfRes.getData();
		log.debug("------------selectBhfList------ResultMsg ----------:" + data);
		if (data != null) {
			for (Bhf branch : data.getBhfList()) {
				ebmTaxpayerBranchService.saveNewBranch(branch.getTin(), branch.getBhfId(), branch.getBhfNm(),
						branch.getBhfSttsCd(), branch.getPrvncNm(), branch.getDstrtNm(), branch.getSctrNm(),
						branch.getLocDesc(), branch.getHqYn(), branch.getMgrNm(), branch.getMgrTelNo(),
						branch.getMgrEmail());
			}
		}
	}

	@Override
	public void selectNoticeList() {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		EbmSetting pullDate = ebmSettingService.getByName(SettingName.PULL_DATE.name());
		NoticeReq noticeReq = new NoticeReq();
		noticeReq.setTin(registeredTin.getSettingValue());
		noticeReq.setBhfId(registeredBranch.getSettingValue());
		noticeReq.setLastReqDt(pullDate.getSettingValue());

		NoticeExcute noticeExcute = new NoticeExcute();
		NoticeRes noticeRes = noticeExcute.selectNoticeList(noticeReq);
		List<EbmNotice> notices = new ArrayList<>();
		if (noticeRes != null) {
			NoticeResData noticeResData = noticeRes.getData();
			if (noticeResData != null) {
				for (Notice notice : noticeResData.getNoticeList()) {

					notices.add(EbmNotice.builder().registrationNumber(notice.getRegrNm()).title(notice.getTitle())
							.noticeCount(notice.getCont()).noticeNumber(notice.getNoticeNo())
							.noticeUrl(notice.getDtlUrl()).build());
				}

			}
		}

	}

	@Override
	public void skmmManager() {
		try {

		} catch (Exception e) {

		}
	}

	@Override
	public TrnsSalesSaveWrRes generateReceipt(TrnsSalesSaveWrReq req) {
		TrnsSalesExcute excute = new TrnsSalesExcute();
		TrnsSalesSaveWrRes resreponse = null;
		try {
			resreponse = excute.saveTrnsSales(req);
			if (resreponse != null) {
				log.debug("======================RESPONSE CODE:" + resreponse.getResultCd());
				log.debug("===================RESPONSE MESSAGE:" + resreponse.getResultMsg());
				log.debug("======================RESPONSE DATE:" + resreponse.getResultDt());
				TrnsSalesSaveWrResData data = resreponse.getData();
				if (data != null) {
					log.debug("====================RESPONSE  SDC ID:" + resreponse.getData().getSdcId());
					log.debug("==============RESPONSE INTERNAL DATA:" + resreponse.getData().getIntrlData());
					log.debug("==================RESPONSE SIGNATURE:" + resreponse.getData().getRcptSign());
					log.debug("=================RESPONSE RECEIPT NO:" + resreponse.getData().getRcptNo());
					log.debug("==============RESPONSE TOTAL RECEIPT:" + resreponse.getData().getTotRcptNo());
					log.debug("==============RESPONSE SDC DATE:" + resreponse.getData().getVsdcRcptPbctDate());
				} else {
					log.debug("================RESPONSE DATA IS NULL");
				}
			} else {
				log.debug("=====================RESPONSE  IS NULL:" + resreponse);
			}
			return resreponse;
		} catch (Exception e) {
			log.debug(String.format("================RESPONSE DATA IS NULL:%s", e.toString()));
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public void selectImportItemList() {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		EbmSetting pullDate = ebmSettingService.getByName(SettingName.PULL_DATE.name());
		ImportItemReq imptItemReq = new ImportItemReq();
		imptItemReq.setTin(registeredTin.getSettingValue());
		imptItemReq.setBhfId(registeredBranch.getSettingValue());
		imptItemReq.setLastReqDt(pullDate.getSettingValue());

		ImportItemExcute importItemExcute = new ImportItemExcute();
		ImportItemRes importItemRes = importItemExcute.selectImportItemList(imptItemReq);
		if (importItemRes != null) {
			ImportItemResData data = importItemRes.getData();

			if (data != null) {
				for (ImportItem item : data.getItemList()) {
					log.debug(
							String.format("---------selectImportItemList--------Status:%s", item.getImptItemsttsCd()));
					EbmImportation ebmImportation = EbmImportation.builder().agentName(item.getAgntNm())
							.declarationDate(DataFormatUtil.parseVsdcStringDateToLocalDate(item.getDclDe()))
							.declarationNumber(item.getDclNo()).exchangeRate(item.getInvcFcurExcrt())
							.exportNationCode(item.getExptNatCd()).foreignCurrencyAmount(item.getInvcFcurAmt())
							.foreignCurrencyCode(item.getInvcFcurCd()).grossWeight(item.getTotWt())
							.hsCode(item.getHsCd())
							.importStatus(DataFormatUtil.parseImportationStatus(item.getImptItemsttsCd()))
							.itemName(item.getItemNm()).itemSequence(item.getItemSeq()).netWeight(item.getNetWt())
							.orgineNationCode(item.getOrgnNatCd()).packaging(item.getPkg())
							.packagingUnitCode(item.getPkgUnitCd()).quantity(item.getPkg())
							.quantityUnitCode(item.getQtyUnitCd()).supplierName(item.getSpplrNm())
							.taskCode(item.getTaskCd()).sentServer(true).build();

					ebmImportationService.saveEntity(ebmImportation);

				}

			}
		}

	}

	@Override
	public void updateImportItem(List<EbmImportation> importations) {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		ImportItemExcute importItemExcute = new ImportItemExcute();

		for (EbmImportation ebmImportation : importations) {
			if (ebmImportation.getEbmItem() != null) {
				ImportItemUpdateReq importItemUpdateReq = new ImportItemUpdateReq();
				importItemUpdateReq.setTin(registeredTin.getSettingValue());
				importItemUpdateReq.setBhfId(registeredBranch.getSettingValue());
				importItemUpdateReq.setHsCd(ebmImportation.getHsCode());
				importItemUpdateReq.setImptItemSttsCd(ebmImportation.getImportStatus().getStatus());
				importItemUpdateReq.setItemCd(ebmImportation.getEbmItem().getItemCode());
				importItemUpdateReq.setItemClsCd(ebmImportation.getEbmItem().getItemClassificationCode());
				importItemUpdateReq.setItemSeq(ebmImportation.getItemSequence());
				importItemUpdateReq.setModrId(VsdcRRAUtil.REGISTERED_USER);
				importItemUpdateReq.setModrNm(VsdcRRAUtil.REGISTERED_USER);
				importItemUpdateReq.setRemark(VsdcRRAUtil.REGISTERED_USER);
				importItemUpdateReq.setTaskCd(ebmImportation.getTaskCode());
				importItemUpdateReq.setDclDe(
						DataFormatUtil.parseCisStringDateToVsdcStringDate(ebmImportation.getDeclarationDate()));
				ImportItemUpdateRes response = importItemExcute.updateImportItem(importItemUpdateReq);
				if (response.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {
					ebmImportationService.updateSentToServer(ebmImportation);
				}
			}
		}

	}

	@Override
	public void selectItemList() {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		EbmSetting pullDate = ebmSettingService.getByName(SettingName.PULL_DATE.name());
		ItemReq itemReq = new ItemReq();
		itemReq.setTin(registeredTin.getSettingValue());
		itemReq.setBhfId(registeredBranch.getSettingValue());
		itemReq.setLastReqDt(pullDate.getSettingValue());
		ItemExcute itemExcute = new ItemExcute();
		ItemRes itemRes = itemExcute.selectItemList(itemReq);
		ItemResData data = itemRes.getData();
		if (data != null) {
			List<EbmItem> items = new ArrayList<>();
			for (Item item : data.getItemList()) {
				log.debug(String.format("---------selectItemList--------Item:%s", item.getItemNm()));
				EbmItem.builder().registredTin(item.getTin()).registBranchId(item.getRegBhfId())
						.rraModif(item.getRraModYn()).itemName(item.getItemNm()).itemCode(item.getItemCd())
						.barcode(item.getBcd()).batchNumber(item.getBtchNo())
						.itemClassificationCode(item.getItemClsCd()).itemStandardName(item.getItemStdNm())
						.itemTypeCode(item.getItemTyCd()).originPlaceCode(item.getOrgnNatCd())
						.packagingUnitCode(item.getPkgUnitCd()).quantityUnitCode(item.getQtyUnitCd())
						.defaultUnitPrice(item.getDftPrc()).groupUnitPrice1(item.getGrpPrcL1())
						.groupUnitPrice2(item.getGrpPrcL2()).groupUnitPrice3(item.getGrpPrcL3())
						.groupUnitPrice4(item.getGrpPrcL4()).groupUnitPrice5(item.getGrpPrcL5())
						.saftyQuantity(item.getSftyQty()).taxationTypeCode(item.getTaxTyCd())
						.insuranceApplicable(item.getIsrcAplcbYn()).additionalInformation(item.getAddInfo())
						.useYn(item.getUseYn()).build();
				EbmItem ebmItem = new EbmItem(item.getTin(), item.getRegBhfId(), item.getRraModYn(), item.getItemNm(),
						item.getItemCd(), item.getBcd(), item.getBtchNo(), item.getItemClsCd(), item.getItemStdNm(),
						item.getItemTyCd(), item.getOrgnNatCd(), item.getPkgUnitCd(), item.getQtyUnitCd(),
						item.getDftPrc(), item.getDftPrc(), item.getGrpPrcL1(), item.getGrpPrcL2(), item.getGrpPrcL3(),
						item.getGrpPrcL4(), item.getGrpPrcL5(), item.getSftyQty(), item.getTaxTyCd(),
						item.getIsrcAplcbYn(), VsdcRRAUtil.REGISTERED_USER, item.getAddInfo(), item.getUseYn(),
						new BigDecimal("0.00"), true, true);
				items.add(ebmItem);
			}
			ebmItemService.saveList(items);
		}
	}

	@Override
	public void sendItemToServer(List<EbmItem> items) {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		for (EbmItem item : items) {
			ItemExcute itemExcute = new ItemExcute();
			ItemSaveReq itemSaveReq = new ItemSaveReq();

			itemSaveReq.setTin(registeredTin.getSettingValue());
			itemSaveReq.setBhfId(registeredBranch.getSettingValue());
			itemSaveReq.setItemNm(item.getItemName());
			itemSaveReq.setItemCd(item.getItemCode());
			itemSaveReq.setBcd(item.getBarcode());
			itemSaveReq.setBtchNo(item.getBatchNumber());
			itemSaveReq.setItemClsCd(item.getItemClassificationCode());
			itemSaveReq.setItemStdNm(item.getItemStandardName());
			itemSaveReq.setItemTyCd(
					item.getItemTypeCode() == null ? VsdcRRAUtil.registredItemTypeCode : item.getItemTypeCode());
			itemSaveReq.setOrgnNatCd(item.getOriginPlaceCode() == null ? VsdcRRAUtil.registredOrginalPlaceCode
					: item.getOriginPlaceCode());
			itemSaveReq.setPkgUnitCd(
					item.getPackagingUnitCode() == null ? VsdcRRAUtil.registredPkgUnitCd : item.getPackagingUnitCode());
			itemSaveReq.setQtyUnitCd(
					item.getQuantityUnitCode() == null ? VsdcRRAUtil.registredQtyUnitCd : item.getQuantityUnitCode());
			itemSaveReq.setDftPrc(
					item.getDefaultUnitPrice() == null ? new BigDecimal("0.00") : item.getDefaultUnitPrice());
			itemSaveReq.setGrpPrcL1(item.getGroupUnitPrice1());
			itemSaveReq.setGrpPrcL2(item.getGroupUnitPrice2());
			itemSaveReq.setGrpPrcL3(item.getGroupUnitPrice3());
			itemSaveReq.setGrpPrcL4(item.getGroupUnitPrice4());
			itemSaveReq.setGrpPrcL5(item.getGroupUnitPrice5());
			itemSaveReq.setSftyQty(item.getSaftyQuantity());
			itemSaveReq.setTaxTyCd(
					item.getTaxationTypeCode() == null ? VsdcRRAUtil.registredTaxTyCd : item.getTaxationTypeCode());
			itemSaveReq.setIsrcAplcbYn(
					item.getInsuranceApplicable() == null ? VsdcRRAUtil.registredYN : item.getInsuranceApplicable());
			itemSaveReq.setAddInfo(item.getAdditionalInformation());
			itemSaveReq.setUseYn(item.getUseYn() == null ? VsdcRRAUtil.registredYN : item.getUseYn());
			itemSaveReq.setRegrNm(
					item.getRegistrationName() == null ? VsdcRRAUtil.REGISTERED_USER : item.getRegistrationName());
			itemSaveReq.setRegrId(
					item.getRegistrationName() == null ? VsdcRRAUtil.REGISTERED_USER : item.getRegistrationName());
			itemSaveReq.setModrId(
					item.getRegistrationName() == null ? VsdcRRAUtil.REGISTERED_USER : item.getRegistrationName());
			itemSaveReq.setModrNm(
					item.getRegistrationName() == null ? VsdcRRAUtil.REGISTERED_USER : item.getRegistrationName());
			log.debug(String.format("Item header{%s} and {%s}", itemSaveReq.getTin(), itemSaveReq.getBhfId()));
			ItemSaveRes itemSaveRes = itemExcute.saveItem(itemSaveReq);
			if (itemSaveRes != null && itemSaveRes.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {
				
				ebmItemService.updateSentItemToSever(item);
			}

		}
	}

	@Override
	public void insertStockIO(List<EbmStockMove> moves) {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		for (EbmStockMove move : moves) {
			List<StockIoItemSaveReq> items = new ArrayList<>();
			log.debug(String.format("-------------------Number of Item in stock move:%s", move.getMoveItems()));
			for (EbmStockMoveItem moveItem : move.getMoveItems()) {
				StockIoItemSaveReq stockIoItemSaveReq = new StockIoItemSaveReq();
				stockIoItemSaveReq.setBcd(moveItem.getBarcode());
				stockIoItemSaveReq.setItemCd(moveItem.getItemCode());
				stockIoItemSaveReq.setItemClsCd(moveItem.getItemClassificationCode());
				stockIoItemSaveReq.setItemExprDt("");
				stockIoItemSaveReq.setItemNm(moveItem.getItemName());
				stockIoItemSaveReq.setItemSeq(moveItem.getSequanceNumber());
				stockIoItemSaveReq.setPkg(moveItem.getPackaging());
				stockIoItemSaveReq.setPkgUnitCd(moveItem.getPackageUnitCode());
				stockIoItemSaveReq.setPrc(moveItem.getSaleUnitPrice());
				stockIoItemSaveReq.setQty(moveItem.getQuantity());
				stockIoItemSaveReq.setQtyUnitCd(moveItem.getQuantityUnitCode());
				stockIoItemSaveReq.setSplyAmt(DataFormatUtil.generateZeroDecimal(moveItem.getPurcaseUnitPrice()));
				stockIoItemSaveReq.setTaxAmt(moveItem.getTaxAmount());
				stockIoItemSaveReq.setTaxblAmt(moveItem.getTaxableAmount());
				stockIoItemSaveReq.setTaxTyCd(moveItem.getTaxLabel());
				stockIoItemSaveReq.setTotAmt(moveItem.getTotalAmount());
				stockIoItemSaveReq.setTotDcAmt(moveItem.getDiscountAmount());
				items.add(stockIoItemSaveReq);

			}

			StockIoSaveReq stockIOSaveReq = new StockIoSaveReq();
			StockIoExcute stockIoExcute = new StockIoExcute();
			stockIOSaveReq.setTin(registeredTin.getSettingValue());
			stockIOSaveReq.setBhfId(registeredBranch.getSettingValue());
			stockIOSaveReq.setCustBhfId(move.getCustomerBranchId());
			stockIOSaveReq.setCustNm(move.getCustomerName());
			stockIOSaveReq.setCustTin(move.getCustomerTin());
			stockIOSaveReq.setItemList(items);
			stockIOSaveReq.setModrId(VsdcRRAUtil.REGISTERED_USER);
			stockIOSaveReq.setModrNm(VsdcRRAUtil.REGISTERED_USER);
			stockIOSaveReq.setOcrnDt(DataFormatUtil.parseCisStringDateToVsdcStringDate(move.getOccurredDate()));
			stockIOSaveReq.setOrgSarNo(0);
			stockIOSaveReq.setRegrId(VsdcRRAUtil.REGISTERED_USER);
			stockIOSaveReq.setRegrNm(VsdcRRAUtil.REGISTERED_USER);
			stockIOSaveReq.setRemark(VsdcRRAUtil.REGISTERED_USER);
			stockIOSaveReq.setSarNo(0);
			stockIOSaveReq.setSarTyCd(move.getStoredReleasedTypeCode());
			stockIOSaveReq.setTotAmt(move.getTotalAmount());
			stockIOSaveReq.setTotItemCnt(items.size());
			stockIOSaveReq.setTotTaxAmt(move.getTotalTaxAmount());
			stockIOSaveReq.setTotTaxblAmt(move.getTotalTaxableAmount());
			stockIOSaveReq.setRegTyCd(move.getRegistrationTypeCode());
			StockIoSaveRes stockIoSaveRes = stockIoExcute.insertStockIO(stockIOSaveReq);
			if (stockIoSaveRes.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {
				
				ebmStockMoveService.updateStockSent(move);
			}
		}
	}

	@Override
	public void selectStockMoveList() {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		EbmSetting pullDate = ebmSettingService.getByName(SettingName.PULL_DATE.name());
		StockIoExcute stockIoExcute = new StockIoExcute();
		StockMoveReq stockMoveReq = new StockMoveReq();
		stockMoveReq.setTin(registeredTin.getSettingValue());
		stockMoveReq.setBhfId(registeredBranch.getSettingValue());
		stockMoveReq.setLastReqDt(pullDate.getSettingValue());

		StockMoveRes stockMoveRes = stockIoExcute.selectStockMoveList(stockMoveReq);
		if (stockMoveRes != null) {
			log.debug("======================RESPONSE CODE:" + stockMoveRes.getResultCd());
			log.debug("===================RESPONSE MESSAGE:" + stockMoveRes.getResultMsg());
			log.debug("======================RESPONSE DATE:" + stockMoveRes.getResultDt());

			StockMoveResData stockMoveResData = stockMoveRes.getData();
			if (stockMoveResData != null) {

				for (StockMove stockMove : stockMoveResData.getStockList()) {
					EbmStockMove.builder().customerBranchId(stockMove.getCustBhfId())
							.customerTin(stockMove.getCustTin())
							.occurredDate(DataFormatUtil.parseVsdcStringDateToLocalDate(stockMove.getOcrnDt()))
							.remark(stockMove.getRemark()).storedReleasedNumber(stockMove.getSarNo())
							.totalAmount(stockMove.getTotAmt()).totalItemNumber(stockMove.getTotItemCnt())
							.totalTaxAmount(stockMove.getTotTaxAmt()).totalTaxableAmount(stockMove.getTotTaxblAmt())
							.build();

					for (StockMoveItem moveitem : stockMove.getItemList()) {

						EbmStockMoveItem.builder().barcode(moveitem.getBcd()).itemName(moveitem.getItemNm())
								.itemCode(moveitem.getItemCd()).itemClassificationCode(moveitem.getItemClsCd())
								.expirationDate(DataFormatUtil.parseVsdcStringDateToLocalDate(moveitem.getItemExprDt()))
								.sequanceNumber(moveitem.getItemSeq()).packaging(moveitem.getPkg())
								.packageUnitCode(moveitem.getPkgUnitCd()).saleUnitPrice(moveitem.getPrc())
								.quantity(moveitem.getQty()).quantityUnitCode(moveitem.getQtyUnitCd())
								.purcaseUnitPrice(moveitem.getSplyAmt()).taxAmount(moveitem.getTaxAmt())
								.totalAmount(moveitem.getTotAmt()).taxLabel(moveitem.getTaxTyCd())
								.discountAmount(moveitem.getTotDcAmt()).build();

					}

				}

			}
		}
	}

	@Override
	public void saveStockMaster(List<EbmItem> items) {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		List<EbmItem> updatedItems = new ArrayList<>();
		for (EbmItem item : items) {
			StockMasterSaveReq stockMasterSaveReq = new StockMasterSaveReq();
			stockMasterSaveReq.setTin(registeredTin.getSettingValue());
			stockMasterSaveReq.setBhfId(registeredBranch.getSettingValue());
			stockMasterSaveReq.setItemCd(item.getItemCode());
			stockMasterSaveReq.setModrId(
					item.getRegistrationName() == null ? VsdcRRAUtil.REGISTERED_USER : item.getRegistrationName());
			stockMasterSaveReq.setModrNm(
					item.getRegistrationName() == null ? VsdcRRAUtil.REGISTERED_USER : item.getRegistrationName());
			stockMasterSaveReq.setRegrId(
					item.getRegistrationName() == null ? VsdcRRAUtil.REGISTERED_USER : item.getRegistrationName());
			stockMasterSaveReq.setRegrNm(
					item.getRegistrationName() == null ? VsdcRRAUtil.REGISTERED_USER : item.getRegistrationName());
			stockMasterSaveReq.setRsdQty(item.getCurrentStock());

			StockMasterExcute stockMasterExcute = new StockMasterExcute();
			StockMasterSaveRes response = stockMasterExcute.saveStockMaster(stockMasterSaveReq);
			if (response != null && response.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {

				
				item.setStockSent(true);
				updatedItems.add(item);
				ebmItemService.updateSentStockToSever(item);
			}
		}

		log.debug(String.format("======================saveStockMaster:{%s}", updatedItems.size()));
	}

	@Override
	public void saveBhfCustomer(List<EbmCustomer> ebmCustomers) {
		for (EbmCustomer customer : ebmCustomers) {
			BhfCustSaveReq bhfCustSaveReq = new BhfCustSaveReq();
			bhfCustSaveReq.setAdrs(customer.getCustomerAddress());
			bhfCustSaveReq.setBhfId(customer.getBranchId());
			bhfCustSaveReq.setCustNm(customer.getCustomerName());
			bhfCustSaveReq.setCustNo(customer.getCustomerTin());
			bhfCustSaveReq.setCustTin(customer.getCustomerTin());
			bhfCustSaveReq.setEmail(customer.getCustomerEmail());
			bhfCustSaveReq.setFaxNo(customer.getFaxNo());
			bhfCustSaveReq.setModrId(VsdcRRAUtil.REGISTERED_USER);
			bhfCustSaveReq.setModrNm(VsdcRRAUtil.REGISTERED_USER);
			bhfCustSaveReq.setRegrId(VsdcRRAUtil.REGISTERED_USER);
			bhfCustSaveReq.setRegrNm(VsdcRRAUtil.REGISTERED_USER);
			bhfCustSaveReq.setRemark(VsdcRRAUtil.REGISTERED_USER);
			bhfCustSaveReq.setTelNo(customer.getCustomerPhone());
			bhfCustSaveReq.setTin(customer.getRegisteredTin());
			bhfCustSaveReq.setUseYn(customer.getUseYn());
			BhfExcute bhfExcute = new BhfExcute();

			BhfCustSaveRes bhfCustSaveRes = bhfExcute.saveBhfCustomer(bhfCustSaveReq);
			log.debug(bhfCustSaveRes.getResultCd());
			log.debug(bhfCustSaveRes.getResultDt());
			log.debug(bhfCustSaveRes.getResultMsg());

			if (bhfCustSaveRes.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {
				
				ebmCustomerService.updateEntityWithSentToServer(customer);

			}
		}

	}

	@Override
	public void selectTrnsPurchaseSalesList() {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		EbmSetting pullDate = ebmSettingService.getByName(SettingName.PULL_DATE.name());
		TrnsPurchaseSalesReq trnsPurchaseSalesReq = new TrnsPurchaseSalesReq();
		trnsPurchaseSalesReq.setTin(registeredTin.getSettingValue());
		trnsPurchaseSalesReq.setBhfId(registeredBranch.getSettingValue());
		trnsPurchaseSalesReq.setLastReqDt(pullDate.getSettingValue());

		TrnsPurchaseExcute trnsPurchaseExcute = new TrnsPurchaseExcute();
		TrnsPurchaseSalesRes trnsPurchaseSalesRes = trnsPurchaseExcute
				.selectTrnsPurchaseSalesList(trnsPurchaseSalesReq);

		log.debug("======================RESPONSE CODE:" + trnsPurchaseSalesRes.getResultCd());
		log.debug("===================RESPONSE MESSAGE:" + trnsPurchaseSalesRes.getResultMsg());
		log.debug("======================RESPONSE DATE:" + trnsPurchaseSalesRes.getResultDt());

		if (trnsPurchaseSalesRes.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {
			
			TrnsPurchaseSalesResData data = trnsPurchaseSalesRes.getData();
			if (data != null) {
				List<TrnsPurchaseSales> purchases = data.getSaleList();
				for (TrnsPurchaseSales p : purchases) {
					List<EbmPurchaseItem> purchaseItems = new ArrayList<>();
					EbmPurchase ebmPurchase = EbmPurchase.builder()
							.cisDate(DataFormatUtil.parseApiStringDateToLocalDateTime(p.getCfmDt()))
							.paymentCode(p.getPmtTyCd()).transactionType(p.getRcptTyCd()).remark(p.getRemark())
							.stockReleasedDate(DataFormatUtil.parseVsdcStringDateToLocalDate(p.getSalesDt()))
							.supplierBranch(p.getSpplrBhfId()).invoiceNumber(p.getSpplrInvcNo())
							.supplierName(p.getSpplrNm()).supplierTin(p.getSpplrTin())
							.stockReleasedDate(DataFormatUtil.parseVsdcStringDateToLocalDate(p.getStockRlsDt()))
							.taxableAmountA(p.getTaxAmtA()).taxableAmountB(p.getTaxAmtB())
							.taxableAmountC(p.getTaxAmtC()).taxableAmountD(p.getTaxAmtD()).taxAmountA(p.getTaxAmtA())
							.taxAmountB(p.getTaxAmtB()).taxAmountC(p.getTaxAmtC()).taxAmountD(p.getTaxAmtD())
							.taxRateA(p.getTaxRtA()).taxRateB(p.getTaxRtB()).taxRateC(p.getTaxRtC())
							.taxRateD(p.getTaxRtD()).totalAmount(p.getTotAmt()).totalTaxAmount(p.getTotTaxAmt())
							.totalTaxableAmount(p.getTotTaxblAmt()).itemNumber(p.getTotItemCnt()).sentServer(true)
							.registeredTin(registeredTin.getSettingValue()).branchId(p.getSpplrBhfId())
							.registrationTypeCode(Ebm2StaticValue.AUTOMATIC_REGISTRATION_TYPE_CODE).build();

					List<TrnsPurchaseSalesItem> items = p.getItemList();
					for (TrnsPurchaseSalesItem item : items) {

						EbmPurchaseItem purchaseItem = EbmPurchaseItem.builder().barcode(item.getBcd())
								.discountAmount(item.getDcAmt()).discountRate(item.getDcRt()).itemCode(item.getItemCd())
								.itemClassificationCode(item.getItemClsCd()).itemName(item.getItemNm())
								.itemSequance(item.getItemSeq()).packaging(item.getPkg())
								.packageUnit(item.getPkgUnitCd()).saleUnitPrice(item.getPrc()).quantity(item.getQty())
								.quantityUnitCode(item.getQtyUnitCd()).purchaseUnitPrice(item.getSplyAmt())
								.taxAmount(item.getTaxAmt()).taxableAmount(item.getTaxblAmt())
								.taxLabel(item.getTaxTyCd()).totalAmount(item.getTotAmt()).ebmPurchase(ebmPurchase)
								.build();
						log.debug(purchaseItem.getItemName());
						purchaseItems.add(purchaseItem);
					}
					ebmPurchase.setPurchaseItems(purchaseItems);
					ebmPurchaseService.saveNewEbmPurchase(ebmPurchase);
				}

			}
		}

	}

	@Override
	public void updatePurchase(List<EbmPurchase> purchases) {
		EbmSetting registeredTin = ebmSettingService.getByName(SettingName.CURRENT_TAXPAYER_TIN.name());
		EbmSetting registeredBranch = ebmSettingService.getByName(SettingName.CURRENT_BRANCH.name());
		for (EbmPurchase p : purchases) {
			TrnsPurchaseExcute trnsPurchaseExcute = new TrnsPurchaseExcute();
			TrnsPurchaseSaveReq trnsPurchaseSaveReq = new TrnsPurchaseSaveReq();
			trnsPurchaseSaveReq.setTin(registeredTin.getSettingValue());
			trnsPurchaseSaveReq.setBhfId(registeredBranch.getSettingValue());
			trnsPurchaseSaveReq.setCfmDt(null);
			trnsPurchaseSaveReq.setCnclDt(null);
			trnsPurchaseSaveReq.setCnclReqDt(null);
			trnsPurchaseSaveReq.setInvcNo(p.getInvoiceNumber());
			trnsPurchaseSaveReq.setModrId(VsdcRRAUtil.REGISTERED_USER);
			trnsPurchaseSaveReq.setModrNm(VsdcRRAUtil.REGISTERED_USER);
			trnsPurchaseSaveReq.setOrgInvcNo(p.getInvoiceNumber());
			trnsPurchaseSaveReq.setPchsDt(DataFormatUtil.parseCisStringDateToVsdcStringDate(p.getValidatedDate()));
			trnsPurchaseSaveReq.setPchsSttsCd(p.getPurchaseStatus() == null ? PurchaseStatus.APPROVED.getStatus()
					: p.getPurchaseStatus().getStatus());
			trnsPurchaseSaveReq.setPchsTyCd(p.getReceiptType());
			trnsPurchaseSaveReq.setPmtTyCd(p.getPaymentCode());
			trnsPurchaseSaveReq.setRcptTyCd(p.getTransactionType());
			trnsPurchaseSaveReq.setRegrId(VsdcRRAUtil.REGISTERED_USER);
			trnsPurchaseSaveReq.setRegrNm(VsdcRRAUtil.REGISTERED_USER);
			trnsPurchaseSaveReq.setRemark(VsdcRRAUtil.REGISTERED_USER);
			trnsPurchaseSaveReq.setRegTyCd(p.getRegistrationTypeCode());
			trnsPurchaseSaveReq.setRfdDt(null);
			trnsPurchaseSaveReq.setSpplrBhfId(p.getSupplierBranch());
			trnsPurchaseSaveReq.setSpplrInvcNo(p.getInvoiceNumber());
			trnsPurchaseSaveReq.setSpplrNm(p.getSupplierName());
			trnsPurchaseSaveReq.setSpplrTin(p.getSupplierTin());
			trnsPurchaseSaveReq.setTaxAmtA(p.getTaxAmountA());
			trnsPurchaseSaveReq.setTaxAmtB(p.getTaxAmountB());
			trnsPurchaseSaveReq.setTaxAmtC(p.getTaxAmountC());
			trnsPurchaseSaveReq.setTaxAmtD(p.getTaxAmountD());
			trnsPurchaseSaveReq.setTaxblAmtA(p.getTaxableAmountA());
			trnsPurchaseSaveReq.setTaxblAmtB(p.getTaxableAmountB());
			trnsPurchaseSaveReq.setTaxblAmtC(p.getTaxableAmountC());
			trnsPurchaseSaveReq.setTaxblAmtD(p.getTaxableAmountD());
			trnsPurchaseSaveReq.setTaxRtA(p.getTaxRateA());
			trnsPurchaseSaveReq.setTaxRtB(p.getTaxRateB());
			trnsPurchaseSaveReq.setTaxRtC(p.getTaxRateC());
			trnsPurchaseSaveReq.setTaxRtD(p.getTaxRateD());
			trnsPurchaseSaveReq.setTotAmt(p.getTotalAmount());
			trnsPurchaseSaveReq.setTotItemCnt(p.getPurchaseItems().size());
			trnsPurchaseSaveReq.setTotTaxblAmt(p.getTotalTaxableAmount());
			trnsPurchaseSaveReq.setTotTaxAmt(p.getTotalTaxAmount());
			List<TrnsPurchaseSaveItem> list = new ArrayList<>();
			for (EbmPurchaseItem it : p.getPurchaseItems()) {
				TrnsPurchaseSaveItem trnsPurchaseSaveItem = new TrnsPurchaseSaveItem();
				trnsPurchaseSaveItem.setBcd(it.getBarcode());
				trnsPurchaseSaveItem.setDcAmt(it.getDiscountAmount());
				trnsPurchaseSaveItem.setDcRt(it.getDiscountRate());
				trnsPurchaseSaveItem.setItemCd(it.getItemCode());
				trnsPurchaseSaveItem.setItemClsCd(it.getItemClassificationCode());
				trnsPurchaseSaveItem
						.setItemExprDt(DataFormatUtil.parseCisStringDateToVsdcStringDate(it.getExpirationDate()));
				trnsPurchaseSaveItem.setItemNm(it.getItemName());
				trnsPurchaseSaveItem.setItemSeq(it.getItemSequance());
				trnsPurchaseSaveItem.setPkg(it.getPackaging());
				trnsPurchaseSaveItem.setPkgUnitCd(it.getPackageUnit());
				trnsPurchaseSaveItem.setPrc(it.getSaleUnitPrice());
				trnsPurchaseSaveItem.setQty(it.getQuantity());
				trnsPurchaseSaveItem.setQtyUnitCd(it.getQuantityUnitCode());
				trnsPurchaseSaveItem.setSplyAmt(it.getPurchaseUnitPrice());
				trnsPurchaseSaveItem.setSpplrItemCd(it.getItemCode());
				trnsPurchaseSaveItem.setSpplrItemClsCd(it.getItemClassificationCode());
				trnsPurchaseSaveItem.setSpplrItemNm(it.getItemName());
				trnsPurchaseSaveItem.setItemSeq(it.getItemSequance());
				trnsPurchaseSaveItem.setTaxAmt(it.getTaxAmount());
				trnsPurchaseSaveItem.setTaxblAmt(it.getTaxableAmount());
				trnsPurchaseSaveItem.setTaxTyCd(it.getTaxLabel());
				trnsPurchaseSaveItem.setTotAmt(it.getTotalAmount());

				list.add(trnsPurchaseSaveItem);
			}
			trnsPurchaseSaveReq.setItemList(list);
			TrnsPurchaseSaveRes trnsPurchaseSalesRes = trnsPurchaseExcute.saveTrnsPurchase(trnsPurchaseSaveReq);

			log.debug("======================RESPONSE CODE:" + trnsPurchaseSalesRes.getResultCd());
			log.debug("===================RESPONSE MESSAGE:" + trnsPurchaseSalesRes.getResultMsg());
			log.debug("======================RESPONSE DATE:" + trnsPurchaseSalesRes.getResultDt());
			if (trnsPurchaseSalesRes.getResultCd().equals(VsdcRRACodeUtil.SUCCESS)) {
				
				ebmPurchaseService.updateEbmPurchase(p, true);
			}

		}

	}

}
