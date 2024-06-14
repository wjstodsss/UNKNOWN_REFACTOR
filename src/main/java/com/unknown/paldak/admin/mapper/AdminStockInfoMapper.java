package com.unknown.paldak.admin.mapper;

import com.unknown.paldak.admin.domain.StockInfoVO;

public interface AdminStockInfoMapper extends BaseMapper<StockInfoVO> {

	void updateByStockOrderId(StockInfoVO stockInfoVO);
}
