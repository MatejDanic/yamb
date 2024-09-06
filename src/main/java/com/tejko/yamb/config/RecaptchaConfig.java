package com.tejko.yamb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "recaptcha.key")
public class RecaptchaConfig {

    private String site;
    private String secret;

    public String getSite() {
        return site;
    }
    
    public void setSite(String site) {
        this.site = site;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}