package rw.mangatek.ebm2.core.domain;

import jakarta.persistence.*;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "ebm_user")
@Data
@ToString
@Builder
@EqualsAndHashCode(callSuper = true)
public class EbmUser extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3096436149635316240L;

	@Column(name = "registered_tin")
	private String registeredTin;
	@Column(name = "branch_id")
	private String branchId;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "username")
	private String username;
	@Column(name = "password")
	private String password;
	@Column(name = "user_address")
	private String userAddress;
	@Column(name = "user_phone")
	private String userPhone;
	@Column(name = "authority_code")
	private String authorityCode;
	@Column(name = "use_yn")
	private String useYn;
	@Column(name = "sent_server")
	private boolean sentServer;

	public EbmUser() {

	}

	public EbmUser(String registeredTin, String branchId,   String userId, String username,
			String password, String userAddress,String userPhone, String authorityCode, String useYn, boolean sentServer) {
		this.registeredTin = registeredTin;
		this.branchId = branchId;
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userAddress = userAddress;
		this.userPhone = userPhone;
		this.authorityCode = authorityCode;		 
		this.useYn = useYn;
		this.sentServer = sentServer;

	}

}
