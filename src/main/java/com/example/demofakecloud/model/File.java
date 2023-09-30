package com.example.demofakecloud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class File {
  
  private Long fileid;
  private Long userid;

  private String filename;
  private String contenttype;
  private String filesize;

  private byte[] filedata;
}
