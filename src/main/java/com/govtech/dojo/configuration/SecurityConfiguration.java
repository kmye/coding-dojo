package com.govtech.dojo.configuration;

//import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/").permitAll()
				.and()
			.formLogin()
				.loginPage("/login")
				.usernameParameter("email")
				.permitAll()
				.and()
			.logout()
				.permitAll();

		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
}
