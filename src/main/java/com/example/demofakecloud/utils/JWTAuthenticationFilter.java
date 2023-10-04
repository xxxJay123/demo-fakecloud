package com.example.demofakecloud.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.demofakecloud.service.AuthenticationService;
// import com.example.demofakecloud.service.CustomUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JWTAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private JWTGenerator tokenGenerator;
  @Autowired
  private AuthenticationService customUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response, //
      FilterChain filterChain)//
      throws ServletException, IOException {
    String token = getJWTFromRequest(request);
    if (StringUtils.hasText(token) && tokenGenerator.validateTokens(token)) {
      String username = tokenGenerator.getUsernameFromToken(token);

      UserDetails userDetails =
          customUserDetailsService.loadUserByUsername(username);
      if (userDetails != null) {
        log.info("User authenticated in Filter: {}", userDetails.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext()
            .setAuthentication(authenticationToken);
        log.info("UsernamePasswordAuthenticationToken: {}",
            authenticationToken);

        // Proceed with the request after authentication
        filterChain.doFilter(request, response);
        log.info("Proceed with the request after authentication");
      } else {
        log.warn("User details not found for username: {}", username);
        // Handle unauthorized access
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      }
    } else {
      log.warn("Invalid or expired token");
      // Handle unauthorized access
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
  }



  // @Override
  // protected void doFilterInternal(HttpServletRequest req,
  // HttpServletResponse res, FilterChain chain)
  // throws IOException, ServletException {
  // String header = req.getHeader("Authorization");
  // String username = null;
  // String authToken = null;

  // if (header != null && header.startsWith("Bearer ")) {
  // authToken = header.replace("Bearer ", "");
  // try {
  // username = tokenGenerator.getUsernameFromToken(authToken);
  // } catch (IllegalArgumentException e) {
  // logger.error("An error occurred during getting username from token", e);
  // } catch (ExpiredJwtException e) {
  // logger.warn("The token is expired and not valid anymore", e);
  // }
  // } else {
  // logger.warn("Couldn't find bearer string, will ignore the header");
  // }

  // if (username != null
  // && SecurityContextHolder.getContext().getAuthentication() == null) {
  // UserDetails userDetails =
  // customUserDetailsService.loadUserByUsername(username);
  // if (tokenGenerator.validateToken(authToken, userDetails)) {
  // String role = userDetails.getAuthorities().size() > 1 ? "ROLE_ADMIN"
  // : "ROLE_TOURIST";
  // UsernamePasswordAuthenticationToken authentication =
  // new UsernamePasswordAuthenticationToken(userDetails, null,
  // Arrays.asList(new SimpleGrantedAuthority(role)));
  // authentication
  // .setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
  // logger.info(
  // "Authenticated user " + username + ", setting security context");
  // SecurityContextHolder.getContext().setAuthentication(authentication);
  // }
  // }
  // chain.doFilter(req, res);
  // }
  private String getJWTFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

}
