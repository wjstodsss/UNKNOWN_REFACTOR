package com.unknown.mapper;

import java.util.List;

import com.unknown.model.AttachImageVO;

public interface AttachMapper {

	/* �̹��� ������ ��ȯ */
	public List<AttachImageVO> getAttachList(int itemId);
}
