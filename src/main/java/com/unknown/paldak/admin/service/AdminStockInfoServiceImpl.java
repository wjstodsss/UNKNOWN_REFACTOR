package com.unknown.paldak.admin.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.common.domain.PageDTO;
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
public class AdminStockInfoServiceImpl implements BaseServiceDefault<StockInfoVO> {
    
    private final AdminStockInfoMapper mapper;
    private final AdminStockLogMapper logMapper;
    private final AdminItemMapper itemMapper;

    @Override
    public void register(StockInfoVO stockInfoVO) {
        log.info("Registering stock info: " + stockInfoVO);
        mapper.insertSelectKey(stockInfoVO);
    }

    @Override
    public StockInfoVO get(Long itemId) {
        log.info("Fetching stock info for item ID: " + itemId);
        return mapper.read(itemId);
    }

    @Override
    public boolean modify(StockInfoVO stockInfoVO) {
        LocalDateTime now = LocalDateTime.now();
        Date date = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        stockInfoVO.setReceivedDate(date);
        boolean result = mapper.update(stockInfoVO) == 1;
        log.info("Modifying stock info: " + stockInfoVO + ", result: " + result);
        return result;
    }

    @Override
    public boolean remove(Long itemId) {
        log.info("Removing stock info for item ID: " + itemId);
        boolean result = mapper.delete(itemId) == 1;
        log.info("Remove result: " + result);
        return result;
    }

    @Override
    public List<StockInfoVO> getList(Criteria cri) {
        List<StockInfoVO> result = mapper.getListWithPaging(cri);
        log.info("Fetching stock list with criteria: " + cri + ", result size: " + result.size());
        return result;
    }

    @Override
    public int getTotal(Criteria cri) {
        int total = mapper.getTotalCount(cri);
        log.info("Fetching total count with criteria: " + cri + ", total: " + total);
        return total;
    }
    
    @Override
    public PageDTO getPageMaker(Criteria cri) {
        int total = getTotal(cri);
        log.info("Creating PageDTO for criteria: " + cri + ", total: " + total);
        return new PageDTO(cri, total);
    }
    
    public void stockOrderReg(StockInfoVO stockInfoVO) {
        log.info("Registering stock order: " + stockInfoVO);
        mapper.insertSelectKey(stockInfoVO);
    }

    @Transactional(rollbackFor = Exception.class)
    public void receivedReg(StockInfoVO stockInfoVO) {
        log.info("Processing received registration for stock info: " + stockInfoVO);

        try {
            long itemId = stockInfoVO.getItemId();
            long addQty = stockInfoVO.getReceivedQty();

            stockInfoVO.setIsReceived("Y");
            log.info("Updating stock order info: " + stockInfoVO);

            mapper.updateByStockOrderId(stockInfoVO);

            ItemVO itemVO = itemMapper.read(itemId);
            if (itemVO == null) {
                throw new RuntimeException("Item not found for ID: " + itemId);
            }

            long currentItemStock = itemVO.getItemStock();
            itemVO.setItemStock(addQty + currentItemStock);
            itemMapper.updateItemStock(itemVO);
        } catch (Exception e) {
            log.error("Error processing received registration: " + stockInfoVO, e);
            throw e;
        }
    }

    public List<StockInfoVO> getLogList(Criteria cri) {
        return logMapper.getListWithPaging(cri);
    }


    public int getLogTotal(Criteria cri) {
        return logMapper.getTotalCount(cri);
    }

    public void processStockOrder(StockInfoVO stockInfoVO, String registerType) {
        log.info("Processing stock order. Type: " + registerType + ", StockInfoVO: " + stockInfoVO);
        if ("stockOrderReg".equals(registerType)) {
            stockOrderReg(stockInfoVO);
        } else {
            receivedReg(stockInfoVO);
        }
    }
    
    public List<StockInfoVO> getStockList(Criteria cri) {    	
        List<StockInfoVO> list = getList(cri);
        log.info("Stock list retrieved: " + list.size() + " items");
        return list;    
    }
}
