# demo-fakecloud


## Register
```Java
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
```
![Alt text](/images/image.png)

## Login

```Java

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
```
![Alt text](/images/image-1.png)

##  通過Token Upload File
```Java
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
```
![Alt text](/images/image-3.png)
![Alt text](/images/image-4.png)
![Alt text](/images/image-2.png)