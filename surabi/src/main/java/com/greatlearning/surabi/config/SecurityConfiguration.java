package com.greatlearning.surabi.config;

import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

/**
 * @author sriram
 * SecurityConfiguration class implement authentication and authorization
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;


	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/h2-console/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/surabi/viewgeneratedbills", "/surabi/createuser", "/surabi/updateuser",
						"/surabi/deleteuser", "/surabi/listusers", "/audit/logs")
				.hasRole("ADMIN").antMatchers("/info", "/menu", "/addItemstoOrder", "/confirmorder", "/vieworder")
				.hasAnyRole("ADMIN", "USER").anyRequest().authenticated().and().formLogin();
	}

	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setDataSource(dataSource);

		return jdbcUserDetailsManager;
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
