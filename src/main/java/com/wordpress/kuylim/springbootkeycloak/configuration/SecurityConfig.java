package com.wordpress.kuylim.springbootkeycloak.configuration;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter{

  @Override
  @Bean
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(sessionRegistry());
  }

  @Bean
  public KeycloakConfigResolver keycloakConfigResolver(){
    return new KeycloakSpringBootConfigResolver();
  }

  @Bean
  public SessionRegistry sessionRegistry(){
    return new SessionRegistryImpl();
  }

  @Override
  protected void configure (AuthenticationManagerBuilder auth) throws Exception{
    KeycloakAuthenticationProvider keycloakAuthenticationProvider =keycloakAuthenticationProvider();
    final SimpleAuthorityMapper simpleAuthorityMapper = new SimpleAuthorityMapper();
    simpleAuthorityMapper.setConvertToUpperCase(true);

    keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(simpleAuthorityMapper);

    auth.authenticationProvider(keycloakAuthenticationProvider);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception{
    super.configure(http);

    http.httpBasic().disable();
    http.formLogin().disable();
    http.csrf().disable();

    http.headers()
      .frameOptions().sameOrigin().and()
      .authorizeRequests()
      .antMatchers("/").permitAll()
      .antMatchers("/user/**").hasAnyRole("USER")
      .antMatchers("/admin/**").hasAnyRole("ADMIN")
      .antMatchers("/api").authenticated();
  }
}
