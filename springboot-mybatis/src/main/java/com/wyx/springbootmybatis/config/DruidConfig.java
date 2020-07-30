package com.wyx.springbootmybatis.config;

import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.ApplicationContext;

import java.util.Collection;
import java.util.Map;

public abstract class DruidConfig {

    public void setIntercept(SqlSessionFactoryBean bean, ApplicationContext applicationContext){
        Map<String, Interceptor> interceptorMap = applicationContext.getBeansOfType(Interceptor.class);
        if(interceptorMap!=null){
            Collection<Interceptor> collection = interceptorMap.values();
            if(collection!=null&&collection.size()>0){
                bean.setPlugins(collection.toArray(new Interceptor[collection.size()]));
            }
        }
    }
}
