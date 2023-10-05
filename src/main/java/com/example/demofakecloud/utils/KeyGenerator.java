// package com.example.demofakecloud.utils;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.multipart.MultipartFile;
// import com.example.demofakecloud.entity.Impl.CustomUserDetails;

// // import java.security.SecureRandom;
// // import java.util.Base64;

// public class KeyGenerator {
//   // JWTFilter

//   @Override
//   protected void doFilterInternal(HttpServletRequest request,
//       HttpServletResponse response, FilterChain filterChain) {

//     String token = getTokenFromRequest(request);

//     if (token != null) {

//       // Validate token
//       if (jwtUtil.validateToken(token)) {

//         // Get username from token
//         String username = jwtUtil.getUsernameFromToken(token);

//         // Load user details
//         UserDetails userDetails =
//             userDetailsService.loadUserByUsername(username);

//         // Get roles from token and set authorities
//         List<String> roles = jwtUtil.getRolesFromToken(token);
//         userDetails.setAuthorities(roles);

//         // Create auth token
//         Authentication auth = new UsernamePasswordAuthenticationToken(
//             userDetails, null, userDetails.getAuthorities());

//         // Set authentication context
//         SecurityContextHolder.getContext().setAuthentication(auth);

//       } else {
//         response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//         return;
//       }

//     }

//     filterChain.doFilter(request, response);

//   }
// }@PreAuthorize("hasRole('USER')") 
// @PostMapping("/upload")
// public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {

//   // Get authenticated user
//   UserDetails userDetails = (UserDetails)SecurityContextHolder
//     .getContext().getAuthentication().getPrincipal();

//   // Check user role
//   if (!userDetails.getAuthorities().contains("ROLE_USER")) {
//     return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).build();
//   }

//   // Upload file logic...

//   return ResponseEntity.ok("File uploaded!");

// }

// // JWT Util

// public String getRolesFromToken(String token) {
//   // Decode token and extract roles
// }

// public boolean validateToken(String token) {
//   // Verify signature and expiration
// }
