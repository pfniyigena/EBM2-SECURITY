package rw.mangatek.ebm2.core.domain;

import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ebm_notice")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class EbmNotice extends AbstractEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 530062113789110936L;
	@Column(name = "title", unique = true)
	private String title;
	@Column(name = "registration_number")
	private String registrationNumber;
	@Column(name = "registration_date")
	private LocalDate registrationDate;
	@Column(name = "notice_number")
	private int noticeNumber;
	@Column(name = "notice_url")
	private String noticeUrl;
	@Column(name = "notice_count")
	private String noticeCount;

	public EbmNotice() {

	}

	public EbmNotice(String title, String registrationNumber, LocalDate registrationDate, int noticeNumber,
			String noticeUrl, String noticeCount ) {
		this.title = title;
		this.registrationNumber = registrationNumber;
		this.registrationDate = registrationDate;
		this.noticeNumber = noticeNumber;
		this.noticeUrl = noticeUrl;
		this.noticeCount = noticeCount;

	}

}
