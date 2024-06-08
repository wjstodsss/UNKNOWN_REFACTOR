package com.unknown.paldak.admin.mapper;

import com.unknown.paldak.admin.domain.OrderVO;

public interface AdminOrderMapper extends BaseMapper<OrderVO> {
	public OrderVO readByStringId(String id);
	public int deleteByStringId(String id);
	public int updateOrderState(OrderVO orderVO);
}

