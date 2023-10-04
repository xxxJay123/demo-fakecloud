package com.example.demofakecloud.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demofakecloud.entity.User;
import com.example.demofakecloud.entity.UserFile;

public interface UserFileRepository extends JpaRepository<UserFile, Long> {

  List<UserFile> findByUser(User user);

}
