package com.unknown.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.mapper.AttachMapper;
import com.unknown.model.AttachImageVO;

import lombok.extern.log4j.Log4j;



@Service
@Log4j
public class AttachServiceImpl implements AttachService {
	@Autowired
	private AttachMapper attachMapper;

	/* �̹��� ������ ��ȯ */
	@Override
	public List<AttachImageVO> getAttachList(int itemId) {

		log.info("getAttachList.........");

		return attachMapper.getAttachList(itemId);
	}
}
