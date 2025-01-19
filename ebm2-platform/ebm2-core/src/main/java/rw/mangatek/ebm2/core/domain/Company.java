package rw.mangatek.ebm2.core.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import rw.mangatek.ebm2.core.enns.CompanyStatus;


@Entity
@Table(name = "company")
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Company extends AbstractEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 530062113789110936L;
	@Column(name = "code")
	private String code;
	@Column(name = "name")
	private String name;
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private CompanyStatus status;
	@Column(name = "ip_address")
	private String ipAddress;
	@Column(name = "sync_time")
	private LocalDateTime  syncTime;
	@Column(name = "anydesk")
	private String anydesk;
	@Column(name = "anydesk_pwd")
	private String anydeskPwd;
	@Column(name = "previous_payment_date")
	private LocalDate  previousPaymentDate;
	@Column(name = "next_payment_date")
	private LocalDate  nexPaymentDate;
	@Column(name = "cis_version")
	private String cisVersion;
	@Column(name = "cis_name")
	private String cisName;
	@Column(name = "vsdc_version")
	private String vsdcVersion;
	@Column(name = "vsdc_name")
	private String vsdcName;
	private String tinNumber;
	
}
