/*************************************************************************************************
 * Copyright (c) Mangatek Ltd(Owner) 2021 to Present. All Rights Reserved.
 * No part of this file may be reproduced or distributed in any form or by any means, 
 * or stored in a database or retrieval system, without the prior written permission of the owner.
 *************************************************************************************************/
package rw.mangatek.ebm2.core.security.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


/**
 * @author PIERRE FOURIER NIYIGENA
 * @version 1.0
 */
@Slf4j
@Controller
public class IndexController {
	
	@GetMapping({"/"})
    public String welcome(Model model) {
		log.debug("--------------Calling welcome");
        return "index";
    }
	

	@RequestMapping(value = "/logout")
	public String logout(Model model) {
		return "login";
	}
	

}
