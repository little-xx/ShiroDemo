package com.littlexx.shiro.demo.security;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Map;

@Configuration
public class ShiroConfig {



    // 创建Realm对象，需要自定义类
    @Bean
    public MyRealm myRealm() {
        return new MyRealm();
    }

    // DefaultWebSecurityManager

    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(MyRealm myRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(myRealm);

        /*
         * 关闭session
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);

        return manager;
    }

    // ShiroFilterFactoryBean
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultSecurityManager manager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();

        // 添加jwt过滤器
        Map<String, Filter> filterMap = new HashedMap();
        filterMap.put("jwt", new JwtTokenFilter());
        factoryBean.setFilters(filterMap);

        Map<String, String> filterRuleMap = new HashedMap();
        filterRuleMap.put("/**", "jwt");
        filterRuleMap.put("/login", "anon");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);

        factoryBean.setSecurityManager(manager);

        return factoryBean;
    }
}
