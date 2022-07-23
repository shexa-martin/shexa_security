package com.example.shexa_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity //this class is where everything to do with security of our application goes
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private  final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {//method that will configure HTTP requests security
        http
                .authorizeRequests()
//                filtering URLs to permit
                .antMatchers("/","index", "/css/*", "/js/*")
//                Allowing above URLs requests
                .permitAll()
//                any other requests
                .anyRequest()
//                must be authenticated
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {//method used to authenticate with custom users stored in a database
        UserDetails shexaMartinUser = User.builder()
                .username("shexa")
                .password(passwordEncoder.encode("password"))
                .roles("STUDENT")
                .build();
        return new InMemoryUserDetailsManager(
                shexaMartinUser
        );
    }
}
