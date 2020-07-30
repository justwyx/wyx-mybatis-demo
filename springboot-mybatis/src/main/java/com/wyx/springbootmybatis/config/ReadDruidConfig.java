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
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ReadDruidConfig extends DruidConfig{
    @Value("${mybatis.read.mapper-locations:classpath:mapper/read/*.xml}")
    private String mapperResources;
    @Value("${mybatis.read.config-location:mybatis/mybatis-config.xml}")
    private String mybatisConfig;

    @Bean(name = "readDataSourceProperties")
    @ConfigurationProperties(prefix="spring.datasource.read")
    public DataSourceProperties readDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name ="readDataSource")
    public DataSource readDataSource(@Qualifier("readDataSourceProperties") DataSourceProperties readDataSourceProperties){
        return readDataSourceProperties.initializeDataSourceBuilder().build();
    }
    @Bean(name = "readSqlSessionFactory")
    public SqlSessionFactory readSqlSessionFactory(@Qualifier("readDataSource") DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setConfigLocation(new ClassPathResource(mybatisConfig));
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperResources));
        setIntercept(bean,applicationContext);
        return bean.getObject();
    }

    @Bean
    public DataSourceTransactionManager readTransactionManager(@Qualifier("readDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "readSqlSessionTemplate")
    public SqlSessionTemplate readSqlSessionTemplate(@Qualifier("readSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }




}
