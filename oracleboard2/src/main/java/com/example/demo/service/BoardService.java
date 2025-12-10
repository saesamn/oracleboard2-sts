package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BoardDao;
import com.example.demo.model.Board;

@Service
public class BoardService {

	@Autowired
	private BoardDao dao;

	public int insert(Board board) {
		return dao.insert(board);
	}

	public int count(Board board) {
		return dao.count(board);
	}

	public List<Board> list(Board board) {
		return dao.list(board);
	}

	public void updatecount(int no) {
		 dao.updatecount(no);
	}

	public Board content(int no) {
		return dao.content(no);
	}

	public int update(Board board) {
		return dao.update(board);
	}

	public int delete(int no) {
		return dao.delete(no);
	}
	
}


