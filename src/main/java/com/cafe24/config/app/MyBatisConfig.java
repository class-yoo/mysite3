package com.cafe24.config.app;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

//	<!-- MyBatis SqlSessionFactoryBean --> 
//	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean"> 
//		<property name="dataSource" ref="dataSource" /> 
//		<property name="configLocation" value="classpath:mybatis/configuration.xml" /> 
//	</bean>
	
	@Bean
	public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean 
			= new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:com/cafe24/config/app/mybatis/configuration.xml"));

		return sqlSessionFactoryBean.getObject();
	}
	
//	<!-- MyBatis SqlSessionTemplate --> 
//	<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
//		<constructor-arg index="0" ref="sqlSessionFactory" />
//	</bean>
	
	@Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
}
