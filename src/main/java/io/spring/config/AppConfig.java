package io.spring.config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;

import javax.sql.DataSource;
import java.util.Properties;


@PropertySource({"classpath:db/application.properties"})
@ComponentScan({"io.spring"})
@EnableTransactionManagement
@Configuration
@EnableWebMvc
@Profile("Web")
public class AppConfig {

    @Value("${hibernate.dialect}")
    private String dialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Value("${hibernate.packagesToScan}")
    private String packagesToScan;

    @Bean
    public LocalSessionFactoryBean sessionFactory(@Qualifier("h2") DataSource dataSource) {

        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan(packagesToScan());
        sessionFactory.setHibernateProperties(additionalProperties());

        return sessionFactory;
    }

    String packagesToScan() {
        return packagesToScan;
    }

    Properties additionalProperties() {

        Properties props = new Properties();
        props.setProperty("hibernate.dialect", dialect);
        props.setProperty("hibernate.show_sql", showSql);
        props.setProperty("hibernate.hbm2ddl.auto", hbm2ddlAuto);

        return props;
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }
}
