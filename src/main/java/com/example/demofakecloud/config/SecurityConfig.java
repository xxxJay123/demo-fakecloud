package com.example.demofakecloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static org.springframework.security.config.Customizer.withDefaults;
import com.example.demofakecloud.service.AuthenticationService;
// import com.example.demofakecloud.service.CustomUserDetailsService;
import com.example.demofakecloud.utils.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import com.example.demofakecloud.repository.UserRepository;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Order(1)
public class SecurityConfig {

  @Autowired
  private JwtAuthEntryPoint authEntryPoint;
  @Autowired
  private final PasswordEncoder passwordEncoder;
  @Autowired
  private final JWTAuthenticationFilter jwtAuthenticationFilter;
  // @Autowired
  // private CustomUserDetailsService customUserDetailsService;
  @Autowired
  private AuthenticationService authenticationService;
  




  // private static final String[] WHITE_LIST_URL = {//
  // "/swagger-resources", //
  // "/swagger-resources/**", //
  // "/configuration/ui", //
  // "/configuration/security", //
  // "/swagger-ui/**", //
  // "/webjars/**", //
  // "/swagger-ui.html", //
  // "/api/auth/**", //
  // "/api/auth/register",//

  // };//

  // @Autowired
  // private JwtAuthEntryPoint authEntryPoint;
  // @Autowired
  // private CustomUserDetailsService userDetailsService;


  @Bean 
  SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http//
        .cors(withDefaults())
        .csrf(csrf -> csrf//
            .disable()//
        )
        .cors(withDefaults())//
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)//
        )//
        .authorizeHttpRequests((requests) -> requests//
            // .requestMatchers(HttpMethod.POST, WHITE_LIST_URL).permitAll() // allow CORS option calls for Swagger UI
            .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()//
            .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()//
            .requestMatchers(HttpMethod.POST, "api/files/upload")
            .authenticated()//
            // .requestMatchers(HttpMethod.POST,"/api/auth/logout").permitAll()
            // .requestMatchers(HttpMethod.POST,"/api/auth/refreshToken").permitAll()
            // .requestMatchers(HttpMethod.GET,"/api/auth/me").permitAll()
            // .requestMatchers(HttpMethod.GET,"/api/auth/users").permitAll()
            // .requestMatchers(HttpMethod.GET,"/api/auth/users/{id}").permitAll()
            // .requestMatchers("/api/files/upload").permitAll()
            .anyRequest().authenticated()//

        )//
        .authenticationProvider(//
            authenticationProvider())//
        .addFilterBefore(jwtAuthenticationFilter, //
            UsernamePasswordAuthenticationFilter.class);//


    return http.build();//
  }

  // @Bean
  // AuthenticationService userDetailsService(UserRepository userRepository) {
  // return new AuthenticationService(userRepository);
  // }

  @Bean
  AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider =
        new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(authenticationService);
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
    return daoAuthenticationProvider;
  }


  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  JWTAuthenticationFilter jwtAuthenticationFilter() {
    return new JWTAuthenticationFilter();
  }

  // @Bean
  // Authentication authentication(AuthenticationManager authenticationManager) {
  // return authenticationManager
  // .authenticate(new UsernamePasswordAuthenticationToken("USER", "ADMIN"));
  // }

  // @Bean
  // AuthenticationManagerBuilder customAuthenticationProvider(
  // AuthenticationManagerBuilder auth) throws Exception {
  // return auth.authenticationProvider(customAuthenticationProvider);
  // }
  /*
   * @Bean Key secretKey() { return new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName()); }
   */


}

