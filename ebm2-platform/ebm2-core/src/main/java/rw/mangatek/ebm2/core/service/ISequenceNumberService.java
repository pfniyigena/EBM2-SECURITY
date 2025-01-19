package rw.mangatek.ebm2.core.service;

public interface ISequenceNumberService {

	public void initializaSequenceNumbers();

	public String getNextSequenceNumber(rw.mangatek.ebm2.core.enns.SequenceType type);

	public String findNextCompanyCode();

}
