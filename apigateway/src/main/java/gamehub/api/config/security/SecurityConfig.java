package gamehub.api.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            "/user/create",
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Autowired
    private GHAuthenticationProvider ghAuthenticationProvider;

    @Autowired
    private GHAuthenticationEntryPoint ghAuthenticationEntryPoint;

    @Autowired
    private GHAuthenticationSuccessHandler ghAuthenticationSuccessHandler;

    @Autowired
    private GHLogoutSuccessHandler ghLogoutSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(ghAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(ghAuthenticationEntryPoint)
                .and()
                    .authorizeRequests()
                    .antMatchers(AUTH_WHITELIST).permitAll()
                    .antMatchers("/**").authenticated()
                .and()
                    .formLogin()
                    .successHandler(ghAuthenticationSuccessHandler)
                .and()
                    .logout()
                    .logoutSuccessHandler(ghLogoutSuccessHandler);
    }
}
