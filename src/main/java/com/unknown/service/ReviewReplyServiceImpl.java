package com.unknown.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unknown.model.Criteria;
import com.unknown.model.ReviewReplyVO;
import com.unknown.mapper.ReviewReplyMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class ReviewReplyServiceImpl implements ReviewReplyService {
	private ReviewReplyMapper mapper;

	@Override
	public int register(ReviewReplyVO vo) {
		log.info("register......" + vo);
		return mapper.insert(vo);

	}

	@Override
	public ReviewReplyVO get(Long replyId) {
		log.info("get......" + replyId);
		return mapper.read(replyId);

	}

	@Override
	public int modify(ReviewReplyVO vo) {
		log.info("modify......" + vo);
		return mapper.update(vo);

	}

	@Override
	public int remove(Long replyId) {
		log.info("remove...." + replyId);
		return mapper.delete(replyId);

	}

	@Override
	public List<ReviewReplyVO> getList(Criteria cri, Long reviewId) {
		log.info("get Reply List of a Board " + reviewId);
		return mapper.getListWithPaging(cri, reviewId);

	}

//	@Override
//	public ReplyPageDTO getListPage(Criteria cri, Long reviewId) {
//		return new ReplyPageDTO(mapper.getCountByreviewId(reviewId), mapper.getListWithPaging(cri, reviewId));
//	}
}
