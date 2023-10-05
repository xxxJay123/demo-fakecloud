// package com.example.demofakecloud.config;

// import org.hibernate.annotations.Comment;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationProvider;
// import org.springframework.security.authentication.BadCredentialsException;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.AuthenticationException;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.stereotype.Component;
// import com.example.demofakecloud.repository.UserRepository;
// import com.example.demofakecloud.service.AuthenticationService;

// import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor
// @Component
// public class CustomAuthenticationProvider implements AuthenticationProvider {
//   @Autowired
//   private AuthenticationService authenticationService;

//   @Override
//   public Authentication authenticate(Authentication authentication)
//           throws AuthenticationException {
//       String username = authentication.getName(); // Obtain username from authentication object
//       UserDetails userDetails = authenticationService.loadUserByUsername(username);

//       // If userDetails is null or not valid, you should throw an AuthenticationException
//       if (userDetails == null || !userDetails.isAccountNonExpired()
//               || !userDetails.isEnabled()) {
//           throw new BadCredentialsException("Invalid credentials");
//       }

//       return new UsernamePasswordAuthenticationToken(userDetails, null,
//               userDetails.getAuthorities());
//   }

//   @Override
//   public boolean supports(Class<?> authentication) {
//     return UsernamePasswordAuthenticationToken.class
//         .isAssignableFrom(authentication);
//   }
// }

