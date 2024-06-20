package com.unknown.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unknown.mapper.AttachMapper;
import com.unknown.mapper.CartMapper;
import com.unknown.mapper.ItemMapper;
import com.unknown.mapper.MemberMapper;
import com.unknown.mapper.OrderMapper;
import com.unknown.model.AttachImageVO;
import com.unknown.model.CartDTO;
import com.unknown.model.ItemSalesDTO;
import com.unknown.model.ItemVO;
import com.unknown.model.MemberVO;
import com.unknown.model.OrderCancelDTO;
import com.unknown.model.OrderDTO;
import com.unknown.model.OrderItemDTO;
import com.unknown.model.OrderPageItemDTO;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AttachMapper attachMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Override
    public List<OrderPageItemDTO> getGoodsInfo(List<OrderPageItemDTO> orders) {
        List<OrderPageItemDTO> result = new ArrayList<>();
        for (OrderPageItemDTO ord : orders) {
            System.out.println("Processing itemId: " + ord.getItemId()); // 로그 추가
            if (ord.getItemId() == 0) {
                System.out.println("Invalid itemId: " + ord.getItemId()); // 로그 추가
                continue;
            }
            OrderPageItemDTO goodsInfo = orderMapper.getGoodsInfo(ord.getItemId());
            if (goodsInfo == null) {
                System.out.println("No goodsInfo found for itemId: " + ord.getItemId()); // 로그 추가
                continue; // NullPointerException 방지
            }
            goodsInfo.setItemCount(ord.getItemCount());
            goodsInfo.initSaleTotal();
            List<AttachImageVO> imageList = attachMapper.getAttachList(goodsInfo.getItemId());
            goodsInfo.setImageList(imageList);
            result.add(goodsInfo);
        }
        return result;
    }


    @Override
    @Transactional
    public void order(OrderDTO ord) {
        // receiver 값 설정
        if (ord.getReceiver() == null || ord.getReceiver().isEmpty()) {
            ord.setReceiver(ord.getMemberId()); // memberId로 설정
        }

        /* 사용할 데이터가져오기 */
        /* 주문 정보 */
        List<OrderItemDTO> ords = new ArrayList<>();
        for (OrderItemDTO oit : ord.getOrders()) {
            OrderItemDTO orderItem = orderMapper.getOrderInfo(oit.getItemId());
            // 수량 셋팅
            orderItem.setItemCount(oit.getItemCount());
            // 기본정보 셋팅
            orderItem.initSaleTotal();
            // List객체 추가
            ords.add(orderItem);
        }
        /* OrderDTO 셋팅 */
        ord.setOrders(ords);
        ord.getOrderPriceInfo();

        // Log OrderDTO details
        System.out.println("OrderDTO: " + ord.toString());

        // Check if mandatory fields are set
        if (ord.getMemberAddr1() == null || ord.getMemberAddr1().isEmpty()) {
            throw new IllegalArgumentException("MemberAddr1 is missing!");
        }

        /* DB 주문,주문상품(,배송정보) 넣기 */
        /* orderId만들기 및 OrderDTO객체 orderId에 저장 */
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("_yyyyMMddmm");
        String orderId = ord.getMemberId() + format.format(date);
        ord.setOrderId(orderId);

        /* db넣기 */
        orderMapper.enrollOrder(ord); // tbl_order 등록
        for (OrderItemDTO oit : ord.getOrders()) { // tbl_orderItem 등록
            oit.setOrderId(orderId);
            orderMapper.enrollOrderItem(oit);
        }

        /* 재고 변동 적용 */
        for (OrderItemDTO oit : ord.getOrders()) {
            /* 변동 재고 값 구하기 */
            ItemVO item = itemMapper.getGoodsInfo(oit.getItemId());
            item.setItemStock(item.getItemStock() - oit.getItemCount());
            /* 변동 값 DB 적용 */
            orderMapper.deductStock(item);
        }

        /* 장바구니 제거 */
        for (OrderItemDTO oit : ord.getOrders()) {
            CartDTO dto = new CartDTO();
            dto.setMemberId(ord.getMemberId());
            dto.setItemId(oit.getItemId());
            cartMapper.deleteOrderCart(dto);
        }
    }

    /* 주문취소 */
    @Override
    @Transactional
    public void orderCancle(OrderCancelDTO dto) {
        /* 주문, 주문상품 객체 */
        /*회원*/
        MemberVO member = memberMapper.getMemberInfo(dto.getMemberId());
        /*주문상품*/
        List<OrderItemDTO> ords = orderMapper.getOrderItemInfo(dto.getOrderId());
        for (OrderItemDTO ord : ords) {
            ord.initSaleTotal();
        }
        /* 주문 */
        OrderDTO orw = orderMapper.getOrder(dto.getOrderId());
        orw.setOrders(ords);
        orw.getOrderPriceInfo();

        /* 주문상품 취소 DB */
        orderMapper.orderCancle(dto.getOrderId());

        /* 포인트, 재고 변환 */
        /* 포인트 */
        int calPoint = member.getPoint();
        calPoint = calPoint + orw.getUsePoint() - orw.getOrderEarnPoint();
        member.setPoint(calPoint);

        /* 재고 */
        for (OrderItemDTO ord : orw.getOrders()) {
            ItemVO item = itemMapper.getGoodsInfo(ord.getItemId());
            item.setItemStock(item.getItemStock() + ord.getItemCount());
            orderMapper.deductStock(item);
        }
    }

    @Override
    @Transactional
    public List<ItemSalesDTO> getTopSellingItems() {
        List<ItemSalesDTO> items = orderMapper.getTopSellingItems();
        for (ItemSalesDTO item : items) {
            List<AttachImageVO> imageList = attachMapper.getAttachList(item.getItemId());
            item.setImageList(imageList);
        }
        return items;
    }

    @Override
    public List<OrderDTO> getOrdersByMemberId(String memberId) {
        return orderMapper.getOrdersByMemberId(memberId);
    }

    @Override
    public List<OrderItemDTO> getOrderItemsByMemberId(String memberId) {
        return orderMapper.getOrderItemsByMemberId(memberId);
    }

    @Override
    public List<OrderDTO> getOrdersByMemberIdAndDateRange(String memberId, String startDate, String endDate) {
        return orderMapper.getOrdersByMemberIdAndDateRange(memberId, startDate, endDate);
    }

    @Override
    public List<OrderItemDTO> getOrderItemsByOrderId(String orderId) {
        return orderMapper.getOrderItemsByOrderId(orderId);
    }

    @Override
    public List<OrderDTO> getCanceledOrdersByMemberIdAndDateRange(String memberId, String startDate, String endDate) {
        return orderMapper.getCanceledOrdersByMemberIdAndDateRange(memberId, startDate, endDate);
    }

    @Override
    public List<ItemVO> getBottomRankedItems() {
        return itemMapper.getBottomRankedItems();
    }
}