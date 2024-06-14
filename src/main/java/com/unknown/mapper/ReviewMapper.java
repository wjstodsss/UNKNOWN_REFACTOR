package com.unknown.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.unknown.model.Criteria;
import com.unknown.model.ReviewVO;

public interface ReviewMapper {
public List<ReviewVO> getList();
    
    public void insert(ReviewVO review);
	
	public void insertSelectKey(ReviewVO review);
	
	public ReviewVO read(Long reviewId);
	
	public int delete(Long reviewId);
	
	public int update(ReviewVO review);
	
	public List<ReviewVO> getListWithPaging(Criteria cri);
	
	public int getTotalCount(Criteria cri);
	
	public List<ReviewVO> searchTest(Map<String, Map<String, String>> map);
	
	public List<ReviewVO> getListByItemId(int itemId);
	
	List<ReviewVO> getListByMemberId(@Param("memberId") String memberId,@Param("amount") int amount, @Param("pageNum") int pageNum);
	
	int getTotalByMemberId(@Param("memberId") String memberId, @Param("cri") Criteria cri);

	public String getItemNameById(int itemId);

	public String getReviewWriterByReviewId(Long reviewId);
}
