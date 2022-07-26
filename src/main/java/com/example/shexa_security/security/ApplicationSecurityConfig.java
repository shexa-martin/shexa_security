package com.example.shexa_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.example.shexa_security.security.ApplicationUserPermission.COURSE_WRITE;
import static com.example.shexa_security.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity //this class is where everything to do with security of our application goes
@EnableGlobalMethodSecurity(prePostEnabled = true)//this enables us to secure our http request methods using Annotations
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private  final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {//method that will configure HTTP requests security
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .authorizeRequests()
//                filtering URLs to permit/Allow these URLs requests
                .antMatchers("/","index", "/css/*", "/js/*").permitAll()
//                Now protect URL requests with roles and /api/ will be accessed only by students
//                This is Role based Authentication
                .antMatchers("/api/**").hasRole(STUDENT.name())
//                Permission based authendication method 1
//                Since order of these HttpMethodProtetos matter, you may not specify the last protetor for GET may not be specified
//                .antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers( "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())//this is for GET Method
//                any other requests
                .anyRequest()
//                must be authenticated
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll();
//                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {//method used to authenticate with custom users stored in a database
        UserDetails shexaMartinUser = User.builder()
                .username("shexa")
                .password(passwordEncoder.encode("password"))
//                .roles(STUDENT.name())//Added static import to ApplicationUserRole.STUDENT.name()
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails goldenRuthUser = User.builder()
                .username("golden")
                .password(passwordEncoder.encode("password123"))
//                .roles(ADMIN.name()) //Added static import to ApplicationUserRole.ADMIN.name()
                .authorities(ADMIN.getGrantedAuthorities())
                .build();
        UserDetails malaUser = User.builder()
                .username("mala")
                .password(passwordEncoder.encode("password123"))
//                .roles(ADMINTRAINEE.name()) //Added static import to ApplicationUserRole.ADMIN.name()
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();
        return new InMemoryUserDetailsManager(
                shexaMartinUser,
                goldenRuthUser,
                malaUser
        );
    }
}
