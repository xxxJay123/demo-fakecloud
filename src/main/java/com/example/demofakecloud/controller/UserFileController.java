package com.example.demofakecloud.controller;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.demofakecloud.entity.UserFile;
import com.example.demofakecloud.service.UserFileService;

@Controller
@RequestMapping("/files")
public class UserFileController {
  @Autowired
  private UserFileService userFileService;

  @GetMapping("/list")
  public String listFiles(Model model) {
    List<UserFile> files = userFileService.getAllFiles();
    model.addAttribute("files", files);
    return "drive/file-list";
  }

  @PostMapping("/upload")
  public String handleFileUpload(@RequestParam("file") MultipartFile file) {
    try {
      UserFile userFile = new UserFile();
      userFile.setFileName(file.getOriginalFilename());
      userFile.setFileType(file.getContentType());
      userFile.setData(file.getBytes());
      userFileService.saveFile(userFile);
    } catch (IOException e) {
      return "redirect:/files/list";
    }
    return "redirect:/drive/list";
  }

  @GetMapping("/delete/{id}")
  public String deleteFile(@PathVariable Long id) {
    userFileService.deleteFile(id);
    return "redirect:/drive/list";
  }
}
