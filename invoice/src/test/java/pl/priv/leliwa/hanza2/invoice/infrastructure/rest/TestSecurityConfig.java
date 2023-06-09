package pl.priv.leliwa.hanza2.invoice.infrastructure.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class TestSecurityConfig {

    @Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
            .csrf().disable()
			.authorizeHttpRequests((requests) -> requests
                .anyRequest().permitAll()
			);

		return http.build();
	}

}