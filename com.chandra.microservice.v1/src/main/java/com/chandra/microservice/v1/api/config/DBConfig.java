package com.chandra.microservice.v1.api.config;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * This class is to create database connection using hibernate and establish Tomcat connection pooling.
 * @author Chandra
 *
 */
@Configuration
@EntityScan("com.chandra.microservice.v1.api.jpa")
@EnableTransactionManagement
@PropertySources({ @org.springframework.context.annotation.PropertySource({ "${env.properties.path}" }),
		@org.springframework.context.annotation.PropertySource({ "${secret.properties.path}" }) })
public class DBConfig {

	private final Logger logger = LoggerFactory.getLogger(DBConfig.class);

	@Autowired
	private Environment env;

	/**
	 * This method is to read the database properties from application.properties file. 
	 */
	@PostConstruct
	public void init() {
		this.logger.info("DB2 Keystore [{}]", this.env.getProperty("keystore.loc"));
		this.logger.info("DB2 Truststore [{}]", this.env.getProperty("truststore.loc"));
		this.logger.info("DB2 DatasourceUrl [{}]", this.env.getProperty("db2.connection.url"));
		this.logger.info("DB2 Drive class [{}] ", this.env.getProperty("db2.driver.classname"));
		this.logger.info("DB2 Username [{}] ", this.env.getProperty("db2.username"));
		this.logger.info("DB2 Schema [{}]", this.env.getProperty("db2.schema"));

		System.setProperty("javax.net.ssl.debug", "all");
		System.setProperty("sslConnection", "true");
		System.setProperty("javax.net.ssl.keyStore", this.env.getProperty("keystore.loc"));
		System.setProperty("javax.net.ssl.keyStoreType", "JKS");
		System.setProperty("javax.net.ssl.keyStorePassword", this.env.getProperty("keystore.pd"));
		System.setProperty("javax.net.ssl.trustStore", this.env.getProperty("truststore.loc"));
		System.setProperty("javax.net.ssl.trustStoreType", "JKS");
		System.setProperty("javax.net.ssl.trustStorePassword", this.env.getProperty("truststore.pd"));
	}

	/**
	 * This method is to create the datasource for given db properties in application.properties
	 * @return DataSource
	 */
	@Bean
	public javax.sql.DataSource dataSource() {
		PoolProperties poolProperties = new PoolProperties();

		poolProperties.setPassword(this.env.getProperty("db2.password"));
		poolProperties.setUsername(this.env.getProperty("db2.username"));
		poolProperties.setUrl(this.env.getProperty("db2.connection.url"));
		poolProperties.setDriverClassName(this.env.getProperty("db2.driver.classname"));

		this.logger.debug("Max Idle [{}]", this.env.getProperty("connection.pool.maxIdle"));
		poolProperties.setMaxIdle(Integer.valueOf(this.env.getProperty("connection.pool.maxIdle")).intValue());
		poolProperties.setMinIdle(Integer.valueOf(this.env.getProperty("connection.pool.minIdle")).intValue());
		poolProperties
				.setTestOnBorrow(Boolean.valueOf(this.env.getProperty("connection.pool.testOnBorrow")).booleanValue());
		poolProperties.setRemoveAbandoned(
				Boolean.valueOf(this.env.getProperty("connection.pool.removeAbandoned")).booleanValue());
		poolProperties
				.setLogAbandoned(Boolean.valueOf(this.env.getProperty("connection.pool.logAbandoned")).booleanValue());
		poolProperties.setDefaultReadOnly(Boolean.valueOf(this.env.getProperty("connection.pool.defaultReadOnly")));
		poolProperties.setValidationQuery(this.env.getProperty("connection.pool.validationQuery"));
		poolProperties.setValidationQueryTimeout(
				Integer.valueOf(this.env.getProperty("connection.pool.validationQueryTimeout")).intValue());
		poolProperties.setValidationInterval(
				Integer.valueOf(this.env.getProperty("connection.pool.validationInterval")).intValue());
		return new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
	}

	/**
	 * This method to create hibernate sessionfactory using spring jpa component.
	 * @return LocalSessionFactoryBean
	 */
	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
		factoryBean.setDataSource(dataSource());
		factoryBean.setPackagesToScan(new String[] { "com.chandra.microservice.v1.api.jpa" });
		Properties props = new Properties();
		props.put("hibernate.show_sql", this.env.getProperty("spring.jpa.show-sql"));
		props.put("hibernate.default_schema", this.env.getProperty("db2.schema"));
		props.put("hibernate.dialect", this.env.getProperty("spring.jpa.properties.hibernate.dialect"));

		props.put("hibernate.enable_lazy_load_no_trans",
				this.env.getProperty("spring.jpa.properties.hibernate.enable_lazy_load_no_trans"));
		props.put("hibernate.generate_statistics",
				this.env.getProperty("spring.jpa.properties.hibernate.generate_statistics"));

		props.put("hibernate.current_session_context_class",
				this.env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));

		factoryBean.setHibernateProperties(props);
		return factoryBean;
	}

	/**
	 * This method is to get hibernate transaction manager from the sessionfactory.
	 * 
	 * @return HibernateTransactionManager
	 */
	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}
}
