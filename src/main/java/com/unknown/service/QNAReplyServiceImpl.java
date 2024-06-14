package com.unknown.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unknown.model.Criteria;
import com.unknown.model.QNAReplyVO;
import com.unknown.mapper.QNAReplyMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
@AllArgsConstructor
public class QNAReplyServiceImpl implements QNAReplyService {
	private QNAReplyMapper mapper;

	@Override
	public int register(QNAReplyVO vo) {
		log.info("register......" + vo);
		return mapper.insert(vo);

	}

	@Override
	public QNAReplyVO get(Long replyId) {
		log.info("get......" + replyId);
		return mapper.read(replyId);

	}

	@Override
	public int modify(QNAReplyVO vo) {
		log.info("modify......" + vo);
		return mapper.update(vo);

	}

	@Override
	public int remove(Long replyId) {
		log.info("remove...." + replyId);
		return mapper.delete(replyId);

	}

	@Override
	public List<QNAReplyVO> getList(Criteria cri, Long qnaId) {
		log.info("get QNAReply List of a Board " + qnaId);
		return mapper.getListWithPaging(cri, qnaId);

	}

//	@Override
//	public QNAReplyPageDTO getListPage(Criteria cri, Long qnaId) {
//		return new QNAReplyPageDTO(mapper.getCountByqnaId(qnaId), mapper.getListWithPaging(cri, qnaId));
//	}
}
