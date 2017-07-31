package com.pointwest.workforce.planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan("com.pointwest.workforce.planner")
@PropertySource("classpath:db-values.properties")
@PropertySource("classpath:security-config.properties")
@SpringBootApplication
public class WorkforcePlannerApplication extends SpringBootServletInitializer {
	
	/**
     * Added for parsing
     * a JacksonJsonParser() implementation
     * @return - parser bean
     */
    @Bean
    public JsonParser jsonParser() {
    	JsonParser parser = new JacksonJsonParser();
    	return parser;
    }
    
    /*@Bean
    public Jackson2ObjectMapperBuilder objectMapper() {
    	Jackson2ObjectMapperBuilder objectMapper = new Jackson2ObjectMapperBuilder();
    	objectMapper.indentOutput(true);
    	return objectMapper;
    } */
    
	public static void main(String[] args) {
		SpringApplication.run(WorkforcePlannerApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WorkforcePlannerApplication.class);
    }
	
	/*@Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }*/
}
