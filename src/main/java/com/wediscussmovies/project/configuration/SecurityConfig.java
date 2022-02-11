package com.wediscussmovies.project.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomUsernamePasswordAuthenticationProvider authenticationProvider;

    public SecurityConfig(CustomUsernamePasswordAuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/*.jpg");
        web.ignoring().antMatchers("/*.png");
        web.ignoring().antMatchers("/*.css");
        web.ignoring().antMatchers("/*.js");


    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // TODO: If you are implementing the security requirements, remove this following line
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/")
//                .permitAll()
//                .anyRequest()
//                .permitAll();
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/movies","/movies/**/",
                        "/actors","/persons/**/","/directors","/discussions",
                        "/profiles/**","/discussions/**/","/discussions/all/**/","/replies",
                        "/register","/genres", "/css/**","/img/**", "/js/**","/graphql/**/**").permitAll()
                .antMatchers("/graphql")
                .permitAll()
                .anyRequest()
                .authenticated()

                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login?error=BadCredentials")
                .defaultSuccessUrl("/movies", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/movies");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }
}
