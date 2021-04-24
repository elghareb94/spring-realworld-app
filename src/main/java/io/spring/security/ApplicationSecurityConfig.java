package io.spring.security;

import io.spring.jwt.JwtTokenVerifier;
import io.spring.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import io.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests()
//                .antMatchers("/h2-console", "/h2-console/**")
                .antMatchers("/h2", "/h2/**")

                .permitAll()
                .and()
                .headers()
                .frameOptions()
                .sameOrigin();

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(userService), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/authenticate", "/users").permitAll()
//                .antMatchers(HttpMethod.GET,"/articles").hasAnyRole("ADMINS","ADMIN")
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        auth.authenticationProvider(provider);
    }

    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        UserDetails userDetails = User.builder()
//                .username("ahmed")
//                .password(passwordEncoder.encode("password"))
//                .roles("Student")
//                .build();
//
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder);
//        provider.setUserDetailsService(new InMemoryUserDetailsManager(userDetails));
//
//        auth.authenticationProvider(provider);
//    }
}
