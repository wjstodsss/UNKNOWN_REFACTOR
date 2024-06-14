package com.unknown.service;

import java.util.List;

import com.unknown.model.ItemSalesDTO;
import com.unknown.model.ItemVO;
import com.unknown.model.OrderCancelDTO;
import com.unknown.model.OrderDTO;
import com.unknown.model.OrderItemDTO;
import com.unknown.model.OrderPageItemDTO;

public interface OrderService {
    /* �ֹ� ���� */
    public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders);
    
    /* �ֹ� */
    public void order(OrderDTO orw);
    
    /* �ֹ� ��� */
    public void orderCancle(OrderCancelDTO dto);
    
    /* �ǽð� �Ǹŷ� ��ŷ */
    public List<ItemSalesDTO> getTopSellingItems();
    
    /* ���Id �������� �ֹ� ��ȸ */
    public List<OrderDTO> getOrdersByMemberId(String memberId);
    
    /* ���Id �������� �ֹ� ��ǰ ��ȸ */
    public List<OrderItemDTO> getOrderItemsByMemberId(String memberId);
    
    /* ��� Id, ��¥ ���� �ֹ� ��ȸ */
    public List<OrderDTO> getOrdersByMemberIdAndDateRange(String memberId, String startDate, String endDate);

    /* orderId ���� �ֹ� ��ǰ ���� ��ȸ */
    public List<OrderItemDTO> getOrderItemsByOrderId(String orderId);
    
    /* memberId ���� ��� ��ǰ ���� ��ȸ */
    public List<OrderDTO> getCanceledOrdersByMemberIdAndDateRange(String memberId, String startDate, String endDate);
    
    public List<ItemVO> getBottomRankedItems();
    
}
