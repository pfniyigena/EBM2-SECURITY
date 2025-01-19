/** Copyright Mangatek Ltd to present
All rights reserved.
*/
package rw.mangatek.ebm2.core.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.service.IEbmSettingService;
import rw.mangatek.ebm2.rra.service.IVsdcService;

/**
 * @author pierre.niyigena
 *
 */
@Component
@Slf4j
public class OnEbm2CoreUIStartUp {

	private static String OS = System.getProperty("os.name").toLowerCase();

	@Autowired
	private IVsdcService vsdcService;
	@Autowired
	private IEbmSettingService ebmSettingService;

	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {

		log.info("----------VSDC CORE UI  STARTING.......on :" + OS + " Machine");

		ebmSettingService.initialize();
		initializeVsdc();

	}

	private void initializeVsdc() {
		// vsdcService.initializeVsdc();
		// vsdcService.codeExcute();
		// vsdcService.selectCustomerList();
		// vsdcService.selectItemClsList();
		// vsdcService.selectNoticeList();

		// vsdcService.selectImportItemList();
		vsdcService.skmmManager();
		// vsdcService. selectItemList();
		// vsdcService.insertStockIO();
		// vsdcService.selectStockMoveList();
		//vsdcService.selectTrnsPurchaseSalesList();
	}

}
