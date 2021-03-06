package com.ese.config.spring;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.SessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySources(value = {@PropertySource("classpath:grimp/properties/application.properties")}) 
@MapperScan({"com.ese.**.service.mapper"})
public class DataSourceConfig {

	@Autowired
	private Environment env;
	
	@Value("${type-db}")
	private String initDateBaseType;

	/**
	 * define the connection information for using datasource
	 * @category method
	 * @author ESE-MILLER
	 * @since 2015.02.05
	 * @return
	 */
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		return dataSource;
	}
	
	/**
	 * Injection mybatis configuration
	 * point the mybatis mapper location
	 * @author ESE-MILLER
	 * @since 2015.02.05
	 * @category method
	 * @return SqlSessionFactory
	 * @throws Exception
	 */
	@Bean
	public SqlSessionFactoryBean sqlSessionFatory(DataSource dataSource) throws Exception{
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:grimp/**/mapper/mapper_"+initDateBaseType.toLowerCase()+"/*.xml"));
		return sqlSessionFactory;
	}
	
	/**
	 * sqlSessionFactory set to sqlSession mybatis
	 * @param sqlSessionFatory
	 * @return SqlSession
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFatory) {
		return new SqlSessionTemplate(sqlSessionFatory);
	}
	
	/**
	 * DataSource set to txManager
	 * @param datasource
	 * @return PlatformTracsantionManager
	 */
    @Bean(name="jpaTx")
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    
    /**
     * hibernate 
     * sesisonFactory set to LocalSessionFactoryBean
     * @return LocalSessionFactoryBean
     */
	@Bean
	public LocalSessionFactoryBean sessionFactory()  {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] { "com.ese.domain" });
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
		   
	/**
	 * hibernate
	 * SessionFactory Set to hibernateTransactionManager
	 * @param sessionFactory
	 * @return HibernateTransactionManager
	 */
	@Bean(name="hibTx")
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);
		txManager.setRollbackOnCommitFailure(true);
		return txManager;
	}
		 	
	/**
	 * hibernate setting 
	 * @return
	 */
	@SuppressWarnings("serial")
	Properties hibernateProperties() {
		return new Properties() {
			{
				setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
				setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
				setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
				setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
			}
		};
	}
}	
