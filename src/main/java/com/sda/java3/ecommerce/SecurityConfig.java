package com.sda.java3.ecommerce;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import ch.qos.logback.core.pattern.Converter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/**").permitAll()
            .antMatchers(HttpMethod.POST, "/**").permitAll()
            .and()
            .csrf().disable()
            .oauth2Login()
            .loginPage("/sign-in")
            .defaultSuccessUrl("/sign-in/success").and()
//            .userInfoEndpoint()

            
            .logout()
            .logoutUrl("/sign-out")
            .logoutSuccessUrl("/");
            
    }
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true); // cho phép dấu "/"
        firewall.setAllowSemicolon(true); // cho phép dấu ";"
        firewall.setAllowBackSlash(true); // cho phép dấu "\"
        firewall.setAllowUrlEncodedDoubleSlash(true); // cho phép dấu "%2F"
        firewall.setAllowUrlEncodedPeriod(true); // cho phép dấu "."
        return firewall;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }
    



}
