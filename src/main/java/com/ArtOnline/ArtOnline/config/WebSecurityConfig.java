package com.ArtOnline.ArtOnline.config;

import com.ArtOnline.ArtOnline.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();

        http.authorizeRequests().antMatchers("/", "/login","/register", "/logout").permitAll();

        http.authorizeRequests().antMatchers("/self", "/*","/self/**").access("hasAnyAuthority('user', 'gallery', 'admin')");

        http.authorizeRequests().antMatchers( "/account", "/account/**").access("hasAnyAuthority('gallery', 'admin')");

        http.authorizeRequests().antMatchers("/admin", "/admin/**").access("hasAuthority('admin')");

        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        http.authorizeRequests().and().formLogin().loginProcessingUrl("/j_spring_security_check")
                .defaultSuccessUrl("/welcome").failureUrl("/login?error=true").usernameParameter("emailId")
                .passwordParameter("password").and().logout().logoutUrl("/logout").logoutSuccessUrl("/");

    }
}