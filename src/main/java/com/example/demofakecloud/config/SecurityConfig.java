package com.example.demofakecloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;
import com.example.demofakecloud.service.AuthenticationService;
import com.example.demofakecloud.utils.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import com.example.demofakecloud.config.CustomAuthenticationProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Order(1)
public class SecurityConfig {
  @Autowired
  private JwtAuthEntryPoint authEntryPoint;

  @Value("${jwt.secret}")
  private String secretKey;

  // @Autowired
  // private CustomAuthenticationProvider customAuthenticationProvider;

  @Autowired
  private AuthenticationService authenticationService;

  private static final String[] WHITE_LIST_URL = {//
      "/swagger-resources", //
      "/swagger-resources/**", //
      "/configuration/ui", //
      "/configuration/security", //
      "/swagger-ui/**", //
      "/webjars/**", //
      "/swagger-ui.html", //
      "/api/auth/**", //
      //"/api/files/upload"//

  };//

  // @Autowired
  // private JwtAuthEntryPoint authEntryPoint;
  // @Autowired
  // private CustomUserDetailsService userDetailsService;


  @Bean
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http//
        .csrf(csrf -> csrf.disable())//
        .authorizeHttpRequests((requests) -> requests//
            .requestMatchers(WHITE_LIST_URL).permitAll() // allow CORS option calls for Swagger UI

            .anyRequest().authenticated())//
        .httpBasic(withDefaults());//
    http.addFilterBefore(jwtAuthenticationFilter(),
        UsernamePasswordAuthenticationFilter.class);//

    return http.build();//
  }

  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  JWTAuthenticationFilter jwtAuthenticationFilter() {
    return new JWTAuthenticationFilter();
  }

  // @Bean
  // Authentication authentication(AuthenticationManager authenticationManager) {
  //   return authenticationManager
  //       .authenticate(new UsernamePasswordAuthenticationToken("USER", "ADMIN"));
  // }

  // @Bean
  // AuthenticationManagerBuilder customAuthenticationProvider(
  //     AuthenticationManagerBuilder auth) throws Exception {
  //   return auth.authenticationProvider(customAuthenticationProvider);
  // }
  /*
   * @Bean Key secretKey() { return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName()); }
   */


}

