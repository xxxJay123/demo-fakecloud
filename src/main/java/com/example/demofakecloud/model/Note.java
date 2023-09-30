package com.example.demofakecloud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Note {
  private Long userid;
  private Long noteid;
  private String notetitle;
  private String notedescription;
}
