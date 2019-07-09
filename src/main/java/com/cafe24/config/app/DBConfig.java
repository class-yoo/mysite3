package com.cafe24.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:com/cafe24/config/app/properties/jdbc.properties")
public class DBConfig {

//	Connection Pool DataSource-->
//	<bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
//		<property name="driverClassName" value="org.mariadb.jdbc.Driver" />
//		<property name="url" value="jdbc:mariadb://192.168.1.221:3307/webdb" />
//		<property name="username" value="webdb" />
//		<property name="password" value="webdb" />
//	</bean> 설정을 Xml이 아닌 java로 해줌
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource basicDataSource() {
//		// <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
//		BasicDataSource basicDataSource = new BasicDataSource();
//		// <property name="driverClassName" value="org.mariadb.jdbc.Driver" />
//		basicDataSource.setDriverClassName("org.mariadb.jdbc.Driver"); 
//		// <property name="url" value="jdbc:mariadb://192.168.1.221:3307/webdb" />
//		basicDataSource.setUrl("jdbc:mariadb://192.168.1.221:3307/webdb");
//		// <property name="username" value="webdb" />
//		basicDataSource.setUsername("webdb");
//		// <property name="password" value="webdb" />
//		basicDataSource.setPassword("webdb");
//		basicDataSource.setInitialSize(10); // 기본 커넥션 10개를 생성해두고 대기
//		basicDataSource.setMaxActive(100); // 100개까지 커넥션풀이 지원해주는데 101번째일경우 자원이 반납될때까지 기다려야함

		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
		basicDataSource.setUrl(env.getProperty("jdbc.url"));
		basicDataSource.setUsername(env.getProperty("jdbc.username"));
		basicDataSource.setPassword(env.getProperty("jdbc.password"));
		basicDataSource.setInitialSize(10);
		basicDataSource.setMaxActive(20);
		
		return basicDataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager(DataSource dataSource) {

		return new DataSourceTransactionManager(dataSource);

	}
}
