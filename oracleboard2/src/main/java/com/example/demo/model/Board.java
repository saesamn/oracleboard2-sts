package com.example.demo.model;

import java.sql.Date;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("board")
public class Board {
	  private int no;
	  private String writer;
	  private String passwd;
	  private String subject;
	  private String content;
	  private int readcount;
	  private Date register;
	  
	  private int startRow;
	  private int endRow;
	  
	  private String search;
	  private String keyword;
}
