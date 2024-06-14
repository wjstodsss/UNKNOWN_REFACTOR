package com.unknown.paldak.admin.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.paldak.admin.domain.AttachImageVO;
import com.unknown.paldak.admin.mapper.AdminAttachMapper;



@Service
public class AdminAttachServiceImpl implements AdminAttachService {
	@Autowired
	private AdminAttachMapper attachMapper;

	@Override
	public int register(AttachImageVO attachImageVO) {
		return attachMapper.insertImageInfo(attachImageVO);
	}

	@Override
	public boolean modify(AttachImageVO attachImageVO) {
		
		if(attachMapper.update(attachImageVO) == 0) {
			register(attachImageVO);
		}
		return true;
	}

	@Override
	public boolean remove(long itemId) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
