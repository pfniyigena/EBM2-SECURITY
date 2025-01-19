package rw.mangatek.ebm2.core.service;

import jakarta.persistence.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rw.mangatek.ebm2.core.domain.SequenceNumber;
import rw.mangatek.ebm2.core.enns.SequenceType;
import rw.mangatek.ebm2.core.repository.ISequenceNumberRepository;



@Service("sequenceNumberService")
public class SequenceNumberServiceImpl implements ISequenceNumberService {
	@Autowired
	private ISequenceNumberRepository sequenceNumberRepository;

	@Override
	public void initializaSequenceNumbers() {
		if (sequenceNumberRepository.getBySequenceType(SequenceType.COMPANY) == null) {

			SequenceNumber caregiverSequence = SequenceNumber.builder().sequence("0")
					.sequenceType(SequenceType.COMPANY).build();
			sequenceNumberRepository.save(caregiverSequence);
		}
		

	}

	@Override
	public String getNextSequenceNumber(SequenceType type) {
		SequenceNumber sequenceNumber = null;
		try {
			sequenceNumber = sequenceNumberRepository.getBySequenceType(type);
			if (sequenceNumber == null) {
				String seq = "1";
				sequenceNumber = SequenceNumber.builder().sequence(seq).sequenceType(type).build();
				sequenceNumberRepository.save(sequenceNumber).getSequence();
				return seq;
			} else {
				// Increment and update the sequence
				Long nextSequence = Long.parseLong(sequenceNumber.getSequence()) + Integer.valueOf(1);
				sequenceNumber.setSequence(nextSequence.toString());
				sequenceNumberRepository.save(sequenceNumber);
				return nextSequence.toString();
			}

		} catch (EntityNotFoundException ex) {
			sequenceNumber = SequenceNumber.builder().sequence("1").sequenceType(type).build();
			return sequenceNumberRepository.save(sequenceNumber).getSequence();
		}

	}

	@Override
	public String findNextCompanyCode() {
		String nextSequence = this.getNextSequenceNumber(SequenceType.COMPANY);
		return "COMP-" + StringUtils.leftPad(nextSequence.toString(), 3, "0");
	}

	

}
