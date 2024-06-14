package com.unknown.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.unknown.model.Criteria;
import com.unknown.model.ReviewReplyVO;

public interface ReviewReplyMapper {
	public int insert(ReviewReplyVO vo);
	public ReviewReplyVO read(Long replyId);
	public int delete(Long replyId);
	//reply, update만 수정가능
	public int update(ReviewReplyVO reply);
	
	 public List<ReviewReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("reviewId") Long reviewId);
	 
	 public int getCountByReplyId(Long reviewId);
}

