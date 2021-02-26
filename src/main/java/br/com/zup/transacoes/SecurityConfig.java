package br.com.zup.transacoes;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> {
            authorizeRequests
                    .antMatchers(HttpMethod.GET, "/actuator/prometheus").permitAll()
                    .antMatchers(HttpMethod.GET, "/actuator/**").hasAuthority("SCOPE_actuator:read")
                    .antMatchers(HttpMethod.GET, "/api/v1/**").hasAuthority("SCOPE_transacao:read");
        })
            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .cors().disable()
            .csrf().disable()
            .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint());
    }

    private static class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse,
                             AuthenticationException e) throws IOException {
            String json = "{\"erro\": \"Credenciais inválidas.\"}";
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setLocale(LocaleContextHolder.getLocale());
            httpServletResponse.getOutputStream()
                               .write(json.getBytes(StandardCharsets.UTF_8));

        }
    }
}
