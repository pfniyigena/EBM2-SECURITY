/** Copyright Mangatek Ltd to present
All rights reserved.
*/
package rw.mangatek.ebm2.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import rw.mangatek.ebm2.core.service.ISequenceNumberService;

/**
 * @author pierre.niyigena
 *
 */
@Component
@Slf4j
public class OnSecurityStartUp {

	private static String OS = System.getProperty("os.name").toLowerCase();

 
	
	@Autowired
	private ISequenceNumberService sequenceNumberService;
	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {

		log.info(String.format("----------SECURITY  STARTING.......on :%s" , OS));
		sequenceNumberService.initializaSequenceNumbers();
		
		

	}

	

}
