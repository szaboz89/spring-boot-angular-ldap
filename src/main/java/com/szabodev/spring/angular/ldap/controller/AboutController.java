package com.szabodev.spring.angular.ldap.controller;

import com.szabodev.spring.angular.ldap.config.AppConfig;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/about/")
public class AboutController {

    private final AppConfig appConfig;

    public AboutController(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @GetMapping("/version")
    @ResponseStatus(HttpStatus.OK)
    public String getAppVersion() {
        return appConfig.getAppVersion();
    }
}
