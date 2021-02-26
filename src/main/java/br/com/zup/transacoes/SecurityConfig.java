package br.com.zup.transacoes;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> {
            authorizeRequests
                    .antMatchers(HttpMethod.GET, "/api/v1/**")
                    .hasAuthority("SCOPE_transacoes:read");
        })
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().cors()
            .and().csrf().disable();
    }
}
