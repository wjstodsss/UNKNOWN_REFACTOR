package com.unknown.paldak.admin.mapper;


import com.unknown.paldak.admin.domain.BenefitsReplyVO;

public interface AdminBenefitsReplyMapper extends BaseMapper<BenefitsReplyVO>{
	public BenefitsReplyVO readByBenefitsId(Long benefitsId);
}
