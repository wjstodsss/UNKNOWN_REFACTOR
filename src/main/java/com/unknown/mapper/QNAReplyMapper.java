package com.unknown.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.unknown.model.Criteria;
import com.unknown.model.QNAReplyVO;


public interface QNAReplyMapper {
	public int insert(QNAReplyVO vo);
	public QNAReplyVO read(Long qnaReplyId);
	public int delete(Long qnaReplyId);
	//qnaReply, update만 수정가능
	public int update(QNAReplyVO qnaReply);
	
	 public List<QNAReplyVO> getListWithPaging(@Param("cri") Criteria cri, @Param("qnaId") Long qnaId);
	 
	 public int getCountByQNAReplyId(Long qnaId);
}
