package com.active4j.hr.core.config.properties;

import java.sql.SQLException;

import com.alibaba.druid.pool.DruidDataSource;

import lombok.Data;


/**
 * 数据库连接，连接池相关属性配置
 * @author teli_
 *
 */
@Data
public class DataSourceProperties {

	private String url;

	private String username;

	private String password;

	private String driverClassName;

	private Integer initialSize = 30;

	private Integer minIdle = 10;

	private Integer maxActive = 200;

	private Integer maxWait = 60000;

	private Integer timeBetweenEvictionRunsMillis = 60000;

	private Integer minEvictableIdleTimeMillis = 300000;

	private String validationQuery = "SELECT 1";

	private Boolean testWhileIdle = true;

	private Boolean testOnBorrow = false;

	private Boolean testOnReturn = false;

	private Boolean poolPreparedStatements = true;

	private Integer maxPoolPreparedStatementPerConnectionSize = 20;

	private String filters = "stat";

	/**
	 * 初始化dataSource
	 * 
	 * @param dataSource
	 */
	public void initTo(DruidDataSource dataSource) {

		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);

		dataSource.setDriverClassName(driverClassName);
		dataSource.setInitialSize(initialSize); // 定义初始连接数
		dataSource.setMinIdle(minIdle); // 最小空闲
		dataSource.setMaxActive(maxActive); // 定义最大连接数
		dataSource.setMaxWait(maxWait); // 最长等待时间

		// 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
		dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

		// 配置一个连接在池中最小生存的时间，单位是毫秒
		dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		dataSource.setValidationQuery(validationQuery);
		dataSource.setTestWhileIdle(testWhileIdle);
		dataSource.setTestOnBorrow(testOnBorrow);
		dataSource.setTestOnReturn(testOnReturn);

		// 打开PSCache，并且指定每个连接上PSCache的大小
		dataSource.setPoolPreparedStatements(poolPreparedStatements);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

		try {
			dataSource.setFilters(filters);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
