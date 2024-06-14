package com.unknown.service;

import java.util.List;

import com.unknown.model.Criteria;
import com.unknown.model.ReviewVO;

public interface ReviewService {
	public void register(ReviewVO reviewVO);
	
	public ReviewVO get(Long reviewId);
	
	public boolean modify(ReviewVO reviewVO);
	   
	public boolean remove(Long reviewId);
	   
	public List<ReviewVO> getList(Criteria cri);
	
	public int getTotal(Criteria cri);
	
	public List<ReviewVO> getListByItemId(int itemId);
	 
	List<ReviewVO> getListByMemberId(String memberId, Criteria cri);
	 
	int getTotalByMemberId(String memberId, Criteria cri);
	 
	String getItemNameById(int itemId);
	
	String getReviewWriterByReviewId(Long reviewId);
}
