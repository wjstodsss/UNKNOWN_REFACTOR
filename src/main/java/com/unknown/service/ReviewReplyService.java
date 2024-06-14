package com.unknown.service;

import java.util.List;

import com.unknown.model.Criteria;
import com.unknown.model.ReviewReplyVO;

public interface ReviewReplyService {
	public int register(ReviewReplyVO vo);

	public ReviewReplyVO get(Long replyId);

	public int modify(ReviewReplyVO vo);

	public int remove(Long replyId);

           // 특정 게시글의 댓글을 페이징하여 가져오는 메서드
	public List<ReviewReplyVO> getList(Criteria cri, Long reviewId);
	
//	public ReplyPageDTO getListPage(Criteria cri, Long reviewId);
}
