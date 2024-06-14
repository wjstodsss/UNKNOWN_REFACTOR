package com.unknown.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Arg;
import org.apache.ibatis.annotations.ConstructorArgs;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.unknown.model.ItemSalesDTO;
import com.unknown.model.ItemVO;
import com.unknown.model.OrderDTO;
import com.unknown.model.OrderItemDTO;
import com.unknown.model.OrderPageItemDTO;

public interface OrderMapper {
    /* �ֹ� ��ǰ ���� */
    public OrderPageItemDTO getGoodsInfo(int itemId);
    
    /* �ֹ� ��ǰ ����(�ֹ� ó��) */    
    public OrderItemDTO getOrderInfo(int itemId);
    
    /* �ֹ� ���̺� ��� */
    public int enrollOrder(OrderDTO ord);
    
    /* �ֹ� ������ ���̺� ��� */
    public int enrollOrderItem(OrderItemDTO orid);
    
    /* �ֹ� ��� ���� */
    public int deductStock(ItemVO item);
    
    /* �ֹ� ��� */
    public int orderCancle(String orderId);
    
    /* �ֹ� ��ǰ ����(�ֹ� ���) */
    public List<OrderItemDTO> getOrderItemInfo(String orderId);
    
    /* �ֹ� ���� ����(�ֹ� ���) */
    public OrderDTO getOrder(String orderId);
    
    /* �ǽð� �Ǹŷ� ��ŷ */
    @Select("SELECT i.itemid AS itemId, i.itemname AS itemName, i.itemprice AS itemPrice, i.itemdiscount AS itemDiscount, b.brandname AS brandName, SUM(o.itemcount) AS totalSales FROM tbl_item i JOIN tbl_orderitem o ON i.itemid = o.itemid LEFT JOIN tbl_brand b ON i.brandid = b.brandid GROUP BY i.itemid, i.itemname, i.itemprice, i.itemdiscount, b.brandname ORDER BY totalSales DESC")
    @ConstructorArgs({
        @Arg(column = "itemId", javaType = int.class),
        @Arg(column = "itemName", javaType = String.class),
        @Arg(column = "itemPrice", javaType = int.class),
        @Arg(column = "itemDiscount", javaType = double.class),
        @Arg(column = "brandName", javaType = String.class),
        @Arg(column = "totalSales", javaType = int.class)
    })
    public List<ItemSalesDTO> getTopSellingItems();
    
    /* ���Id �������� �ֹ� ���� ��ȸ */
    public List<OrderDTO> getOrdersByMemberId(@Param("memberId") String memberId);
    
    /* ���Id, ��¥ �������� �ֹ� ���� ��ȸ */
    public List<OrderDTO> getOrdersByMemberIdAndDateRange(
            @Param("memberId") String memberId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);
    
    /* orderId �������� �ֹ� ��ǰ ���� ��ȸ */
    public List<OrderItemDTO> getOrderItemsByOrderId(@Param("orderId") String orderId);
    
    /* ���Id �������� �ֹ� ��ǰ ���� ��ȸ */
    public List<OrderItemDTO> getOrderItemsByMemberId(@Param("memberId") String memberId);
    
    /* ���Id, �ֹ����� �������� �ֹ� ��� ���� ��ȸ */
    public List<OrderDTO> getCanceledOrdersByMemberIdAndDateRange(@Param("memberId") String memberId, @Param("startDate") String startDate, @Param("endDate") String endDate);
    
    @Select("SELECT i.itemid AS itemId, i.itemname AS itemName, i.itemprice AS itemPrice, i.itemdiscount AS itemDiscount, b.brandname AS brandName, SUM(o.itemcount) AS totalSales FROM tbl_item i JOIN tbl_orderitem o ON i.itemid = o.itemid LEFT JOIN tbl_brand b ON i.brandid = b.brandid GROUP BY i.itemid, i.itemname, i.itemprice, i.itemdiscount, b.brandname ORDER BY totalSales DESC LIMIT 4")
    public List<ItemSalesDTO> getTop4SellingItems();
}
