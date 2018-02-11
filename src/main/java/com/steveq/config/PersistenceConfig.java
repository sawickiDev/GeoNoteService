package com.steveq.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:persistence.properties"})
@EnableJpaRepositories("com.steveq.app.persistence")
public class PersistenceConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource comboPooledDataSource() throws Exception{

        final ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass(environment.getProperty("jdbc.driverClassName"));
        cpds.setJdbcUrl(environment.getProperty("jdbc.url"));
        cpds.setUser(environment.getProperty("jdbc.user"));
        cpds.setPassword(environment.getProperty("jdbc.pass"));

        return cpds;
    }

    private final Properties hibernateProperties() {

        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
        hibernateProperties.setProperty("hibernate.format_sql", environment.getProperty("hibernate.format_sql"));
        hibernateProperties.setProperty("hibernate.time_zone", environment.getProperty("hibernate.time_zone"));

        return hibernateProperties;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() throws Exception{

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean sessionFactory = new LocalContainerEntityManagerFactoryBean();
        sessionFactory.setDataSource(comboPooledDataSource());
        sessionFactory.setPackagesToScan("com.steveq.app.persistence.model");
        sessionFactory.setJpaVendorAdapter(vendorAdapter);
        sessionFactory.setJpaProperties(hibernateProperties());


        return sessionFactory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception{

        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory());

        return transactionManager;
    }
}
