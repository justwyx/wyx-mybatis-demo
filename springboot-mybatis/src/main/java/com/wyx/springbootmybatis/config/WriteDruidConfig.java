package com.wyx.springbootmybatis.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class WriteDruidConfig extends DruidConfig{
    @Value("${mybatis.write.mapper-locations:classpath:mapper/write/*.xml}")
    private String mapperResources;
    @Value("${mybatis.write.config-location:mybatis/mybatis-config.xml}")
    private String mybatisConfig;
    @Bean(name = "writeDataSourceProperties")
    @ConfigurationProperties(prefix="spring.datasource.write")
    @Primary
    public DataSourceProperties writeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name ="writeDataSource")
    @Primary
    public DataSource writeDataSource(@Qualifier("writeDataSourceProperties") DataSourceProperties writeDataSourceProperties){
        return writeDataSourceProperties.initializeDataSourceBuilder().build();
    }
    @Bean(name = "writeSqlSessionFactory")
    @Primary
    public SqlSessionFactory writeSqlSessionFactory(@Qualifier("writeDataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigLocation(new ClassPathResource(mybatisConfig));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperResources));
        setIntercept(bean,applicationContext);
        return bean.getObject();
    }

    @Bean(name="writeTransactionManager")
    @Primary
    public DataSourceTransactionManager writeTransactionManager(@Qualifier("writeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "writeSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate writeSqlSessionTemplate(@Qualifier("writeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }




}
