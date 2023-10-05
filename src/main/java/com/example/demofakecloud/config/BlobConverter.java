package com.example.demofakecloud.config;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import javax.sql.rowset.serial.SerialBlob;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class BlobConverter {
  public Blob convertToBlob(MultipartFile multipartFile) throws IOException, SQLException {
        byte[] bytes = multipartFile.getBytes();
        return new SerialBlob(bytes);
    }
}
