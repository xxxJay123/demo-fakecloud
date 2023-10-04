package com.example.demofakecloud.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.entity.UserFile;
import com.example.demofakecloud.entity.Impl.CustomUserDetails;
import com.example.demofakecloud.repository.UserFileRepository;

@Service
public class UserFileService {
  @Autowired
  private UserFileRepository userFileRepository;


  public void saveFile(UserFile userFile) {
    userFileRepository.save(userFile);
  }

  public Optional<UserFile> getFileById(Long id) {
    return userFileRepository.findById(id);
  }

  public List<UserFile> getAllFiles() {
    return userFileRepository.findAll();
  }

  public void deleteFile(Long id) {
    userFileRepository.deleteById(id);
  }

  public List<UserFile> getAllFilesByUser(User user) {
    return userFileRepository.findByUser(user);
  }

  public void saveFileMetadata(CustomUserDetails user, String fileName,
      String fileType) {
    // Create a new UserFile entity and set its properties
    UserFile userFile = new UserFile();
    userFile.setUser(user.getUser());
    userFile.setFileName(fileName);
    userFile.setFileType(fileType);

    // Save the user file metadata to the database
    userFileRepository.save(userFile);
  }
}
