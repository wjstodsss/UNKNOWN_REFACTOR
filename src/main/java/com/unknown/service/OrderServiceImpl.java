package com.unknown.service;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

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

        List<OrderPageItemDTO> result = new ArrayList<OrderPageItemDTO>();

        for (OrderPageItemDTO ord : orders) {

            OrderPageItemDTO goodsInfo = orderMapper.getGoodsInfo(ord.getItemId());

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
        
        // receiver �� ����
        if(ord.getReceiver() == null || ord.getReceiver().isEmpty()) {
            ord.setReceiver(ord.getMemberId()); // memberId�� ����
        }

        /* ����� �����Ͱ������� */
        /* �ֹ� ���� */
        List<OrderItemDTO> ords = new ArrayList<>();
        for (OrderItemDTO oit : ord.getOrders()) {
            OrderItemDTO orderItem = orderMapper.getOrderInfo(oit.getItemId());
            // ���� ����
            orderItem.setItemCount(oit.getItemCount());
            // �⺻���� ����
            orderItem.initSaleTotal();
            // List��ü �߰�
            ords.add(orderItem);
        }
        /* OrderDTO ���� */
        ord.setOrders(ords);
        ord.getOrderPriceInfo();

        /* DB �ֹ�,�ֹ���ǰ(,�������) �ֱ� */

        /* orderId����� �� OrderDTO��ü orderId�� ���� */
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("_yyyyMMddmm");
        String orderId = ord.getMemberId() + format.format(date);
        ord.setOrderId(orderId);

        /* db�ֱ� */
        orderMapper.enrollOrder(ord); // tbl_order ���
        for (OrderItemDTO oit : ord.getOrders()) { // tbl_orderItem ���
            oit.setOrderId(orderId);
            orderMapper.enrollOrderItem(oit);
        }

        /* ��� ���� ���� */
        for (OrderItemDTO oit : ord.getOrders()) {
            /* ���� ��� �� ���ϱ� */
            ItemVO item = itemMapper.getGoodsInfo(oit.getItemId());
            item.setItemStock(item.getItemStock() - oit.getItemCount());
            /* ���� �� DB ���� */
            orderMapper.deductStock(item);
        }

        /* ��ٱ��� ���� */
        for (OrderItemDTO oit : ord.getOrders()) {
            CartDTO dto = new CartDTO();
            dto.setMemberId(ord.getMemberId());
            dto.setItemId(oit.getItemId());

            cartMapper.deleteOrderCart(dto);
        }

    }

    /* �ֹ���� */
    @Override
    @Transactional
    public void orderCancle(OrderCancelDTO dto) {
        
        /* �ֹ�, �ֹ���ǰ ��ü */
        /*ȸ��*/
            MemberVO member = memberMapper.getMemberInfo(dto.getMemberId());
        /*�ֹ���ǰ*/
            List<OrderItemDTO> ords = orderMapper.getOrderItemInfo(dto.getOrderId());
            for(OrderItemDTO ord : ords) {
                ord.initSaleTotal();
            }
        /* �ֹ� */
            OrderDTO orw = orderMapper.getOrder(dto.getOrderId());
            orw.setOrders(ords);
            
            orw.getOrderPriceInfo();
            
    /* �ֹ���ǰ ��� DB */
            orderMapper.orderCancle(dto.getOrderId());
            
    /* ����Ʈ, ��� ��ȯ */
            /* ����Ʈ */
            int calPoint = member.getPoint();
            calPoint = calPoint + orw.getUsePoint() - orw.getOrderEarnPoint();
            member.setPoint(calPoint);
            
     
            /* ��� */
            for(OrderItemDTO ord : orw.getOrders()) {
                ItemVO item = itemMapper.getGoodsInfo(ord.getItemId());
                item.setItemStock(item.getItemStock() + ord.getItemCount());
                orderMapper.deductStock(item);
            }

    }
    
    @Override
    @Transactional
    public List<ItemSalesDTO> getTopSellingItems() {
        List<ItemSalesDTO> items = orderMapper.getTopSellingItems();
        System.out.println(items + "kkkkkkkkkkkkkkk");
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
