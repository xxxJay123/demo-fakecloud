package com.example.demofakecloud.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demofakecloud.entity.UserFile;
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
}
