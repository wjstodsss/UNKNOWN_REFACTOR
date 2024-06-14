package com.unknown.service;

import java.util.List;

import com.unknown.model.ItemSalesDTO;
import com.unknown.model.ItemVO;
import com.unknown.model.OrderCancelDTO;
import com.unknown.model.OrderDTO;
import com.unknown.model.OrderItemDTO;
import com.unknown.model.OrderPageItemDTO;

public interface OrderService {
    /* 주문 정보 */
    public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders);
    
    /* 주문 */
    public void order(OrderDTO orw);
    
    /* 주문 취소 */
    public void orderCancle(OrderCancelDTO dto);
    
    /* 실시간 판매량 랭킹 */
    public List<ItemSalesDTO> getTopSellingItems();
    
    /* 멤버Id 기준으로 주문 조회 */
    public List<OrderDTO> getOrdersByMemberId(String memberId);
    
    /* 멤버Id 기준으로 주문 상품 조회 */
    public List<OrderItemDTO> getOrderItemsByMemberId(String memberId);
    
    /* 멤버 Id, 날짜 기준 주문 조회 */
    public List<OrderDTO> getOrdersByMemberIdAndDateRange(String memberId, String startDate, String endDate);

    /* orderId 기준 주문 상품 정보 조회 */
    public List<OrderItemDTO> getOrderItemsByOrderId(String orderId);
    
    /* memberId 기준 취소 상품 정보 조회 */
    public List<OrderDTO> getCanceledOrdersByMemberIdAndDateRange(String memberId, String startDate, String endDate);
    
    public List<ItemVO> getBottomRankedItems();
    
}
