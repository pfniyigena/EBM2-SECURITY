/*************************************************************************************************
 * Copyright (c) Mangatek Ltd(Owner) 2020 to Present. All Rights Reserved.
 * No part of this file may be reproduced or distributed in any form or by any means, 
 * or stored in a database or retrieval system, without the prior written permission of the owner.
 *************************************************************************************************/
package rw.mangatek.ebm2.core.ui.job;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmCustomer;
import rw.mangatek.ebm2.core.domain.EbmImportation;
import rw.mangatek.ebm2.core.domain.EbmItem;
import rw.mangatek.ebm2.core.domain.EbmPurchase;
import rw.mangatek.ebm2.core.domain.EbmStockMove;
import rw.mangatek.ebm2.core.service.IEbmCustomerService;
import rw.mangatek.ebm2.core.service.IEbmImportationService;
import rw.mangatek.ebm2.core.service.IEbmItemService;
import rw.mangatek.ebm2.core.service.IEbmPurchaseService;
import rw.mangatek.ebm2.core.service.IEbmStockMoveService;
import rw.mangatek.ebm2.rra.service.IVsdcService;

/**
 * @author PIERRE FOURIER NIYIGENA
 * @version 1.0
 */
@Component
@EnableScheduling
@Configuration
@Slf4j
public class EbmJob {
	/**
	 * 
	 */
	private final IEbmItemService ebmItemService;
	private final IEbmCustomerService ebmCustomerService;
	private final IEbmStockMoveService ebmStockMoveService;
	private final IEbmImportationService ebmImportationService;
	private final IEbmPurchaseService ebmPurchaseService;
	private final IVsdcService vsdcService;

	public EbmJob(IVsdcService vsdcService, IEbmItemService ebmItemService, IEbmStockMoveService ebmStockMoveService,
			IEbmCustomerService ebmCustomerService, IEbmImportationService ebmImportationService,
			IEbmPurchaseService ebmPurchaseService) {
		this.vsdcService = vsdcService;
		this.ebmItemService = ebmItemService;
		this.ebmStockMoveService = ebmStockMoveService;
		this.ebmCustomerService = ebmCustomerService;
		this.ebmImportationService = ebmImportationService;
		
		this.ebmPurchaseService = ebmPurchaseService;
	}

	@Scheduled(cron = "${send.items.cron.expression}")
	public void sendItems() {
		try {
			List<EbmItem> items = ebmItemService.findBySentServer(false);
			log.debug("-----------------------send.items.cron.expression----------------:" + items.size());
			vsdcService.sendItemToServer(items);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "${send.stock.cron.expression}")
	public void sendStocks() {
		try {
			List<EbmItem> items = ebmItemService.findByStockSent(false);
			log.debug("-----------------------send.stock.cron.expression----------------:" + items.size());
			vsdcService.saveStockMaster(items);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "${send.stock.move.cron.expression}")
	public void sendStockMoves() {
		try {
			List<EbmStockMove> moves = ebmStockMoveService.findBySentServer(false);
			log.debug("-----------------------send.stock.move.cron.expression----------------:" + moves.size());
			vsdcService.insertStockIO(moves);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "${send.customers.cron.expression}")
	public void sendCustomers() {
		try {
			List<EbmCustomer> customers = ebmCustomerService.findBySentServer(false);
			log.debug("-----------------------send.customers.cron.expression----------------:" + customers.size());
			vsdcService.saveBhfCustomer(customers);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "${send.importations.cron.expression}")
	public void sendImportations() {
		try {
			List<EbmImportation> importations = ebmImportationService.findBySentServer(false);
			log.debug(
					"-----------------------send.importations.cron.expression----------------:" + importations.size());
			vsdcService.updateImportItem(importations);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "${send.purchases.cron.expression}")
	public void sendPurchases() {
		try {
			List<EbmPurchase> purchases = ebmPurchaseService.findBySentServer(false);
			log.debug(
					"-----------------------send.purchases.cron.expression----------------:" + purchases.size());
			vsdcService.updatePurchase(purchases);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
