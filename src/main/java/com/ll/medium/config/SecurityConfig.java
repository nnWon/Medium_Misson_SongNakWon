package com.ll.medium.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //CSRF 비활성화
        http.csrf((csrf) -> csrf.disable());

        //HttpServletRequest를 사용하는 요청에 대해 접근제한을 하겠다.
        http.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                //해당 경로 요청은 인증없이 접근을 허가한다.
                .requestMatchers(new AntPathRequestMatcher("/**")).permitAll());

        //커스텀 로그인 페이지 등록
        http.formLogin(form -> form.loginPage("/member/login"));

        http.logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                //로그아웃 URL을 설정 (기본값은 /logout)
                .logoutUrl("/member/logout")
                //로그아웃이 성공하면, / 로 리다이렉트
                .logoutSuccessUrl("/")
        );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}