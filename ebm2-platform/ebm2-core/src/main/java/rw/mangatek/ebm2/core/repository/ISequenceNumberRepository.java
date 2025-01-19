package rw.mangatek.ebm2.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import rw.mangatek.ebm2.core.domain.SequenceNumber;
import rw.mangatek.ebm2.core.enns.SequenceType;



public interface ISequenceNumberRepository extends JpaRepository<SequenceNumber, String> {
	
public 	SequenceNumber  getBySequenceType(SequenceType sequenceType);
}
