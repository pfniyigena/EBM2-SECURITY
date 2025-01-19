package rw.mangatek.ebm2.core.domain;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ebm_item_classification")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class EbmItemClassification extends AbstractEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 530062113789110936L;
	@Column(name = "code")
	private String code;
	@Column(name = "name")
	private String name;
	@Column(name = "classification_level")
	private int classificationLevel;
	@Column(name = "tax_label")
	private String taxLabel;
	@Column(name = "major_target")
	private String majorTarget;
	@Column(name = "use_yn")
	private String useYn;

	public EbmItemClassification() {

	}

	public EbmItemClassification(String code, String name, int classificationLevel, String taxLabel, String majorTarget,
			String useYn) {
		this.code = code;
		this.name = name;
		this.classificationLevel = classificationLevel;
		this.taxLabel = taxLabel;
		this.majorTarget = majorTarget;
		this.useYn = useYn;
	}

}
