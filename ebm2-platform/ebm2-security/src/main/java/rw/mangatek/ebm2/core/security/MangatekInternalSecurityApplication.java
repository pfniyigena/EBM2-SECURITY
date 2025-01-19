package rw.mangatek.ebm2.core.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = "rw")
public class MangatekInternalSecurityApplication extends SpringBootServletInitializer  {
	public static void main(String[] args) {
		SpringApplication.run(MangatekInternalSecurityApplication.class);
	}
	/**
	 *
	 */
	@Override

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		return application.sources(MangatekInternalSecurityApplication.class);

	}
}
