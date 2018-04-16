package com.szabodev.spring.angular.ldap.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final AppConfig appConfig;

    @Autowired
    public SecurityConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String[] generatedFiles = {"/**.js", "/fontawesome-*", "/glyphicons-*", "/bootstrap*", "/favicon.ico"};
        String[] unAuthorized = new String[0];
        if (!"production".equals(appConfig.getEnvironment())) {
            unAuthorized = new String[]{"/api/about/**"};
        }
        http.cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)))
                .and()
                .authorizeRequests()
                .antMatchers("/", "/api/auth/**").permitAll()
                .antMatchers(unAuthorized).permitAll()
                .antMatchers(generatedFiles).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginProcessingUrl("/api/auth/login").permitAll()
                .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                .logout().logoutUrl("/api/auth/logout").clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .addLogoutHandler((request, response, authentication) -> logger.info("User successfully logged out, name: {}", authentication.getName()))
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        if ("ldap".equals(appConfig.getLoginMode())) {
            configureLdap(auth,
                    appConfig.getLdapUrl(),
                    appConfig.getLdapBaseDn(),
                    appConfig.getLdapUsername(),
                    appConfig.getLdapPassword(),
                    appConfig.getLdapUserDnPattern());
        } else {
            auth
                    .inMemoryAuthentication()
                    .withUser("admin").password("{noop}" + "admin").roles("ADMIN");
        }
    }

    private void configureLdap(AuthenticationManagerBuilder auth, String ldapUrl, String ldapBaseDn, String ldapUsername, String ldapPassword, String ldapUserDnPattern) throws Exception {
        logger.debug("Configure LDAP, url: {}", ldapUrl);
        auth
                .ldapAuthentication()
                .contextSource()
                .url(ldapUrl + ldapBaseDn)
                .managerDn(ldapUsername)
                .managerPassword(ldapPassword)
                .and()
                .userSearchFilter(ldapUserDnPattern);

    }
}
