package com.example.demofakecloud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demofakecloud.entity.UserFile;

public interface UserFileRepository extends JpaRepository<UserFile, Long> {

}
