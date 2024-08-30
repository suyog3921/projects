package com.app.security;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity//to enable spring sec frmwork support
@Configuration //to tell SC , this is config class containing @Bean methods
@EnableGlobalMethodSecurity(prePostEnabled = true)
//To enable method level authorization support : pre n post authorization
public class SecurityConfig {
	//dep : pwd encoder
	@Autowired
	private PasswordEncoder enc;
	
	@Autowired
	private BCryptPasswordEncoder benc;
	//dep : custom jwt auth filter
	@Autowired
	private JwtAuthenticationFilter jwtFilter;
	//dep : custom auth entry point
	@Autowired
	private CustomAuthenticationEntryPoint authEntry;
	
	@Autowired
    private CustomAuthenticationSuccessHandler successHandler;
	@Bean
	public SecurityFilterChain authorizeRequests(HttpSecurity http) throws Exception
	{
		//URL based authorization rules
		http.cors()
		.and().
		//disable CSRF token generation n verification
		csrf()	.disable()
		.exceptionHandling().authenticationEntryPoint(authEntry).
		and().
		authorizeRequests()
		.antMatchers(
			    "/seat/update-seats",
			    "/order",
			    "/api/showtimes/**/seats",
			    "/api/showtimes/find",
			    "/moviestest/**",
			    "/users/home",
			    "/users/signup",
			    "/users/signin",
			    "/v*/api-doc*/**",
			    "/swagger-ui/**",
//			    "/moviestest"
			    "/api/bookings"
			    
			).permitAll()
		// only required for JS clnts (react / angular) : for the pre flight requests
		.antMatchers(HttpMethod.OPTIONS).permitAll()

		.antMatchers("/order","/users/getUsers").hasRole("USER")
		.antMatchers("/admin/moviestest","/moviestest/add", "/moviestest/update/**", "/moviestest/delete/**").hasRole("ADMIN")

		.anyRequest().authenticated()
		.and()
		.formLogin()
        .successHandler(successHandler)  // Use the custom success handler
        .and()//to tell spring sec : not to use HttpSession to store user's auth details
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		//inserting jwt filter before sec filter
		.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	
		return http.build();
	}
	//configure AuthMgr as a spring bean
	@Bean
	public AuthenticationManager authenticationManager
	(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
	
	@Autowired
    private UserDetailsService userDetailsService;
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	    auth.userDetailsService(userDetailsService)
//	        .passwordEncoder(passwordEncoder());
//	}
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//            .passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//            .authorizeRequests()
//            .antMatchers("/admin/**").hasRole("ADMIN")
//            .antMatchers("/user/**").hasRole("USER")
//            .anyRequest().authenticated()
//            .and()
//            .formLogin()
//            .loginPage("/login")
//            .successHandler(customAuthenticationSuccessHandler())  // Custom success handler
//            .permitAll()
//            .and()
//            .logout()
//            .permitAll();
//    }
//    
//    @Bean
//    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
//        return new CustomAuthenticationSuccessHandler();
//    }
    
    
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
}
