package com.project.mall.demo.config;

import com.project.mall.demo.bo.AdminUserDetails;
import com.project.mall.mapper.UmsAdminMapper;
import com.project.mall.model.UmsAdmin;
import com.project.mall.model.UmsAdminExample;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.List;

/**
 * Spring Security 配置（Spring Boot 3 / Security 6：使用 SecurityFilterChain）
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                     UserDetailsService userDetailsService,
                                                     PasswordEncoder passwordEncoder) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        http.authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll())
                .authenticationProvider(authProvider)
                .httpBasic(basic -> basic.realmName("/"))
                .formLogin(form -> form.loginPage("/login").failureUrl("/login?error=true"))
                .logout(logout -> logout.logoutSuccessUrl("/"))
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UmsAdminMapper umsAdminMapper) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                UmsAdminExample example = new UmsAdminExample();
                example.createCriteria().andUsernameEqualTo(username);
                List<UmsAdmin> umsAdminList = umsAdminMapper.selectByExample(example);
                if (umsAdminList != null && !umsAdminList.isEmpty()) {
                    return new AdminUserDetails(umsAdminList.get(0));
                }
                throw new UsernameNotFoundException("用户名或密码错误");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
