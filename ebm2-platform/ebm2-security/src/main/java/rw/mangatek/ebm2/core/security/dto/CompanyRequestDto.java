/*************************************************************************************************
 * Copyright (c) Mangatek Ltd(Owner) 2020 to Present. All Rights Reserved.
 * No part of this file may be reproduced or distributed in any form or by any means, 
 * or stored in a database or retrieval system, without the prior written permission of the owner.
 *************************************************************************************************/
package rw.mangatek.ebm2.core.security.dto;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "companyRequestDto")
public class CompanyRequestDto {

	// Owner
	@XmlAttribute(required = true)
	private String code;
	@XmlAttribute(required = true)
	private String tinNumber;

	public CompanyRequestDto() {
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the tinNumber
	 */
	public String getTinNumber() {
		return tinNumber;
	}

	/**
	 * @param tinNumber the tinNumber to set
	 */
	public void setTinNumber(String tinNumber) {
		this.tinNumber = tinNumber;
	}

}
