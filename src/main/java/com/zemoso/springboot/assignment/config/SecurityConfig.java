package com.zemoso.springboot.assignment.config;

import com.zemoso.springboot.assignment.controller.LogoutHandler;
import lombok.Generated;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Generated
public class SecurityConfig {
    private final LogoutHandler logoutHandler;

    public SecurityConfig(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    /**
     * @deprecated This method is deprecated and marked for removal.
     *             Use an alternative approach for configuring the security filter chain.
     *             Don't forget to remove this deprecated code someday.
     */
    @Bean
    @Deprecated(forRemoval = true)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .oauth2Login().and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .addLogoutHandler(logoutHandler)
                .and();

        return http.build();
    }
}
