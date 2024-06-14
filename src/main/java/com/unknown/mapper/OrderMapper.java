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
    /* 주문 상품 정보 */
    public OrderPageItemDTO getGoodsInfo(int itemId);
    
    /* 주문 상품 정보(주문 처리) */    
    public OrderItemDTO getOrderInfo(int itemId);
    
    /* 주문 테이블 등록 */
    public int enrollOrder(OrderDTO ord);
    
    /* 주문 아이템 테이블 등록 */
    public int enrollOrderItem(OrderItemDTO orid);
    
    /* 주문 재고 차감 */
    public int deductStock(ItemVO item);
    
    /* 주문 취소 */
    public int orderCancle(String orderId);
    
    /* 주문 상품 정보(주문 취소) */
    public List<OrderItemDTO> getOrderItemInfo(String orderId);
    
    /* 주문 내역 정보(주문 취소) */
    public OrderDTO getOrder(String orderId);
    
    /* 실시간 판매량 랭킹 */
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
    
    /* 멤버Id 기준으로 주문 정보 조회 */
    public List<OrderDTO> getOrdersByMemberId(@Param("memberId") String memberId);
    
    /* 멤버Id, 날짜 기준으로 주문 정보 조회 */
    public List<OrderDTO> getOrdersByMemberIdAndDateRange(
            @Param("memberId") String memberId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate);
    
    /* orderId 기준으로 주문 상품 정보 조회 */
    public List<OrderItemDTO> getOrderItemsByOrderId(@Param("orderId") String orderId);
    
    /* 멤버Id 기준으로 주문 상품 정보 조회 */
    public List<OrderItemDTO> getOrderItemsByMemberId(@Param("memberId") String memberId);
    
    /* 멤버Id, 주문상태 기준으로 주문 취소 정보 조회 */
    public List<OrderDTO> getCanceledOrdersByMemberIdAndDateRange(@Param("memberId") String memberId, @Param("startDate") String startDate, @Param("endDate") String endDate);
    
    @Select("SELECT i.itemid AS itemId, i.itemname AS itemName, i.itemprice AS itemPrice, i.itemdiscount AS itemDiscount, b.brandname AS brandName, SUM(o.itemcount) AS totalSales FROM tbl_item i JOIN tbl_orderitem o ON i.itemid = o.itemid LEFT JOIN tbl_brand b ON i.brandid = b.brandid GROUP BY i.itemid, i.itemname, i.itemprice, i.itemdiscount, b.brandname ORDER BY totalSales DESC LIMIT 4")
    public List<ItemSalesDTO> getTop4SellingItems();
}
