package com.Property.Config;


import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@ImportResource(locations={"classpath:shiro-config.xml"})
public class ShiroConfig
{
//	@Bean
//	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager)
//	{
//		ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
//		shiroFilterFactoryBean.setSecurityManager(securityManager);
//		Map<String,String> filterChainDefinitionMap=new LinkedHashMap<>();
//		shiroFilterFactoryBean.setLoginUrl("/login");
//		shiroFilterFactoryBean.setSuccessUrl("/ticket/list");
//		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//
//		filterChainDefinitionMap.put("/INSPINIA/**","anon");
//		filterChainDefinitionMap.put("/jsencrypt/jsencrypt.min.js","anon");
//		filterChainDefinitionMap.put("/favicon.ico","anon");
//		filterChainDefinitionMap.put("../public/**","anon");
//		filterChainDefinitionMap.put("/display","anon");
////		filterChainDefinitionMap.put("/logout","logout");
//		filterChainDefinitionMap.put("/**","authc");
//
//		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//		return shiroFilterFactoryBean;
//	}
//
//
//	@Bean
//	public HashedCredentialsMatcher hashedCredentialsMatcher(){
//		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//		hashedCredentialsMatcher.setHashAlgorithmName("SHA-512");
//		hashedCredentialsMatcher.setHashIterations(5);
//		return hashedCredentialsMatcher;
//	}
//
//	@Bean
//	public ShiroRealmConfig myShiroRealm(){
//		ShiroRealmConfig myShiroRealm = new ShiroRealmConfig();
//		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//		return myShiroRealm;
//	}
//
//
//	@Bean
//	public SecurityManager securityManager(){
//		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
//		securityManager.setRealm(myShiroRealm());
//		return securityManager;
//	}
//
//
//	@Bean
//	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
//		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//		return authorizationAttributeSourceAdvisor;
//	}
//

	@Bean
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}

	@Bean(name="simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver
	createSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
		Properties mappings = new Properties();
		mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
		mappings.setProperty("UnauthorizedException","403");
		r.setExceptionMappings(mappings);  // None by default
		r.setDefaultErrorView("error");    // No default
		r.setExceptionAttribute("ex");     // Default is "exception"
		//r.setWarnLogCategory("example.MvcLogger");     // No default
		return r;
	}

}
