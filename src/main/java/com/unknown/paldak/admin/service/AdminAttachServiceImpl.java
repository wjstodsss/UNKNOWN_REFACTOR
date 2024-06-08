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
		System.out.println("1616541666666666666668165185dddddddddddddddddddddddddd16");
		return attachMapper.insertImageInfo(attachImageVO);
	}

	@Override
	public boolean modify(AttachImageVO attachImageVO) {
		
		if(attachMapper.update(attachImageVO) == 0) {
			System.out.println("161654166666666666666816518516");
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
