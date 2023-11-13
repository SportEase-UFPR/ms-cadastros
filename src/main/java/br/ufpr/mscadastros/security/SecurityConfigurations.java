package br.ufpr.mscadastros.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfigurations {

    private final SecurityFilter securityFilter;

    @Autowired
    public SecurityConfigurations(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable() //desabilita a proteção contra csrf (o token já é uma proteção pra isso)
                .cors() // Adicionar essa linha para habilitar o suporte a CORS
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //desabilita a autenticação stateful que utiliza sessões e página de login do spring security
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/espacos-esportivos/existe-por-id/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/espacos-esportivos/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/espacos-esportivos/buscar-inf-complementares-locacao").permitAll()
                .requestMatchers(HttpMethod.PUT, "/espacos-esportivos/atualizar-media-avaliacao/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/clientes/buscar-lista-nomes").permitAll()
                .requestMatchers(HttpMethod.GET, "/clientes/buscar-ids").permitAll()
                .requestMatchers(HttpMethod.GET, "/clientes/via-ms/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/buscar-emails-clientes/via-ms").permitAll()


                .requestMatchers(HttpMethod.POST, "/espacos-esportivos/buscar-lista-ee-simplificado").permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //serve para chamar o filtro (SecurityFilter) antes do filtro do spring
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        //método que cria um objeto AuthenticationManager
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
