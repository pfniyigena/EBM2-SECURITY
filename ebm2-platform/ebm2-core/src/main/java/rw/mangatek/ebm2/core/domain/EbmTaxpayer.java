package rw.mangatek.ebm2.core.domain;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "ebm_taxpayer")
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = true)
public class EbmTaxpayer extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3096436149635316240L;
	@Column(name = "tin", unique = true)
	private String taxpayerTin;
	@Column(name = "taxpayer_name")
	private String taxpayerName;
	@Column(name = "taxpayer_activity")
	private String taxpayerActivity;

	public EbmTaxpayer() {

	}

	public EbmTaxpayer(String taxpayerTin, String taxpayerName, String taxpayerActivity) {
		this.taxpayerTin = taxpayerTin;
		this.taxpayerName = taxpayerName;
		this.taxpayerActivity = taxpayerActivity;
	}

}
