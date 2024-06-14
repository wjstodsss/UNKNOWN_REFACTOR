package com.unknown.mapper;

import java.util.List;
import java.util.Map;

import com.unknown.model.Criteria;
import com.unknown.model.QNAVO;

public interface QNAMapper {
	public List<QNAVO> getList();
    
    public void insert(QNAVO qna);
	
	public void insertSelectKey(QNAVO qna);
	
	public QNAVO read(Long qnaId);
	
	public int delete(Long qnaId);
	
	public int update(QNAVO qna);
	
	public List<QNAVO> getListWithPaging(Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	public List<QNAVO> searchTest(Map<String, Map<String, String>> map);
}
