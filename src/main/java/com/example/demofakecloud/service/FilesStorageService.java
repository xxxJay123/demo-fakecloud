package com.example.demofakecloud.service;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import com.example.demofakecloud.entity.Impl.CustomUserDetails;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilesStorageService {
  private final UserFileService userFileService;
  private final Path root = Paths.get("./uploads");


  public void init() {
    try {
      Files.createDirectories(root);
    } catch (IOException e) {
      throw new RuntimeException("Could not initialize folder for upload!");
    }
  }

  public String save(MultipartFile file, CustomUserDetails user) {
    try {
      String uniqueFileName = generateUniqueFileName(file);
      Path userFolderPath = root.resolve(user.toString());

      if (!Files.exists(userFolderPath)) {
        Files.createDirectory(userFolderPath);
      }

      Path filePath = userFolderPath.resolve(uniqueFileName);
      Files.copy(file.getInputStream(), filePath);
      userFileService.saveFileMetadata(user, uniqueFileName,
          file.getContentType());

      return uniqueFileName;
    } catch (Exception e) {
      if (e instanceof FileAlreadyExistsException) {
        throw new RuntimeException("A file of that name already exists.");
      }
      throw new RuntimeException("Failed to save the file: " + e.getMessage());
    }
  }

  public Resource load(String filename, Long userId) {
    try {
      Path userFolderPath = root.resolve(userId.toString());
      Path file = userFolderPath.resolve(filename);
      Resource resource = new UrlResource(file.toUri());

      if (resource.exists() && resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read the file!");
      }
    } catch (Exception e) {
      throw new RuntimeException("Error: " + e.getMessage());
    }
  }

  public void deleteAll(Long userId) {
    Path userFolderPath = root.resolve(userId.toString());
    FileSystemUtils.deleteRecursively(userFolderPath.toFile());
  }

  // Generate a unique file name using UUID
  private String generateUniqueFileName(MultipartFile file) {
    return UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
  }
}
