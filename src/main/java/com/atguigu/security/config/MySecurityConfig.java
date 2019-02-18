package com.atguigu.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) // 控制权限注解Secured开启
//@EnableGlobalMethodSecurity(prePostEnabled = true)  // 控制权限注解PreAuthorize开启
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired @Qualifier("dataSource")
    private DataSource dataSource;

    /**
     * 换成Annotation方式以后，则需要使用@EnableGlobalMethodSecurity(prePostEnabled=true)注解来开启。
     *
     * 并且需要提供以下方法：
     */
    /*@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }*/

    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserService();
    }

    @Bean
    public JdbcTokenRepositoryImpl tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        //自动创建数据表，使用一次后注释掉，不然会报错
//        jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
        return jdbcTokenRepositoryImpl;
    }

    @Bean
    public CsrfSecurityRequestMatcher csrfSecurityRequestMatcher(){
        CsrfSecurityRequestMatcher csrfSecurityRequestMatcher = new CsrfSecurityRequestMatcher();
        List<String> list = new ArrayList<String>();
        list.add("csrfurl");
        csrfSecurityRequestMatcher.setIncludeUrls(list);
        return csrfSecurityRequestMatcher;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.authorizeRequests().antMatchers("/").permitAll();
        //交易权限控制
//        http.authorizeRequests().antMatchers("/level1/**").hasRole("VIP1");
//        http.authorizeRequests().antMatchers("/level2/**").hasRole("VIP2");
//        http.authorizeRequests().antMatchers("/level3/**").hasRole("VIP3");
        //自定义登录URL:userlogin请求获取from的user和pwd
        http.formLogin().usernameParameter("user").passwordParameter("pwd")
                .loginPage("/userlogin").successForwardUrl("/");
        //安全退出后跳转的URL
        http.logout().logoutSuccessUrl("/");
        //记住我
        http.rememberMe().rememberMeParameter("remeber");
        //记住我86400=1天
        http.rememberMe().tokenValiditySeconds(86400).tokenRepository(tokenRepository());
//        http.csrf().disable();//关闭CSRF
        http.csrf().requireCsrfProtectionMatcher(csrfSecurityRequestMatcher());
    }

    //用户认证
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        //添加在内存中的用户，并可给用户指定角色权限
        /*auth.inMemoryAuthentication()
                .withUser("zhangsan").password("123456").roles("VIP1","VIP2")
                .and().withUser("lisi").password("123456").roles("VIP2","VIP3")
                .and().withUser("wangwu").password("123456").roles("VIP1","VIP3");*/
        //user处理和BCrypt加密
        auth.userDetailsService(userDetailsService()).passwordEncoder(new BCryptPasswordEncoder());
    }
}
