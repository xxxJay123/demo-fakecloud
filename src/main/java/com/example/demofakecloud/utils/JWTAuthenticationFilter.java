package com.example.demofakecloud.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.example.demofakecloud.service.AuthenticationService;
import org.springframework.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
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
    //String requestUri = request.getRequestURI();
    // Check if the request URI matches any allowed endpoint without a token
    if (isEndpointAllowedWithoutToken(request)) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = getJWTFromRequest(request);
    if (StringUtils.hasText(token) && tokenGenerator.validateTokens(token)
       ) {
      String username = tokenGenerator.getUsernameFromToken(token);

      UserDetails userDetails =
          customUserDetailsService.loadUserByUsername(username);
      if (userDetails != null) {
        UsernamePasswordAuthenticationToken authenticationToken =
            new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());
        authenticationToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext()
            .setAuthentication(authenticationToken);
      }
      filterChain.doFilter(request, response);

    }

  }

  private String getJWTFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }

  private boolean isEndpointAllowedWithoutToken(HttpServletRequest request) {
    String requestUri = request.getRequestURI();
    // List of endpoints that don't require a token
    List<String> allowedEndpoints = Arrays.asList("/api/auth/register",
        "/api/auth/login", "/public-endpoint");

    // Check if the request URI matches any allowed endpoint
    return allowedEndpoints.contains(requestUri);
  }

}
