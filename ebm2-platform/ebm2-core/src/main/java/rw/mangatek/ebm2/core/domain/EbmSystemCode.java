package rw.mangatek.ebm2.core.domain;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "ebm_system_code")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class EbmSystemCode extends AbstractEntity {
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
	@ManyToOne
	@JoinColumn(name = "type_id", unique = false, nullable = true, insertable = true, updatable = true)
	private EbmCodeType type;
	@Transient
	private String typeCode;
	@Column(name = "user_dfn_cd1")
	private String userDfnCd1;
	@Column(name = "user_dfn_cd2")
	private String userDfnCd2;
	@Column(name = "user_dfn_cd3")
	private String userDfnCd3;
	@Column(name = "use_yn")
	private String useYn;

	public EbmSystemCode() {
	}

	public EbmSystemCode(String code, String name, String description, EbmCodeType type, String typeCode,
			String userDfnCd1, String userDfnCd2, String userDfnCd3, String useYn) {
		this.name = name;
		this.code = code;
		this.description = description;
		this.type = type;
		this.typeCode = typeCode;
		this.userDfnCd1 = userDfnCd1;
		this.userDfnCd2 = userDfnCd2;
		this.userDfnCd3 = userDfnCd3;
		this.useYn = useYn;

	}

}
