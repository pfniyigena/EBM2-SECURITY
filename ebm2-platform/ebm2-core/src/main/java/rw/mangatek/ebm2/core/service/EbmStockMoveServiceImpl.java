package rw.mangatek.ebm2.core.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmStockMove;
import rw.mangatek.ebm2.core.domain.EbmStockMoveItem;
import rw.mangatek.ebm2.core.repository.IEbmStockMoveRepository;
import rw.mangatek.ebm2.core.util.DataFormatUtil;
import rw.mangatek.ebm2.core.util.Ebm2StaticValue;

@Slf4j
@Service
public class EbmStockMoveServiceImpl implements IEbmStockMoveService {

	private final IEbmStockMoveRepository ebmStockMoveRepository;

	public EbmStockMoveServiceImpl(IEbmStockMoveRepository ebmStockMoveRepository) {
		this.ebmStockMoveRepository = ebmStockMoveRepository;
	}

	@Override
	public void saveEntity(EbmStockMove type) {
		ebmStockMoveRepository.save(type);
	}

	@Override
	public void saveEntities(List<EbmStockMove> types) {
		ebmStockMoveRepository.saveAll(types);
	}

	@Override
	public Iterable<EbmStockMove> findAll(Pageable pageable) {

		return ebmStockMoveRepository.findAll(pageable);
	}

	@Override
	public List<EbmStockMove> findAllAsList(Pageable pageable) {

		return ebmStockMoveRepository.findAll(pageable).getContent();
	}

	@Override
	public void saveEntity(List<EbmStockMoveItem> items,String branchId) {
		log.debug("--------------Calling saveEntity--------------");
		EbmStockMove itemMove = new EbmStockMove();
		BigDecimal totalAmount = new BigDecimal("0.00");
		BigDecimal totalTaxAmount = new BigDecimal("0.00");
		BigDecimal taxRateA = new BigDecimal("0.00");
		BigDecimal taxRateB = new BigDecimal("18.00");
		BigDecimal taxRateC = new BigDecimal("0.00");
		BigDecimal taxRateD = new BigDecimal("0.00");
		List<EbmStockMoveItem> newItems = new ArrayList<>();
		for (EbmStockMoveItem item : items) {
			String taxLabel = item.getTaxLabel();
			BigDecimal preTaxAmount = null;
			BigDecimal taxAmount = null;
			BigDecimal discountAmount = null;
			BigDecimal grossAmount = item.getQuantity().multiply(item.getSaleUnitPrice());
			totalAmount = totalAmount.add(grossAmount);
			if (taxLabel == null || taxLabel.isEmpty()) {
				taxLabel = "A";
			}
			if (taxLabel.equals("A")) {
				if (taxRateA.intValue() > 0) {
					preTaxAmount = taxRateA.divide(new BigDecimal("100.00"));
					taxAmount = grossAmount.subtract(
							grossAmount.divide((new BigDecimal("1.00").add(preTaxAmount)), 2, RoundingMode.HALF_UP));

					totalTaxAmount = totalTaxAmount.add(taxAmount);
				}

			}
			if (taxLabel.equals("B")) {
				if (taxRateB.intValue() > 0) {
					preTaxAmount = taxRateB.divide(new BigDecimal("100.00"));

					taxAmount = grossAmount.subtract(
							grossAmount.divide((new BigDecimal("1.00").add(preTaxAmount)), 2, RoundingMode.HALF_UP));

					totalTaxAmount = totalTaxAmount.add(taxAmount);
				}

			}
			if (taxLabel.equals("C")) {
				if (taxRateC.intValue() > 0) {
					preTaxAmount = taxRateC.divide(new BigDecimal("100.00"));
					taxAmount = grossAmount.subtract(
							grossAmount.divide((new BigDecimal("1.00").add(preTaxAmount)), 2, RoundingMode.HALF_UP));

					totalTaxAmount = totalTaxAmount.add(taxAmount);
				}

			}
			if (taxLabel.equals("D")) {
				if (taxRateD.intValue() > 0) {
					preTaxAmount = taxRateD.divide(new BigDecimal("100.00"));
					taxAmount = grossAmount.subtract(
							grossAmount.divide((new BigDecimal("1.00").add(preTaxAmount)), 2, RoundingMode.HALF_UP));

					totalTaxAmount = totalTaxAmount.add(taxAmount);
				}

			}
			item.setTotalAmount(grossAmount);
			item.setTaxableAmount(grossAmount);
			item.setTaxAmount(DataFormatUtil.generateZeroDecimal(taxAmount));
			item.setDiscountAmount(DataFormatUtil.generateZeroDecimal(discountAmount));
			item.setExpirationDate(LocalDate.of(2050, 12, 31));
			item.setStockMove(itemMove);
			newItems.add(item);
		}
		itemMove.setCustomerBranchId(branchId);
		itemMove.setMoveItems(newItems);
		itemMove.setOccurredDate(LocalDate.now());
		itemMove.setTotalItemNumber(newItems.size());
		itemMove.setRegistrationTypeCode(Ebm2StaticValue.MANUAL_REGISTRATION_TYPE_CODE);
		itemMove.setStoredReleasedTypeCode(Ebm2StaticValue.MANUAL_STORE_RELEASED_TYPE_CODE);
		itemMove.setTotalAmount(totalAmount);
		itemMove.setTotalTaxAmount(totalTaxAmount);
		itemMove.setTotalTaxableAmount(totalAmount);
		itemMove.setSentServer(false);
		ebmStockMoveRepository.save(itemMove);
	}

	@Override
	public EbmStockMove getById(String id) {
		Optional<EbmStockMove> value = ebmStockMoveRepository.findById(id);

		return value.orElseThrow(null);
	}

	@Override
	public List<EbmStockMove> findBySentServer(Boolean sentServer) {

		return ebmStockMoveRepository.findBySentServer(sentServer);
	}

	@Override
	public void updateStockSent(EbmStockMove move) {
		move.setSentServer(true);
		ebmStockMoveRepository.save(move);

	}

}
