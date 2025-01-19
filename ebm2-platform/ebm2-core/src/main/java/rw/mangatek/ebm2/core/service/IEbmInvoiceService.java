package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import rw.mangatek.ebm2.core.domain.EbmInvoice;

public interface IEbmInvoiceService {

	public EbmInvoice saveNewInvoice(EbmInvoice ebmInvoice);

	public EbmInvoice updateInvoice(EbmInvoice ebmInvoice, boolean sentToServer);

	public List<EbmInvoice> findAllAsList(Pageable pageable);

	public EbmInvoice getById(String id);
}
