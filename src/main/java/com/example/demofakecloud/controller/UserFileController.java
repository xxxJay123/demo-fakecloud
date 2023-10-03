package com.example.demofakecloud.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.entity.UserFile;
import com.example.demofakecloud.repository.UserRepository;
import com.example.demofakecloud.service.UserFileService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class UserFileController {
  @Autowired
  private UserFileService userFileService;
  @Autowired
  private final UserRepository userRepository;

  @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                   @AuthenticationPrincipal User user) {
        try {
            UserFile userFile = new UserFile();
            userFile.setFileName(file.getOriginalFilename());
            userFile.setFileType(file.getContentType());
            userFile.setData(file.getBytes());
            userFile.setUser(user); // Set the user for the file

            userFileService.saveFile(userFile);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading the file.");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserFile>> listFiles(@AuthenticationPrincipal User user) {
        List<UserFile> files = userFileService.getAllFilesByUser(user);
        return ResponseEntity.ok(files);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Optional<UserFile> fileOptional = userFileService.getFileById(id);
        if (fileOptional.isPresent() && fileOptional.get().getUser().equals(user)) {
            userFileService.deleteFile(id);
            return ResponseEntity.ok("File deleted successfully!");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to delete the file.");
    }
}
