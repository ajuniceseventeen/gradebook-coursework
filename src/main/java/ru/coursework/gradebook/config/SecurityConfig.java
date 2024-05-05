package ru.coursework.gradebook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.coursework.gradebook.user.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // Создание экземпляра CustomUserDetailsService для загрузки информации о пользователях
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    // Создание экземпляра BCryptPasswordEncoder для хэширования паролей
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Создание AuthenticationProvider для аутентификации пользователей
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // Настройка цепочки фильтров безопасности
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
                 .csrf(AbstractHttpConfigurer::disable)
                 .authorizeHttpRequests((authorize) -> authorize
                         .requestMatchers("/admin/**").hasRole("ADMIN")
                         .requestMatchers("/auth/login", "/auth/signup", "/error").permitAll()
                         .anyRequest().authenticated()
                 )
//                 .httpBasic(Customizer.withDefaults())
                 .formLogin(form -> form
                         .loginPage("/auth/login")
                         .loginProcessingUrl("/process_login")
                         .defaultSuccessUrl("/home", true)
                         .failureUrl("/auth/login?error")

//                 ).logout(((logout) -> logout
//                         .logoutUrl("/logout")
//                         .permitAll()
//                         .logoutSuccessUrl("/auth/login")
//                         .deleteCookies("JSESSIONID"))
                 );

         return http.build();
    }
}
