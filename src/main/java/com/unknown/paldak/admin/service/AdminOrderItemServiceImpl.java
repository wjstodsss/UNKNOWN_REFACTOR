package com.unknown.paldak.admin.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.domain.OrderItemVO;
import com.unknown.paldak.admin.mapper.AdminOrderItemMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminOrderItemServiceImpl implements BaseService<OrderItemVO>{
    
	@Autowired
	private AdminOrderItemMapper mapper;


	@Override
	public List<OrderItemVO> getList(Criteria cri) {
		System.out.println(cri);
		return mapper.getListWithPaging(cri);
	}
	
	@Override
	public List<OrderItemVO> getDescList(Criteria cri) {
		return mapper.getDescListWithPaging(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}

	@Override
	public void register(OrderItemVO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public OrderItemVO get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean modify(OrderItemVO t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Long id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
