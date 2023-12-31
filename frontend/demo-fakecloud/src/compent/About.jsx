import React, { useState, useEffect } from "react";
import Header from "./Header";
import Footer from "./Footer";
import { FaGithub } from "react-icons/fa";
import ReactMarkdown from "react-markdown";

import "./About.css";

const About = () => {
  const [isCodeVisible, setCodeVisible] = useState(false);

  const handleGithubClick = () => {
    window.open("https://github.com/xxxJay123/demo-fakecloud", "_blank");
  };
  const markdownContent = `
  # About Page
  
  This is a demo application for a fake cloud storage service. You can find
  the source code on GitHub.
  
  `;

  const markdownContent1 = `
  ## Register
  \`\`\`Java
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody RegisterDTO registerDto) {
    try {
      // Check if the username already exists
      if (userRepository.existsByUserName(registerDto.getUserName())) {
        return ResponseEntity.badRequest().body("Username is taken!");
      }

      // Check if the role 'USER' exists in the database
      Optional<Role> optionalRole = roleRepository.findByName("USER");
      Role role = optionalRole.orElseGet(() -> {
        Role newRole = new Role();
        newRole.setName("USER");
        return roleRepository.save(newRole); // Save the newRole and return the managed entity
      });

      // Create a new user with the provided details
      User user = new User();
      user.setUserName(registerDto.getUserName());
      user.setUserPassword(
          passwordEncoder.encode(registerDto.getUserPassword()));
      user.setUserEmail(registerDto.getUserEmail());

      // Associate the user with the role
      user.setRoles(Collections.singletonList(role));

      // Save the user entity, which will also save the associated role
      userRepository.save(user);

      return ResponseEntity.ok("User registered successfully!");
    } catch (Exception e) {
      // Handle specific exceptions if necessary
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("Failed to register user: " + e.getMessage());
    }
  }
\`\`\`
  
  ## Login
  
  \`\`\`Java
  
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto) {
  
      // Find user by username
      User user = userRepository.findByUserName(loginDto.getUserName());
  
      if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
  
      // Check if password matches
      if (!passwordEncoder.matches(loginDto.getUserPassword(),
          user.getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
  
      // Authenticate the user
      Authentication authentication = authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(loginDto.getUserName(),
              loginDto.getUserPassword()));
  
      // If authentication successful, generate JWT token
      if (authentication.isAuthenticated()) {
  
        String token = jwtGenerator.generateToken(authentication);
        log.info("Token: {}", token);
        return ResponseEntity.ok(new AuthResponseDTO(token));
      } else {
        // If authentication fails, return unauthorized response
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
  
    }
    \`\`\`
  
  ##  通過Token Upload File
  \`\`\`Java
   @PostMapping("/upload")
      public ResponseEntity<String> uploadFile(
              @RequestParam("file") MultipartFile file,
              @AuthenticationPrincipal CustomUserDetails userDetails) {
          try {
              if (userDetails == null) {
                  return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                          .body("User not authenticated.");
              }
              String username = userDetails.getUsername();
              User user = userRepository.findByUserName(username);
  
  
              Blob data = blobConverter.convertToBlob(file);
              String fileType = file.getContentType();
              String fileName = file.getOriginalFilename();
  
  
              if (user == null) {
                  return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                          .body("User not found.");
              }
  
              storageService.storeFile(file, fileName, fileType, data, user);
  
              log.info("File saved in DB: {}", fileName);
              return ResponseEntity.ok("File uploaded successfully: " + fileName);
          } catch (Exception e) {
              return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                      .body("Failed to upload the file: " + e.getMessage());
          }
      }
      \`\`\`
  
  `;

  return (
    <div>
      <Header />
      <div className="about-container">
        <main className="center-content">
          <div className="text-container">
            <div className="markdown-content">
              <h1>About Page</h1>
              <p>
                This is a demo application for a fake cloud storage service. You
                can find the source code on GitHub.
              </p>
            </div>
            <a
              href="https://github.com/xxxJay123/demo-fakecloud"
              target="_blank"
              rel="noopener noreferrer"
            >
              GitHub Repository
            </a>

            <div className="white">------------------------------------------------</div>
            <div className="white">------------------------------------------------</div>
            <div className="markdown-content-code scrollable-code-container">
              <ReactMarkdown>{markdownContent1}</ReactMarkdown>
            </div>
          </div>
        </main>
      </div>
      <Footer />
    </div>
  );
};

export default About;
