package com.szabodev.spring.angular.ldap.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BeansConfig {

    private static final Logger logger = LoggerFactory.getLogger(BeansConfig.class);

    private final AppConfig appConfig;

    @Autowired
    public BeansConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            String appVersion = appConfig.getAppVersion();
            logger.info("Application version: {}", appVersion);
            System.out.println("Application version: " + appVersion);
        };
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                if (!"production".equals(appConfig.getEnvironment())) {
                    registry.addMapping("/**")
                            .allowedOrigins("http://localhost:4200")
                            .allowedMethods("GET", "POST", "DELETE");
                }
            }
        };
    }
}
