package com.unknown.mapper;

import java.util.List;
import java.util.Map;



import com.unknown.model.BoardVO;
import com.unknown.model.Criteria;

public interface BoardMapper {
//	@Select("select * from board_Notice where noticeId >  0")
    public List<BoardVO> getList();
    
    public void insert(BoardVO board);
	
	public void insertSelectKey(BoardVO board);
	
	public BoardVO read(Long noticeId);
	
	public int delete(Long noticeId);
	
	public int update(BoardVO board);
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	public List<BoardVO> searchTest(Map<String, Map<String, String>> map);
}
