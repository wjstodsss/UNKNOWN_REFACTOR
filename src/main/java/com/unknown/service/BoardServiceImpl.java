package com.unknown.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.model.BoardVO;
import com.unknown.model.Criteria;
import com.unknown.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper mapper;
	
	@Override
	public BoardVO get(Long noticeId) {
		 log.info("get....." + noticeId);
	      return mapper.read(noticeId);
	}

	@Override
	public boolean modify(BoardVO board) {
		log.info("modify.... " + board);
	     return mapper.update(board)==1;
	}


	@Override
	public boolean remove(Long noticeId) {
		log.info("remove...." + noticeId);
	     return mapper.delete(noticeId)==1;
	}


	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("getList.....");
		//return mapper.getList();
		return mapper.getListWithPaging(cri);
	}

	@Override
	public void register(BoardVO board) {
		log.info("register.... " + board);
		mapper.insertSelectKey(board);
		
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	
}
