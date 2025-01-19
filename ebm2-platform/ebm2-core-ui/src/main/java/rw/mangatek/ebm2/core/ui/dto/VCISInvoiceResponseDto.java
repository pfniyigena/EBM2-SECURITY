/*************************************************************************************************
 * Copyright (c) Mangatek Ltd(Owner) 2020 to Present. All Rights Reserved.
 * No part of this file may be reproduced or distributed in any form or by any means, 
 * or stored in a database or retrieval system, without the prior written permission of the owner.
 *************************************************************************************************/
package rw.mangatek.ebm2.core.ui.dto;

import javax.xml.bind.annotation.XmlAttribute;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VCISInvoiceResponseDto {
	@XmlAttribute(required = true)
	private String sdcData;

	public VCISInvoiceResponseDto() {
	}

	public VCISInvoiceResponseDto(String sdcData) {
		this.sdcData = sdcData;
	}

	public VCISInvoiceResponseDto(String sdcId, Integer receiptType, Integer totalReceipts, String invoiceType,
			String transactionType, String sdcDate, String signature, String internalData) {

		this.sdcData = sdcId + "," + receiptType + "," + totalReceipts + "," + invoiceType + transactionType + ","
				+ sdcDate + "," + signature + "," + internalData;

	}

}
