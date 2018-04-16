package com.szabodev.spring.angular.ldap.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

    @Value("${environment}")
    private String environment;

    @Value("${login.mode}")
    private String loginMode;

    @Value("${ldap.url}")
    private String ldapUrl;

    @Value("${ldap.base.dn}")
    private String ldapBaseDn;

    @Value("${ldap.username}")
    private String ldapUsername;

    @Value("${ldap.password}")
    private String ldapPassword;

    @Value("${ldap.user.dn.pattern}")
    private String ldapUserDnPattern;

    public String getEnvironment() {
        return environment;
    }

    public String getLoginMode() {
        return loginMode;
    }

    public String getLdapUrl() {
        return ldapUrl;
    }

    public String getLdapBaseDn() {
        return ldapBaseDn;
    }

    public String getLdapUsername() {
        return ldapUsername;
    }

    public String getLdapPassword() {
        return ldapPassword;
    }

    public String getLdapUserDnPattern() {
        return ldapUserDnPattern;
    }

    /**
     * Returns the application version string when you run the jar
     * When you run from IntelliJ it returns 'Local'
     *
     * @return the Implementation-Version defined in the MANIFEST.MF
     */
    public String getAppVersion() {
        String version = getClass().getPackage().getImplementationVersion();
        return version != null ? version : "Local";
    }
}
