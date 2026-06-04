/*************************************************************************************************
 * Copyright (c) Mangatek Ltd(Owner) 2020 to Present. All Rights Reserved.
 * No part of this file may be reproduced or distributed in any form or by any means, 
 * or stored in a database or retrieval system, without the prior written permission of the owner.
 *************************************************************************************************/
package rw.mangatek.ebm2.core.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RraVscDataResponseDto {
	@JsonProperty("rcptNo")
	private int rcptNo;
	@JsonProperty("intrlData")
	private String intrlData;
	@JsonProperty("rcptSign")
	private String rcptSign;
	@JsonProperty("totRcptNo")
	private int totRcptNo;
	@JsonProperty("vsdcRcptPbctDate")
	private String vsdcRcptPbctDate;
	@JsonProperty("sdcId")
	private String sdcId;
	@JsonProperty("mrcNo")
	private String mrcNo;
}
