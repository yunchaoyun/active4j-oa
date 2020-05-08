package com.active4j.hr.core.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.active4j.hr.core.config.properties.DataSourceProperties;
import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;


/**
 * 数据库相关配置
 * 		使用Mybatis plus插件: 基于mybatis
 * 		使用druid的连接池
 * @author teli_
 *
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.active4j.**.dao"})
public class DbConfig {
	
	@Autowired
	private DataSourceProperties dataSourceProperties;

	/**
	 * 初始化properties
	 * @return
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}
	
	 /**
     * druid数据库连接池
     */
    @Bean(initMethod = "init", destroyMethod="close")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSourceProperties.initTo(dataSource);
        return dataSource;
    }
    
    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setDialectType(DbType.MYSQL.getDb());
        return paginationInterceptor;
    }

    /**
     * 乐观锁插件
     * @return
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
