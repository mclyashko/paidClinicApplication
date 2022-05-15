package ru.mirea.paidClinicApplication.configurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.mirea.paidClinicApplication.services.appUser.UserDetailsServiceImpl;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsServiceImpl appUserService) {
        this.userDetailsService = appUserService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/login", "/logout", "/registration")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main")
                .and().rememberMe()
                .tokenValiditySeconds((int) TimeUnit.MINUTES.toSeconds(5))
                .and().userDetailsService(userDetailsService)
                .sessionManagement();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() { // валидация
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(authProvider());
    }
}
