package com.example.demofakecloud.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.entity.UserFile;
import com.example.demofakecloud.entity.Impl.CustomUserDetails;
import com.example.demofakecloud.repository.UserRepository;

import com.example.demofakecloud.service.UserFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/files")
@RequiredArgsConstructor
@Slf4j
public class UserFileController {
    @Autowired
    private UserFileService storageService;
    @Autowired
    private final UserRepository userRepository;


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("file in controller: {}", file.toString());

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not authenticated.");
        }
        try {
            byte[] data = file.getBytes();
            String fileType = file.getContentType();
            String fileName = file.getOriginalFilename();
            User user = userRepository.findById(userDetails.getUser().getId())
                    .orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("User not found.");
            }

            storageService.storeFile(file, fileName, fileType, data, user);

            log.info("File saved in DB: {}", fileName);
            return ResponseEntity.ok("File uploaded successfully: " + fileName);
        } catch (Exception e) {
            log.error("Failed to upload the file: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload the file: " + e.getMessage());
        }
    }


    // @PostMapping("/download")
    // public ResponseEntity<Resource> downloadFile(
    //         @RequestParam("filename") String fileName,
    //         @AuthenticationPrincipal CustomUserDetails userDetails) {
    //     if (userDetails == null) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    //     }

    //     Resource file =
    //             storageService.load(fileName, userDetails.getUser().getId());
    //     if (file.exists() && file.isReadable()) {
    //         return ResponseEntity.ok(file);
    //     } else {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    //     }
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
}

