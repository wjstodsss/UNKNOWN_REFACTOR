package com.unknown.paldak.admin.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
import com.unknown.paldak.admin.domain.OrderVO;
import com.unknown.paldak.admin.mapper.AdminOrderMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class AdminOrderServiceImpl implements BaseService<OrderVO>{
    
	@Autowired
	private AdminOrderMapper mapper;

	@Override
	public void register(OrderVO orderVO) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("_yyyyMMddmm");
		String orderId = orderVO.getMemberId() + format.format(date);
		orderVO.setOrderId(orderId);
		mapper.insert(orderVO);	
	}

	@Override
	public OrderVO get(Long orderId) {
		return mapper.read(orderId);
	}

	@Override
	public boolean modify(OrderVO orderVO) {
		return mapper.update(orderVO)==1;
	}

	@Override
	public boolean remove(Long orderId) {
		return mapper.delete(orderId)==1;
	}

	@Override
	public List<OrderVO> getList(Criteria cri) {
		System.out.println(cri);
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public List<OrderVO> getDescList(Criteria cri) {
		return mapper.getDescListWithPaging(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	
	public OrderVO getByStringId(String orderId) {
		return mapper.readByStringId(orderId);
	}
	
	public boolean removeByStringId(String orderId) {
		return mapper.deleteByStringId(orderId)==1;
	}
	
	public boolean modifyOrderState(OrderVO orderVO) {
		int result = mapper.updateOrderState(orderVO);
		return result==1;
	}
	
	@Override
    public PageDTO getPageMaker(Criteria cri) {
        int total = getTotal(cri);
        log.info("Creating PageDTO for criteria: " + cri + ", total: " + total);
        return new PageDTO(cri, total);
    }

	
}
