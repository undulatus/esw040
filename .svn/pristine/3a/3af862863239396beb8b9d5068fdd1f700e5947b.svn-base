package com.pointwest.workforce.planner.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pointwest.workforce.planner.security.RoleAccessDeniedHandler;
import com.pointwest.workforce.planner.security.TokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public RoleAccessDeniedHandler roleAccessDeniedHandler;
	
	@Bean
	public TokenFilter tokenFilter() throws Exception {
		return new TokenFilter();
	}
	
	public WebSecurityConfig() {
        super(true);
    }
	
	@Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
		.csrf().disable()
		// non authorized role handler
		.exceptionHandling().accessDeniedHandler(roleAccessDeniedHandler).and()

		// don't create session
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
		
		//allow this for CRM?
		.authorizeRequests()
	        .antMatchers("/estimate-worksheet/public/**").permitAll()
            .anyRequest().authenticated();
	
		// Custom JWT based security filter
		httpSecurity.addFilterAt(tokenFilter(), UsernamePasswordAuthenticationFilter.class);
		
//		BMAB for reference
//		httpSecurity
//	        .authorizeRequests()
//            .antMatchers( HttpMethod.GET, "/opportunities");
//            .addFilterBefore(new TokenFilter(), BasicAuthenticationFilter.class); 
//	            .antMatchers(
//                        HttpMethod.GET,
//                        "/",
//                        "/*.html",
//                        "/favicon.ico",
//                        "/**/*.html",
//                        "/**/*.css",
//                        "/**/*.js"
//                ).permitAll();
//		httpSecurity.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
//      httpSecurity.addFilter(tokenFilter());
	        
    }
	
}