package com.blog.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.application.component.JwtTokenFilter;
import com.blog.application.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(
	    prePostEnabled = true, securedEnabled = false, jsr250Enabled = true
	)
public class ApplicationSecurityConfig {
	
	public static final String[] PUBLIC_URL = {
			"/auth/login",
			"/v3/api-docs/**",
			"/v2/api-docs/**",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	
	@Autowired
	JwtTokenFilter jwtTokenFilter;

	 @Bean
	 	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		 
		 http.exceptionHandling()
	        .authenticationEntryPoint(
	            (request, response, ex) -> {
	                response.sendError(
	                    HttpServletResponse.SC_UNAUTHORIZED,
	                    ex.getMessage()
	                );
	            }
	        );
	 
	http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		 
	 		http.csrf(csrf -> csrf.disable())
	 			.authorizeHttpRequests((authorizeHttpRequests) ->
	 				authorizeHttpRequests
	 				.requestMatchers(PUBLIC_URL).permitAll()
 			 		.anyRequest().authenticated()
 			 		
	 			);
	 		return http.build();
		 
	 	}
	 
	 @Autowired private UserRepository userRepo;
	     
	    @Bean
	    public UserDetailsService userDetailsService() {
	        return new UserDetailsService() {
	             
	            @Override
	            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	                return userRepo.findByEmail(username)
	                        .orElseThrow(
	                                () -> new UsernameNotFoundException("User " + username + " not found"));
	            }
	        };
	    }
	     
	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }
	     
	    @Bean
	    public AuthenticationManager authenticationManager(
	            AuthenticationConfiguration authConfig) throws Exception {
	        return authConfig.getAuthenticationManager();
	    }
}
