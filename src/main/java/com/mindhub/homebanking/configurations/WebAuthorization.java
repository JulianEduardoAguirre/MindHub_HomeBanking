package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity
@Configuration
public class WebAuthorization {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/app/login").permitAll()
                .antMatchers("/rest/**").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers("/api/**").hasAuthority("CLIENT")
                .antMatchers("/web/index.html").permitAll()
                .antMatchers("/web/accounts/**").authenticated()
                .antMatchers("/web/cards.html").authenticated()
                .antMatchers("/app/logout").authenticated();

//                        .antMatchers("/api/**").hasAuthority("ADMIN")
//                .antMatchers("/rest/**").hasAuthority("ADMIN")
//                .anyRequest().denyAll();
//        http.authorizeRequests()
//                        .antMatchers("/api/**").hasAnyAuthority();
//                .antMatchers("/app/clients").hasAuthority("CLIENT")
//                        .antMatchers("/app/login").hasAnyAuthority();
//                        .antMatchers("/api/**").hasAnyAuthority();
//                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
//                .antMatchers("/h2-console").hasAuthority("ADMIN")
//                .antMatchers("/rest/**").hasAuthority("CLIENT")
//                .antMatchers("/web/clients").hasAuthority("CLIENT")
//                .antMatchers("/web/clients/**").hasAuthority("CLIENT")
//
//                .antMatchers("/index").hasAuthority("CLIENT")
//                .antMatchers("/**").hasAnyAuthority();
//        .antMatchers("/**").hasAnyAuthority();

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/app/login");
//
        http.logout().logoutUrl("/app/logout");


//        //Turn off checking for CSRF tokens
//        http.csrf().disable();
//
//        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();
//
//        //auth failure response - Not authenticated
        http.exceptionHandling().authenticationEntryPoint( (req, res, exc) ->
//                res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
//
//        //clear flags for auth when login is ok
        http.formLogin().successHandler( (req, res, auth) ->
                clearAuthenticationAttributes(req));
//
//        //If login fails, just send an auth failure response
        http.formLogin().failureHandler( (req, res, exc) ->
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
//
//        //if logout is successful, just send a success response
        http.logout().logoutSuccessHandler( new HttpStatusReturningLogoutSuccessHandler());


        return http.build();
    }

    private static final String[] AUTH_WHITELIST = {
            "/js", "js/**", "/css", "/css/**", "/img"
    } ;


    //@Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/app/login").permitAll()
                .antMatchers("/rest/**").hasAuthority("CLIENT")
                .antMatchers("/api/**").hasAuthority("CLIENT")
                .antMatchers("/web/accounts").authenticated()
                .antMatchers("/web/accounts/**").authenticated()
                .antMatchers("app/logout").authenticated();

//                        .antMatchers("/api/**").hasAuthority("ADMIN")
//                .antMatchers("/rest/**").hasAuthority("ADMIN")
//                .anyRequest().denyAll();
//        http.authorizeRequests()
//                        .antMatchers("/api/**").hasAnyAuthority();
//                .antMatchers("/app/clients").hasAuthority("CLIENT")
//                        .antMatchers("/app/login").hasAnyAuthority();
//                        .antMatchers("/api/**").hasAnyAuthority();
//                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
//                .antMatchers("/h2-console").hasAuthority("ADMIN")
//                .antMatchers("/rest/**").hasAuthority("CLIENT")
//                .antMatchers("/web/clients").hasAuthority("CLIENT")
//                .antMatchers("/web/clients/**").hasAuthority("CLIENT")
//
//                .antMatchers("/index").hasAuthority("CLIENT")
//                .antMatchers("/**").hasAnyAuthority();
//        .antMatchers("/**").hasAnyAuthority();

        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/app/login");
//
        http.logout().logoutUrl("/app/logout");


//        //Turn off checking for CSRF tokens
//        http.csrf().disable();
//
//        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();
//
//        //auth failure response - Not authenticated
        http.exceptionHandling().authenticationEntryPoint( (req, res, exc) ->
//                res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
//
//        //clear flags for auth when login is ok
        http.formLogin().successHandler( (req, res, auth) ->
                clearAuthenticationAttributes(req));
//
//        //If login fails, just send an auth failure response
        http.formLogin().failureHandler( (req, res, exc) ->
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
//
//        //if logout is successful, just send a success response
        http.logout().logoutSuccessHandler( new HttpStatusReturningLogoutSuccessHandler());


    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }

    }


}
