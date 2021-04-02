package com.axsos.exambuilder.config;

import com.axsos.exambuilder.services.UserDetailsServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImplementation userDetailsService;

    public WebSecurityConfig(UserDetailsServiceImplementation userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                // .antMatchers("/static/**", "/registration","/createUser").permitAll()
                // .antMatchers("/static/**").permitAll()
                // .antMatchers("/**").authenticated()
                .antMatchers("/admin/**").access("hasRole('ADMIN')")    // NEW    // NEW
                .antMatchers("/instructor/**").access("hasRole('INSTRUCTOR')")    // NEW    // NEW
                .antMatchers("/student/**").access("hasRole('STUDENT')")    // NEW    // NEW  
                .antMatchers("/students").hasAnyRole("ADMIN","INSTRUCTOR")    // NEW    // NEW  
                .antMatchers("/instructors/**").access("hasRole('ADMIN')")    // NEW    // NEW  
                .antMatchers("/css/**","/js/**").permitAll()
                // .antMatchers("/registration","/createUser").permitAll()
                .antMatchers("admin/nav.jsp").access("hasRole('STUDENT')")
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }
    // @Override
    // public void configure(WebSecurity web) throws Exception {
    //     web.ignoring().antMatchers("/static/**");
    // }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
