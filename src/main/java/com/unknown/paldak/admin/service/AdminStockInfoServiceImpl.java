package com.unknown.paldak.admin.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.domain.ItemVO;
import com.unknown.paldak.admin.domain.StockInfoVO;
import com.unknown.paldak.admin.mapper.AdminItemMapper;
import com.unknown.paldak.admin.mapper.AdminStockInfoMapper;
import com.unknown.paldak.admin.mapper.AdminStockLogMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@RequiredArgsConstructor
public class AdminStockInfoServiceImpl implements BaseService<StockInfoVO>{
    
	
	private final AdminStockInfoMapper mapper;
	private final AdminStockLogMapper logMapper;
	private final AdminItemMapper itemMapper;

	@Override
	public void register(StockInfoVO stockInfoVO) {
		mapper.insertSelectKey(stockInfoVO);
	}

	@Override
	public StockInfoVO get(Long itemId) {

		return mapper.read(itemId);
	}

	@Override
	public boolean modify(StockInfoVO stockInfoVO) {
		LocalDateTime now = LocalDateTime.now();
		Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
		stockInfoVO.setReceivedDate(date);
		return mapper.update(stockInfoVO)==1;
	}

	@Override
	public boolean remove(Long itemId) {
		log.info("remove ... " + itemId);
		return mapper.delete(itemId)==1;
	}

	@Override
	public List<StockInfoVO> getList(Criteria cri) {
		List<StockInfoVO> result = mapper.getListWithPaging(cri);
		
		return result;
	}
	
	@Override
	public List<StockInfoVO> getDescList(Criteria cri) {
		return mapper.getDescListWithPaging(cri);
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	
	public void stockOrderReg(StockInfoVO stockInfoVO) {
		mapper.insertSelectKey(stockInfoVO);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void receivedReg(StockInfoVO stockInfoVO) {
		// ???? ?��??? ??�????? ?��??�? ???? �????? �????? ???????��???? ??�????? ?��??????.
		long itemId = stockInfoVO.getItemId();
		long addQyt = stockInfoVO.getReceivedQty();
		
		// ??�??? ??�? ????("Y") �??��?��????.
		stockInfoVO.setIsReceived("Y");
		System.out.println(stockInfoVO);
		
		// ??�??��?? DB???��?��?��?? ?��??????.
		mapper.updateByStockOrderId(stockInfoVO);
	
		// ?????? ???? ?��??? �?�???�? ???? ?��?? ?????? 조�?????? ?��????? �????? ?��????.
		ItemVO itemVO = itemMapper.read(itemId);
		long currentItemStock = itemVO.getItemStock();
		
		// ??�????? �?�????? ?????? ?��? ?????? ??�??? ?��????��? ???��?��?? ????.
		itemVO.setItemStock(addQyt + currentItemStock);
		itemMapper.updateItemStock(itemVO);
	}
	
	
	public List<StockInfoVO> getLogList(Criteria cri) {
		List<StockInfoVO> result = logMapper.getListWithPaging(cri);
		return result;
	}
	
	
	public List<StockInfoVO> getLogDescList(Criteria cri) {
		return logMapper.getDescListWithPaging(cri);
	}
	
	public int getLogTotal(Criteria cri) {
		return logMapper.getTotalCount(cri);
	}
	
	
}
