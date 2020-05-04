package ImageHoster.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

//This class is used to create beans that are required for the accessing the database and manage the transactions on the database thru EntityManagerFactory bean.
//This file is used to configure these beans and this is the information used by the application to configure the datasource
@Configuration
public class JpaConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setPersistenceXmlLocation("classpath:META-INF/persistence.xml");
        emfb.afterPropertiesSet();
        return emfb.getObject();
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/imageHoster");
        ds.setUsername("postgres");
        ds.setPassword("Kaizen#2020");
        return ds;
    }
}
