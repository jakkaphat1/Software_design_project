package com.example.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import jakarta.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Autowired
	UserSuccessHandler userSuccessHandler;
	
	@Autowired
	EmpUserDatailsService empUserDatailsService;
	
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@PostConstruct
    public void init() {
        // รหัสผ่านที่คุณต้องการเข้ารหัส
        String rawPassword = "123";
        String encodedPassword = passwordEncoder().encode(rawPassword);

        // พิมพ์รหัสผ่านที่ถูกเข้ารหัสลงในคอนโซล
        System.out.println("Encoded password: " + encodedPassword);
    }
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(c -> c.disable())
            .authorizeHttpRequests(request -> request
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/user-page/**").hasAuthority("USER")
                .requestMatchers("/kitchen", "/kitchen/**").hasAuthority("KITCHENMAN")

                .requestMatchers("/","/admin/register", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(userSuccessHandler)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
	
	
	
	
	@Autowired
	public void configure (AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(empUserDatailsService).passwordEncoder(passwordEncoder());
	}
	
	
	
}
