package com.springboot.template.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @name: DataSourceOneConfig
 * @desc: 多数据源配置之数据源1
 * @author: gxing
 * @date: 2019-02-15 15:53
 **/
@Configuration
@MapperScan(basePackages = "com.springboot.template.mapper.source1", sqlSessionTemplateRef = "sqlSessionTemplateOne")
public class DataSourceOneConfig {

    /**
     * @desc: 数据源 1
     * @author: gxing
     * @date: 2019/2/15 16:46
     * @param: []
     * @return: javax.sql.DataSource
     **/
    @Bean(name = "dataSourceOne")
    @ConfigurationProperties(prefix = "spring.datasource.source1")
    @Primary
    public DataSource dataSourceOne() {
        //一定要用DruidDataSource类，不然的话druid数据库连接池是不会起作用的
        DruidDataSource dataSourceOne = new DruidDataSource();
        return dataSourceOne;
    }

    /**
     * @desc: SqlSessionFactory 1
     * @author: gxing
     * @date: 2019/2/15 16:52
     * @param: [dataSource]
     * @return: org.apache.ibatis.session.SqlSessionFactory
     **/
    @Bean(name = "sqlSessionFactoryOne")
    @Primary
    public SqlSessionFactory sqlSessionFactoryOne(@Qualifier("dataSourceOne") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * @desc: SqlSessionTemplate
     * @author: gxing
     * @date: 2019/2/15 16:54
     * @param: [sqlSessionFactory]
     * @return: SqlSessionTemplate
     **/
    @Bean(name = "sqlSessionTemplateOne")
    @Primary
    public SqlSessionTemplate sqlSessionTemplateOne(@Qualifier("sqlSessionFactoryOne") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * @desc: 数据源1的事务管理
     * @author: gxing
     * @date: 2019/2/15 16:48
     * @param: [dataSource]
     * @return: org.springframework.jdbc.datasource.DataSourceTransactionManager
     **/
    @Bean(name = "dataSourceTransactionManagerOne")
    @Primary
    public DataSourceTransactionManager dataSourceTransactionManagerOne(@Qualifier("dataSourceOne") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}