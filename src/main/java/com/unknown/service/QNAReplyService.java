package com.unknown.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unknown.model.Criteria;
import com.unknown.model.QNAReplyVO;
@Service
public interface QNAReplyService {
	
	public int register(QNAReplyVO vo);

	public QNAReplyVO get(Long replyId);

	public int modify(QNAReplyVO vo);

	public int remove(Long replyId);

           // 특정 게시글의 댓글을 페이징하여 가져오는 메서드
	public List<QNAReplyVO> getList(Criteria cri, Long qnaId);
}
