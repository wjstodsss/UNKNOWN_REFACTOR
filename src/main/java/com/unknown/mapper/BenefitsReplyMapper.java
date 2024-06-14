package com.unknown.mapper;

import java.util.List;

import com.unknown.model.BenefitsReplyVO;
import com.unknown.model.Criteria;

public interface BenefitsReplyMapper {
	public int insert(BenefitsReplyVO vo);

	public BenefitsReplyVO read(Long benefitsReplyId);

	public int remove(Long benefitsReplyId);

	public int getTotalCount(Criteria cri);

	public List<BenefitsReplyVO> getListWithPaging();
}
