package rw.mangatek.ebm2.core.ui.dto;

import rw.mangatek.ebm2.core.domain.EbmUser;

public class UserDto {

	private String id;

	private String registeredTin;

	private String branchId;

	private String userId;

	private String username;

	private String password;

	private String userAddress;

	private String userPhone;

	private String authorityCode;

	private String useYn;

	private boolean sentServer;

	public UserDto() {

	}

	public UserDto(String id, String registeredTin, String branchId, String userId, String username, String password,
			String userAddress, String userPhone, String authorityCode, String useYn, boolean sentServer) {
		this.id = id;
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

	public static UserDto fromUserModel(EbmUser user) {

		UserDto dto = new UserDto();
		dto.setAuthorityCode(user.getAuthorityCode());
		dto.setBranchId(user.getBranchId());
		dto.setId(user.getId());
		dto.setPassword(user.getPassword());
		dto.setRegisteredTin(user.getRegisteredTin());
		dto.setSentServer(user.isSentServer());
		dto.setUserAddress(user.getUserAddress());
		dto.setUserId(user.getUserId());
		dto.setUsername(user.getUsername());
		dto.setUserPhone(user.getUserPhone());
		dto.setUseYn(user.getUseYn());
		return dto;
	}

	public static EbmUser fromUserDto(UserDto dto) {
		EbmUser user = new EbmUser();
		user.setAuthorityCode(dto.getAuthorityCode());
		user.setBranchId(dto.getBranchId());
		user.setId(dto.getId());
		user.setPassword(dto.getPassword());
		user.setRegisteredTin(dto.getRegisteredTin());
		user.setSentServer(dto.isSentServer());
		user.setUserAddress(dto.getUserAddress());
		user.setUserId(dto.getUserId());
		user.setUsername(dto.getUsername());
		user.setUserPhone(dto.getUserPhone());
		user.setUseYn(dto.getUseYn());
		return user;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the registeredTin
	 */
	public String getRegisteredTin() {
		return registeredTin;
	}

	/**
	 * @param registeredTin the registeredTin to set
	 */
	public void setRegisteredTin(String registeredTin) {
		this.registeredTin = registeredTin;
	}

	/**
	 * @return the branchId
	 */
	public String getBranchId() {
		return branchId;
	}

	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the userAddress
	 */
	public String getUserAddress() {
		return userAddress;
	}

	/**
	 * @param userAddress the userAddress to set
	 */
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	/**
	 * @return the userPhone
	 */
	public String getUserPhone() {
		return userPhone;
	}

	/**
	 * @param userPhone the userPhone to set
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	/**
	 * @return the authorityCode
	 */
	public String getAuthorityCode() {
		return authorityCode;
	}

	/**
	 * @param authorityCode the authorityCode to set
	 */
	public void setAuthorityCode(String authorityCode) {
		this.authorityCode = authorityCode;
	}

	/**
	 * @return the useYn
	 */
	public String getUseYn() {
		return useYn;
	}

	/**
	 * @param useYn the useYn to set
	 */
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	/**
	 * @return the sentServer
	 */
	public boolean isSentServer() {
		return sentServer;
	}

	/**
	 * @param sentServer the sentServer to set
	 */
	public void setSentServer(boolean sentServer) {
		this.sentServer = sentServer;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", registeredTin=" + registeredTin + ", branchId=" + branchId + ", userId="
				+ userId + ", username=" + username + ", password=" + password + ", userAddress=" + userAddress
				+ ", userPhone=" + userPhone + ", authorityCode=" + authorityCode + ", useYn=" + useYn + ", sentServer="
				+ sentServer + "]";
	}

}
