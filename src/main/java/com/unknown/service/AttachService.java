package com.unknown.service;

import java.util.List;

import com.unknown.model.AttachImageVO;

public interface AttachService {
	/* �̹��� ������ ��ȯ */
	public List<AttachImageVO> getAttachList(int bookId);
}
