package com.unknown.paldak.admin.service;

import com.unknown.paldak.admin.domain.AttachImageVO;

public interface AdminAttachService {
	
	public int register(AttachImageVO attachImageVO);
	public boolean remove(long itemId);
	public boolean modify(AttachImageVO attachImageVO);
}
