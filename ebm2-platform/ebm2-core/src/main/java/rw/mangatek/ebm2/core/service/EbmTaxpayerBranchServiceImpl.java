package rw.mangatek.ebm2.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.domain.EbmTaxpayer;
import rw.mangatek.ebm2.core.domain.EbmTaxpayerBranch;
import rw.mangatek.ebm2.core.repository.IEbmTaxpayerBranchRepository;
import rw.mangatek.ebm2.core.repository.IEbmTaxpayerRepository;

@Slf4j
@Service
public class EbmTaxpayerBranchServiceImpl implements IEbmTaxpayerBranchService {

	private final IEbmTaxpayerBranchRepository ebmTaxpayerBranchRepository;
	private final IEbmTaxpayerRepository ebmTaxpayerRepository;

	public EbmTaxpayerBranchServiceImpl(IEbmTaxpayerBranchRepository ebmTaxpayerBranchRepository,
			IEbmTaxpayerRepository ebmTaxpayerRepository) {
		this.ebmTaxpayerBranchRepository = ebmTaxpayerBranchRepository;
		this.ebmTaxpayerRepository = ebmTaxpayerRepository;
	}

	@Override
	public EbmTaxpayerBranch getByBranchIdAndTaxpayer(String branchId, String tin) {
		EbmTaxpayer taxpayer = ebmTaxpayerRepository.getByTaxpayerTin(tin);
		return ebmTaxpayerBranchRepository.getByBranchIdAndTaxpayer(branchId, taxpayer);
	}

	@Override
	public List<EbmTaxpayerBranch> findAllAsList(Pageable pageable) {

		return ebmTaxpayerBranchRepository.findAll(pageable).getContent();
	}

	@Override
	public EbmTaxpayerBranch saveNewBranch(String taxpayerTin, String branchId, String branchName,
			String branchStatusCode, String provinceName, String districtName, String sectorName,
			String locationDescription, String headQuarter, String managerName, String managerContactNumber,
			String manageEmail) {
		log.debug("------------saveNewBranch----------------");
		EbmTaxpayer taxpayer = ebmTaxpayerRepository.getByTaxpayerTin(taxpayerTin);
		EbmTaxpayerBranch exists = ebmTaxpayerBranchRepository.getByBranchIdAndTaxpayer(branchId, taxpayer);
		if (exists == null) {
			EbmTaxpayerBranch branch = EbmTaxpayerBranch.builder().taxpayer(taxpayer).branchId(branchId)
					.branchName(branchName).branchStatusCode(branchStatusCode).provinceName(provinceName)
					.districtName(districtName).sectorName(sectorName).locationDescription(locationDescription)
					.headQuarter(headQuarter).managerName(managerName).managerContactNumber(managerContactNumber)
					.manageEmail(manageEmail).build();
			ebmTaxpayerBranchRepository.save(branch);

		}

		return null;
	}

}
