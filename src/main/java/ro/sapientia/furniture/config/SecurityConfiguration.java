package ro.sapientia.furniture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")          // Apply to all endpoints
                .allowedOrigins("*")        // Allow ANY domain/frontend
                .allowedMethods("*")        // Allow ANY method (GET, POST, PUT, DELETE, etc.)
                .allowedHeaders("*");       // Allow ANY header
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.csrf().disable().authorizeRequests().antMatchers("/").permitAll();
        return http.build();
    }

}