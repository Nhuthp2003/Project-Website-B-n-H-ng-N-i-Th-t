package com.arphor;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.arphor.entity.UserRoles;
import com.arphor.service.UserRolesService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRolesService userRolesService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/profile/**", "/order/**").authenticated()
                .antMatchers("/dashboard/**").hasRole("Admin")
                .anyRequest().permitAll()
            .and()
            .formLogin()
                .loginPage("/account/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/account/login=success", false)
                .failureUrl("/account/login=error")
            .and()
            .exceptionHandling()
                .accessDeniedPage("/account/unauthorized")
            .and()
            .logout()
                .logoutUrl("/account/logout")
                .logoutSuccessUrl("/account/logout=success");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            UserRoles userRoles = userRolesService.getUserByEmail(username);
            if (userRoles == null) {
                throw new UsernameNotFoundException(username + " not found");
            }
            
            if(userRoles.getDeleted() == 1) {
                throw new UsernameNotFoundException(username + " has been deleted");
            }
            
            String password = passwordEncoder().encode(userRoles.getPassword());
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + userRoles.getRole().getRoleName()));
            return new org.springframework.security.core.userdetails.User(username, password, authorities);
        });
    }
}
