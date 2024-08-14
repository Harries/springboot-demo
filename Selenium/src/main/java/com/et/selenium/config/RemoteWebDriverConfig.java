package com.et.selenium.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;

import java.net.URL;

// for selenium grid run; this will be the remote profile
@Lazy
@Configuration
@Profile("remote") // only activate this class if remote profile
public class RemoteWebDriverConfig {

    @Value("${selenium.grid.url}")
    private URL url;


    @Bean
    @ConditionalOnProperty(name = "browser", havingValue = "firefox")
    public WebDriver remoteFirefoxDriver(){
        return new RemoteWebDriver(this.url, DesiredCapabilities.firefox());
    }

    @Bean
    @ConditionalOnMissingBean // to catch invalid browser values
    public WebDriver remoteChromeDriver(){
        return new RemoteWebDriver(this.url, DesiredCapabilities.chrome());
    }

}
