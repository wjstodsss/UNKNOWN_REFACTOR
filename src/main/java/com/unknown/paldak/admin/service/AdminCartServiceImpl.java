package com.unknown.paldak.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.CartVO;
import com.unknown.paldak.admin.mapper.AdminCartMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class AdminCartServiceImpl implements BaseServiceDefault<CartVO>{
    
	@Autowired
	private AdminCartMapper mapper;

	@Override
	public void register(CartVO cartVO) {
		mapper.insertSelectKey(cartVO);
		
	}

	@Override
	public CartVO get(Long cartId) {
		return mapper.read(cartId);
	}

	
	@Override
	public boolean modify(CartVO cartVO) {
		return mapper.update(cartVO)==1;
	}

	@Override
	public boolean remove(Long cartId) {
		return mapper.delete(cartId)==1;
	}

	@Override
	public List<CartVO> getList(Criteria cri) {
		return mapper.getListWithPaging(cri);
	}
	
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}

	@Override
    public PageDTO getPageMaker(Criteria cri) {
        int total = getTotal(cri);
        log.info("Creating PageDTO for criteria: " + cri + ", total: " + total);
        return new PageDTO(cri, total);
    }
	
}