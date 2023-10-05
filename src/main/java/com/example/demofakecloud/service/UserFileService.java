package com.example.demofakecloud.service;

import java.sql.Blob;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.entity.UserFile;
import com.example.demofakecloud.entity.Impl.CustomUserDetails;
import com.example.demofakecloud.repository.UserFileRepository;
import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;

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

  @Transactional
  public UserFile storeFile(MultipartFile file, String fileName,
      String fileType, Blob data, User user) throws IOException {
    UserFile userFile = new UserFile();
    userFile.setFileName(fileName);
    userFile.setFileType(fileType);
    userFile.setData(data);
    userFile.setUser(user);

    // Save file metadata to the database
    return userFileRepository.save(userFile);
  }
}
