package com.example.demofakecloud.controller;

import java.sql.Blob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demofakecloud.config.BlobConverter;
import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.entity.Impl.CustomUserDetails;
import com.example.demofakecloud.repository.UserRepository;
import com.example.demofakecloud.service.UserFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/api/files")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@Slf4j
public class UserFileController {
    @Autowired
    private UserFileService storageService;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BlobConverter blobConverter;


    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok(" you have access now  ");
    }

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

    // @PostMapping("/download")
    // public ResponseEntity<Resource> downloadFile(
    // @RequestParam("filename") String fileName,
    // @AuthenticationPrincipal CustomUserDetails userDetails) {
    // if (userDetails == null) {
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    // }

    // Resource file =
    // storageService.load(fileName, userDetails.getUser().getId());
    // if (file.exists() && file.isReadable()) {
    // return ResponseEntity.ok(file);
    // } else {
    // return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    // }
    // }
    // @PostMapping("/upload")
    // public ResponseEntity<String> handleFileUpload(
    // @RequestParam("file") MultipartFile file,
    // @AuthenticationPrincipal CustomUserDetails userDetails) {
    // log.info("Received file upload request from user: {}",
    // userDetails.getUsername());
    // try {
    // UserFile userFile = new UserFile();
    // userFile.setFileName(file.getOriginalFilename());
    // userFile.setFileType(file.getContentType());
    // userFile.setData(file.getBytes());
    // userFile.setUser(userDetails.getUser()); // Set the user for the file

    // userFileService.saveFile(userFile);
    // log.info("File uploaded successfully: {} by user: {}",
    // file.getOriginalFilename(), userDetails.getUsername());
    // return ResponseEntity.ok("File uploaded successfully!");
    // } catch (IOException e) {
    // log.error("Error uploading the file: {}", e.getMessage());
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body("Error uploading the file.");
    // }
    // }

    // @GetMapping("/list")
    // public ResponseEntity<List<UserFile>> listFiles(
    // @AuthenticationPrincipal CustomUserDetails userDetails) {
    // List<UserFile> files =
    // userFileService.getAllFilesByUser(userDetails.getUser());
    // return ResponseEntity.ok(files);
    // }

    // @DeleteMapping("/delete/{id}")
    // public ResponseEntity<String> deleteFile(@PathVariable Long id,
    // @AuthenticationPrincipal CustomUserDetails userDetails) {
    // Optional<UserFile> fileOptional = userFileService.getFileById(id);
    // if (fileOptional.isPresent()
    // && fileOptional.get().getUser().equals(userDetails.getUser())) {
    // userFileService.deleteFile(id);
    // return ResponseEntity.ok("File deleted successfully!");
    // }
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    // .body("Unauthorized to delete the file.");
    // }

    // @PostMapping("/upload")
    // public ResponseEntity<String> uploadFile(
    // @RequestParam("file") MultipartFile file, LoginDTO loginDTO) {

    // try {

    // Blob data = blobConverter.convertToBlob(file);
    // String fileType = file.getContentType();
    // String fileName = file.getOriginalFilename();
    // User user = userRepository.findByUserName(loginDTO.getUserName());


    // if (user == null) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body("User not found.");
    // }

    // storageService.storeFile(file, fileName, fileType, data, user);

    // log.info("File saved in DB: {}", fileName);
    // return ResponseEntity.ok("File uploaded successfully: " + fileName);
    // } catch (Exception e) {
    // log.error("Failed to upload the file: {}", e.getMessage());
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body("Failed to upload the file: " + e.getMessage());
    // }
    // }

    // @PostMapping("/upload")
    // public ResponseEntity<String> uploadFile(
    // @RequestParam("file") MultipartFile file) {
    // try {
    // CustomUserDetails userDetails =
    // (CustomUserDetails) SecurityContextHolder.getContext()
    // .getAuthentication().getPrincipal();

    // if (userDetails == null) {
    // return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
    // .body("Unauthorized access.");
    // }
    // String username = userDetails.getUsername();
    // User user = userRepository.findByUserName(username);


    // Blob data = blobConverter.convertToBlob(file);
    // String fileType = file.getContentType();
    // String fileName = file.getOriginalFilename();


    // if (user == null) {
    // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    // .body("User not found.");
    // }

    // storageService.storeFile(file, fileName, fileType, data, user);

    // log.info("File saved in DB: {}", fileName);
    // return ResponseEntity.ok("File uploaded successfully: " + fileName);
    // } catch (Exception e) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body("Failed to upload the file: " + e.getMessage());
    // }
    // }
}

