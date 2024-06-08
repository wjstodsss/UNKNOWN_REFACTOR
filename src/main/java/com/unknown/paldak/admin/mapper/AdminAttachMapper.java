package com.unknown.paldak.admin.mapper;


import com.unknown.paldak.admin.domain.AttachImageVO;

public interface AdminAttachMapper {
	public int insertImageInfo(AttachImageVO attachImageVO);
	public void deleteWithFile(long itemId);
	public int update(AttachImageVO attachImageVO);
}