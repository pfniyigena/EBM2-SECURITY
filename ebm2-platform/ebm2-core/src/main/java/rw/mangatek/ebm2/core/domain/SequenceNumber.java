package rw.mangatek.ebm2.core.domain;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import rw.mangatek.ebm2.core.enns.SequenceType;


@Entity
@Table(name = "sequence_number")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class SequenceNumber extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1981680041287142198L;
	@Column(name = "sequence")
	private String sequence;
	@Column(name = "sequenceType", unique = true)
	private rw.mangatek.ebm2.core.enns.SequenceType sequenceType;

	public SequenceNumber() {

	}

	public SequenceNumber(String sequence, SequenceType sequenceType) {
		this.sequence = sequence;
		this.sequenceType = sequenceType;
	}

}
