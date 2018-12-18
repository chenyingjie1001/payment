package com.firesoon.idaweb.shiro.cas;

import com.firesoon.idaweb.shiro.cas.filter.SkyLogoutFilter;
import com.firesoon.idaweb.shiro.cas.filter.UserFilter;
import com.firesoon.idaweb.shiro.cas.realm.MyShiroCasRealm;
import com.firesoon.utils.PropertiesUtil;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.cas.CasFilter;
import org.apache.shiro.cas.CasSubjectFactory;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author create by yingjie.chen on 2018/11/16.
 * @version 2018/11/16 16:55
 */
@Configuration
public class ShiroCasConfiguration {
    // CasServerUrlPrefix
    public static final String casServerUrlPrefix = PropertiesUtil.properties.getProperty("casServerUrlPrefix");
    // Cas登录页面地址
    public static final String casLoginUrl = casServerUrlPrefix + "/login";
    // Cas登出页面地址
    public static final String casLogoutUrl = casServerUrlPrefix + "/logout";
    // 当前工程对外提供的服务地址
    public static final String shiroServerUrlPrefix = PropertiesUtil.properties.getProperty("shiroServerUrlPrefix");
    // casFilter UrlPattern
    public static final String casFilterUrlPattern = PropertiesUtil.properties.getProperty("casFilterUrlPattern");
    public static final String userFilterUrlPattern = PropertiesUtil.properties.getProperty("userFilterUrlPattern");
    public static final String logoutFilterUrlPattern = PropertiesUtil.properties.getProperty("logoutFilterUrlPattern");

    // 登录地址
    public static final String loginUrl = casLoginUrl + "?service=" + shiroServerUrlPrefix + casFilterUrlPattern;

    //成功地址
    public static final String successUrl = PropertiesUtil.properties.getProperty("successUrl");

    private static boolean casEnabled = true;

    /**
     * 用于实现单点登出功能
     */
    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
        listener.setEnabled(casEnabled);
        listener.setListener(new SingleSignOutHttpSessionListener());
        listener.setOrder(1);
        return listener;
    }

    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new SingleSignOutFilter());
        filterRegistration.setEnabled(casEnabled);
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(3);
        return filterRegistration;
    }

    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager em = new EhCacheManager();
        em.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return em;
    }

    @Bean(name = "myShiroCasRealm")
    public MyShiroCasRealm myShiroCasRealm(EhCacheManager cacheManager) {
        MyShiroCasRealm realm = new MyShiroCasRealm();
        realm.setCasServerUrlPrefix(casServerUrlPrefix);
        realm.setCasService(shiroServerUrlPrefix+casFilterUrlPattern);
        realm.setCacheManager(cacheManager);
        return realm;
    }

    /**
     * 注册DelegatingFilterProxy（Shiro）
     *
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //  该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");
        return filterRegistration;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        daap.setProxyTargetClass(true);
        return daap;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("myShiroCasRealm") MyShiroCasRealm myShiroCasRealm) {
        DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
        dwsm.setRealm(myShiroCasRealm);
//      <!-- 用户授权/认证信息Cache, 采用EhCache 缓存 -->
        dwsm.setCacheManager(getEhCacheManager());
        // 指定 SubjectFactory
        dwsm.setSubjectFactory(new CasSubjectFactory());
        return dwsm;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor aasa = new AuthorizationAttributeSourceAdvisor();
        aasa.setSecurityManager(manager);
        return aasa;
    }

    /**
     * ShiroFilter<br/>
     * 注意这里参数中的 StudentService 和 IScoreDao 只是一个例子，因为我们在这里可以用这样的方式获取到相关访问数据库的对象，
     * 然后读取数据库相关配置，配置到 shiroFilterFactoryBean 的访问规则中。实际项目中，请使用自己的Service来处理业务逻辑。
     *
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(manager);
        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        // 登录成功后要跳转的连接
//        shiroFilterFactoryBean.setSuccessUrl("/user");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        // 添加casFilter到shiroFilter中
        Map<String, Filter> filters = new HashMap<>();
        filters.put("cas", getCasFilter());
        filters.put("user", getUserFilter());
//        filters.put("logout", getSkyLogoutFilter());
        shiroFilterFactoryBean.setFilters(filters);

        loadShiroFilterChain(shiroFilterFactoryBean);
        return shiroFilterFactoryBean;
    }

    /**
     * 加载shiroFilter权限控制规则（从数据库读取然后配置）
     *
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        /////////////////////// 下面这些规则配置最好配置到配置文件中 ///////////////////////
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        //swagger
//        filterChainDefinitionMap.put("/swagger*", "anon");//anon 可以理解为不拦截
//        filterChainDefinitionMap.put("/*/api-docs", "anon");//anon 可以理解为不拦截
//        filterChainDefinitionMap.put("/callback*", "anon");//anon 可以理解为不拦截
//        filterChainDefinitionMap.put("/configuration/*", "anon");//anon 可以理解为不拦截
//        filterChainDefinitionMap.put("/*/configuration/*", "anon");//anon 可以理解为不拦截
//        filterChainDefinitionMap.put("/webjars/**", "anon");//anon 可以理解为不拦截
        filterChainDefinitionMap.put("/logout", "anon");
        filterChainDefinitionMap.put("/shiro-cas", "cas");// shiro集成cas后，首先添加该规则
        filterChainDefinitionMap.put("/api/**", "user");
//        filterChainDefinitionMap.put("/login", "user");
        // authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter
//        filterChainDefinitionMap.put("/user", "authc");// 这里为了测试，只限制/user，实际开发中请修改为具体拦截的请求规则
//        // anon：它对应的过滤器里面是空的,什么都没做
//        logger.info("##################从数据库读取权限规则，加载到shiroFilter中##################");
//        filterChainDefinitionMap.put("/user/edit/**", "authc,perms[user:edit]");// 这里为了测试，固定写死的值，也可以从数据库或其他配置中读取
//
//        filterChainDefinitionMap.put("/login", "anon");



        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    }

    /**
     * CAS过滤器
     *
     */
    @Bean(name = "cas")
    public CasFilter getCasFilter() {
//        CasFilter casFilter = new CasFilter();
//        casFilter.setName("casFilter");
//        casFilter.setEnabled(true);
//        // 登录失败后跳转的URL，也就是 Shiro 执行 CasRealm 的 doGetAuthenticationInfo 方法向CasServer验证tiket
//        casFilter.setFailureUrl(loginUrl);// 我们选择认证失败后再打开登录页面
//        return casFilter;
        CasFilter casFilter = new CasFilter();
        casFilter.setSuccessUrl(successUrl);
        return casFilter;
    }

    @Bean(name = "user")
    public UserFilter getUserFilter() {
        UserFilter userFilter = new UserFilter();
//        userFilter.setLoginUrl(loginUrl);
        return userFilter;
    }

    /**
     * 这个有点问题 所有的请求都会拦截掉 配置了/logout也没用
     * @return
     */
    @Bean(name = "logout")
    public LogoutFilter getSkyLogoutFilter() {
        SkyLogoutFilter skyLogoutFilter = new SkyLogoutFilter();
        skyLogoutFilter.setRedirectUrl(casLogoutUrl + "?service=" + successUrl);
        skyLogoutFilter.setEnabled(false);
        return skyLogoutFilter;
    }
}
