package com.unknown.service;

import java.util.List;

import com.unknown.model.BoardVO;
import com.unknown.model.Criteria;

public interface BoardService {
	public void register(BoardVO board);
	
	public BoardVO get(Long noticeId);
	
	public boolean modify(BoardVO board);
	   
	public boolean remove(Long noticeId);
	   
	public List<BoardVO> getList(Criteria cri);
	
	public int getTotal(Criteria cri);
}
