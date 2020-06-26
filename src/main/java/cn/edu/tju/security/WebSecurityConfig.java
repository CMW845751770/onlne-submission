package cn.edu.tju.security;

import cn.edu.tju.security.filter.CustomLoginFilter;
import cn.edu.tju.security.filter.CustomSecurityInterceptor;
import cn.edu.tju.security.login.CustomLoginAuthEntryPoint;
import cn.edu.tju.security.login.CustomLoginAuthFailureHandler;
import cn.edu.tju.security.login.CustomLoginAuthSuccessHandler;
import cn.edu.tju.security.login.CustomLogoutSuccessHandler;
import cn.edu.tju.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.PropertySource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * spring-security配置
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CustomDetailService customDetailService;

    @Autowired
    private PropertySource propertySourceBean;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.debug("权限框架配置");

        String[] paths = null;
        //设置不拦截
        if (propertySourceBean.getProperty("security.ignoring") != null) {
            paths = propertySourceBean.getProperty("security.ignoring").toString().split(",");
            paths = StringUtil.clearSpace(paths);
        }

        //设置过滤器
        http.authorizeRequests().antMatchers(paths).permitAll()
                .and()
                .httpBasic()
                .authenticationEntryPoint(getCustomLoginAuthEntryPoint())
                .and()
                .addFilterAt(getCustomLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(getCustomSecurityInterceptor(), FilterSecurityInterceptor.class)
                .logout().logoutSuccessHandler(getCustomLogoutSuccessHandler())
                .and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .permitAll();
        logger.debug("配置忽略验证url");
        http.headers().frameOptions().sameOrigin();
    }

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(getDaoAuthenticationProvider());
    }





    /**
     * spring security 配置
     * @return
     */
    @Bean
    public CustomLoginAuthEntryPoint getCustomLoginAuthEntryPoint() {
        return new CustomLoginAuthEntryPoint();
    }

    /**
     * 用户验证
     * @return
     */
    @Bean
    public DaoAuthenticationProvider getDaoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customDetailService);
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    /**
     * 登陆
     * @return
     */
    @Bean
    public CustomLoginFilter getCustomLoginFilter() {
        CustomLoginFilter filter = new CustomLoginFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        filter.setAuthenticationSuccessHandler(getCustomLoginAuthSuccessHandler());
        filter.setAuthenticationFailureHandler(getCustomLoginAuthFailureHandler());

        return filter;
    }

    @Bean
    public CustomLoginAuthSuccessHandler getCustomLoginAuthSuccessHandler() {
        CustomLoginAuthSuccessHandler handler =  new CustomLoginAuthSuccessHandler();
        if (propertySourceBean.getProperty("security.successUrl")!=null){
            handler.setAuthSuccessUrl(propertySourceBean.getProperty("security.successUrl").toString());
        }
        return handler;
    }

    @Bean
    public CustomLoginAuthFailureHandler getCustomLoginAuthFailureHandler() {
        return new CustomLoginAuthFailureHandler();
    }

    /**
     * 登出
     * @return
     */
    @Bean
    public CustomLogoutSuccessHandler getCustomLogoutSuccessHandler() {
        CustomLogoutSuccessHandler handler = new CustomLogoutSuccessHandler();
        if (propertySourceBean.getProperty("security.logoutSuccessUrl")!=null){
            handler.setLoginUrl(propertySourceBean.getProperty("security.logoutSuccessUrl").toString());
        }
        return handler;
    }

    /**
     * 过滤器
     * @return
     */
    @Bean
    public CustomSecurityInterceptor getCustomSecurityInterceptor() {
        CustomSecurityInterceptor interceptor = new CustomSecurityInterceptor();
        interceptor.setAccessDecisionManager(getCustomAccessDecisionManager());
        interceptor.setSecurityMetadataSource(getCustomMetadataSourceService());
        try {
            interceptor.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return interceptor;
    }

    @Bean
    public CustomAccessDecisionManager getCustomAccessDecisionManager() {
        return new CustomAccessDecisionManager();
    }

    @Bean
    public CustomMetadataSourceService getCustomMetadataSourceService() {
        CustomMetadataSourceService sourceService = new CustomMetadataSourceService();
        if (propertySourceBean.getProperty("security.successUrl")!=null){
            sourceService.setIndexUrl(propertySourceBean.getProperty("security.successUrl").toString());
        }
        return sourceService;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
