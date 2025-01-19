package rw.mangatek.ebm2.core.ui.config;

import java.util.Properties;

import jakarta.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author pierre.niyigena
 * 
 */
@Configuration
public class CoreUiDataSourceConfig {

	@Autowired
	private Environment env;
	
	@Bean
    public DataSourceProperties coreDataSourceProperties() {
        return new DataSourceProperties();
    }
    
    @Bean
    public DataSource coreDataSource() {
        DataSourceProperties primaryDataSourceProperties = coreDataSourceProperties();
		return DataSourceBuilder.create()
        			.driverClassName(primaryDataSourceProperties.getDriverClassName())
        			.url(primaryDataSourceProperties.getUrl())
        			.username(primaryDataSourceProperties.getUsername())
        			.password(primaryDataSourceProperties.getPassword())
        			.build();
    }
    
    @Bean
    public PlatformTransactionManager coreTransactionManager()
    {
        EntityManagerFactory factory = coreEntityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean coreEntityManagerFactory()
    {
    	LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(coreDataSource());
        factory.setPackagesToScan(new String[]{"rw.mangatek.ebm2.core.domain"});
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
        jpaProperties.put("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        jpaProperties.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        factory.setJpaProperties(jpaProperties);
        
        return factory;
        
    }
   
}
