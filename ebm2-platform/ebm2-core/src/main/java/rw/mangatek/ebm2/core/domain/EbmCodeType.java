package rw.mangatek.ebm2.core.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ebm_code_type")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class EbmCodeType extends AbstractEntity {
	/**
	* 
	*/
	private static final long serialVersionUID = 530062113789110936L;
	@Column(name = "code", unique = true)
	private String code;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "user_dfn_cd1")
	private String userDfnCd1;
	@Column(name = "user_dfn_cd2")
	private String userDfnCd2;
	@Column(name = "user_dfn_cd3")
	private String userDfnCd3;
	@Column(name = "use_yn")
	private String useYn;
	public EbmCodeType() {

	}

	public EbmCodeType(String code, String name, String description,String userDfnCd1, String userDfnCd2, String userDfnCd3, String useYn) {
		this.code = code;
		this.name = name;
		this.description = description;
		this.userDfnCd1 = userDfnCd1;
		this.userDfnCd2 = userDfnCd2;
		this.userDfnCd3 = userDfnCd3;
		this.useYn = useYn;
	}

}
