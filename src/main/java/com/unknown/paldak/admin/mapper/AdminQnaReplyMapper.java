package com.unknown.paldak.admin.mapper;


import com.unknown.paldak.admin.domain.QNAReplyVO;

public interface AdminQnaReplyMapper extends BaseMapper<QNAReplyVO>{
	public QNAReplyVO readByQnaId(Long qnaId);
	
}
