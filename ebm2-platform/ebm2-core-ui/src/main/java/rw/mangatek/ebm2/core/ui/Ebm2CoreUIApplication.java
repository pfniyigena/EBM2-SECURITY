package rw.mangatek.ebm2.core.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = { 
		"rw.mangatek.ebm2"}, exclude = {
				DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
				DataSourceTransactionManagerAutoConfiguration.class })
@EnableJpaRepositories(basePackages = {
		"rw.mangatek.ebm2.core.repository" }, entityManagerFactoryRef = "coreEntityManagerFactory", transactionManagerRef = "coreTransactionManager")
@EntityScan("rw.mangatek.ebm2.core.domain")
public class Ebm2CoreUIApplication extends SpringBootServletInitializer  {
	public static void main(String[] args) {
		SpringApplication.run(Ebm2CoreUIApplication.class);
	}
	/**
	 *
	 */
	@Override

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

		return application.sources(Ebm2CoreUIApplication.class);

	}
}
