package org.example.myapp.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                /*------------------------------------------------------
                 * Autorisations
                 *-----------------------------------------------------*/
                .authorizeHttpRequests(auth -> auth
                        // requêtes internes (FORWARD, ERROR)
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()

                        // ressources publiques
                        .requestMatchers(
                                "/", "/membres/nouveau",
                                "/loginPage", "/perform_login",
                                "/accueil", "/test",
                                "/forgot-password/**", "/reset-password/**",
                                "/css/**", "/js/**", "/images/**", "/webjars/**",
                                "/api/**",
                                "/sorties/**","/categories/**"
                        ).permitAll()


                        // tout le reste nécessite une authentification
                        .anyRequest().authenticated()
                )

                /*------------------------------------------------------
                 * Form-login
                 *-----------------------------------------------------*/
                .formLogin(form -> form
                        .loginPage("/loginPage")
                        .loginProcessingUrl("/perform_login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)            // 👈 redirige toujours vers /
                        .failureUrl("/loginPage?error=true")
                        .permitAll()
                )

                /*------------------------------------------------------
                 * Logout
                 *-----------------------------------------------------*/
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")                   // 👈 retour à l’accueil
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )

                /*------------------------------------------------------
                 * CSRF (activé par défaut)
                 *-----------------------------------------------------*/
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")      // désactive CSRF pour l’API REST
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
