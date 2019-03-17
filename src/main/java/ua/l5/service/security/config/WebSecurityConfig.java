package ua.l5.service.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ua.l5.service.security.firlters.TokenAuthFilter;

@ComponentScan("ua.l5")
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationProvider authenticationProvider;

    private final TokenAuthFilter tokenAuthFilter;

    @Autowired
    public WebSecurityConfig(AuthenticationProvider authenticationProvider, TokenAuthFilter tokenAuthFilter) {
        this.authenticationProvider = authenticationProvider;
        this.tokenAuthFilter = tokenAuthFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class)
                .antMatcher("/**")
                .authenticationProvider(authenticationProvider)
                .authorizeRequests()
                .antMatchers("/users/**").hasAuthority("USER")
                .antMatchers("/login").permitAll();
        http.csrf().disable();
    }
}
