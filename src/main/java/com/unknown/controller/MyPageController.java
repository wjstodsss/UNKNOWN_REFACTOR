package com.unknown.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unknown.model.AttachImageVO;
import com.unknown.model.ItemVO;
import com.unknown.model.MemberVO;
import com.unknown.model.OrderCancelDTO;
import com.unknown.model.OrderDTO;
import com.unknown.model.OrderItemDTO;
import com.unknown.service.AttachService;
import com.unknown.service.ItemService;
import com.unknown.service.MemberService;
import com.unknown.service.OrderService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final AttachService attachService;


    @GetMapping("/main")
    public String main(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("memberId");
        log.debug("Session memberId: {}", memberId);

        Integer adminCk = (Integer) session.getAttribute("adminCk");
        if (adminCk != null) {
            log.debug("Session adminCk: {}", adminCk);
            model.addAttribute("adminCk", adminCk);
        } else {
            log.debug("Session adminCk is not set");
            model.addAttribute("adminCk", 0); // 기본값 설정 (관리자가 아닌 경우)
        }

        return "mypage/main";
    }

    @GetMapping("/orderList")
    public String orderList(HttpSession session, @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate, Model model) {
        String memberId = (String) session.getAttribute("memberId");
        log.debug("Fetching orders for memberId: {}", memberId);

        if (memberId != null) {
            if (startDate == null || endDate == null) {
                LocalDate endDateDefault = LocalDate.now().plusDays(1); // 오늘 날짜의 다음 날로 설정
                LocalDate startDateDefault = endDateDefault.minusMonths(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                startDate = startDateDefault.format(formatter);
                endDate = endDateDefault.format(formatter);
            } else {
                LocalDate parsedEndDate = LocalDate.parse(endDate);
                parsedEndDate = parsedEndDate.plusDays(1); // 기존 endDate를 하루 뒤로 설정
                endDate = parsedEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }

            log.debug("Date range: {} to {}", startDate, endDate);

            List<OrderDTO> orders = orderService.getOrdersByMemberIdAndDateRange(memberId, startDate, endDate);
            log.debug("Orders fetched: {}", orders);

            if (orders != null) {
                for (OrderDTO order : orders) {
                    List<OrderItemDTO> orderItems = orderService.getOrderItemsByOrderId(order.getOrderId());
                    log.debug("OrderItems fetched for order {}: {}", order.getOrderId(), orderItems);

                    if (orderItems != null) {
                        order.setOrders(orderItems);
                        for (OrderItemDTO item : orderItems) {
                            if (item != null) {
                                List<AttachImageVO> imageList = attachService.getAttachList(item.getItemId());
                                log.debug("ImageList fetched for item {}: {}", item.getItemId(), imageList);

                                if (imageList != null && !imageList.isEmpty()) {
                                    item.setImagePath(imageList.get(0).getUploadPath() + "/"
                                            + imageList.get(0).getUuid() + "_" + imageList.get(0).getFileName());
                                }
                            }
                        }
                    }
                }
            }

            model.addAttribute("orders", orders);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
        }

        return "mypage/orderList";
    }

    
    /* 주문 취소 */
    @PostMapping("/cancelOrder")
    @ResponseBody
    public String cancelOrder(HttpSession session, @RequestParam String orderId) {
        String memberId = (String) session.getAttribute("memberId");
        log.debug("Cancelling order for memberId: {}, orderId: {}", memberId, orderId);

        if (memberId != null && orderId != null) {
            OrderCancelDTO orderCancelDTO = new OrderCancelDTO();
            orderCancelDTO.setMemberId(memberId);
            orderCancelDTO.setOrderId(orderId);
            
            orderService.orderCancle(orderCancelDTO);
            return "success";
        } else {
            return "fail";
        }
    }

    @GetMapping("/myPoint")
    public String myPoints(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("memberId");
        log.debug("Fetching points for memberId: {}", memberId);

        if (memberId != null) {
            MemberVO member = memberService.getMemberInfo(memberId);
            List<OrderDTO> orderList = orderService.getOrdersByMemberId(memberId);
            List<OrderItemDTO> orderItemList = orderService.getOrderItemsByMemberId(memberId);


            model.addAttribute("member", member);
            model.addAttribute("orderList", orderList);
            model.addAttribute("orderItemList", orderItemList);
        }

        return "mypage/myPoint";
    }
    
    @GetMapping("/recentItem")
    public String recentItem(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("memberId");
        log.debug("Fetching recent items for memberId: {}", memberId);

        if (memberId != null) {
            @SuppressWarnings("unchecked")
            List<ItemVO> recentItems = (List<ItemVO>) session.getAttribute("recentItems");
            model.addAttribute("recentItems", recentItems);
        }

        return "mypage/recentItem";
    }
    
    // 회원 정보 수정은 예외적으로 MemberController에 있음
    
    @GetMapping("/orangeMembers")
    public String orangeMembers() {
        return "mypage/orangeMembers";
    }
    
    @GetMapping("/address")
    public String address() {
        return "mypage/address";
    }
    
    @GetMapping("/cancelList")
    public String cancelList(HttpSession session, @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate, Model model) {
        String memberId = (String) session.getAttribute("memberId");
        log.debug("Fetching canceled orders for memberId: {}", memberId);

        if (memberId != null) {
            if (startDate == null || endDate == null) {
                LocalDate endDateDefault = LocalDate.now();
                LocalDate startDateDefault = endDateDefault.minusMonths(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                startDate = startDateDefault.format(formatter);
                endDate = endDateDefault.format(formatter);
            }

            List<OrderDTO> canceledOrders = orderService.getCanceledOrdersByMemberIdAndDateRange(memberId, startDate, endDate);
            log.debug("Canceled orders fetched: {}", canceledOrders);

            if (canceledOrders != null) {
                for (OrderDTO order : canceledOrders) {
                    List<OrderItemDTO> orderItems = orderService.getOrderItemsByOrderId(order.getOrderId());
                    log.debug("OrderItems fetched for order {}: {}", order.getOrderId(), orderItems);

                    if (orderItems != null) {
                        order.setOrders(orderItems);
                        for (OrderItemDTO item : orderItems) {
                            if (item != null) {
                                List<AttachImageVO> imageList = attachService.getAttachList(item.getItemId());
                                log.debug("ImageList fetched for item {}: {}", item.getItemId(), imageList);

                                if (imageList != null && !imageList.isEmpty()) {
                                    item.setImagePath(imageList.get(0).getUploadPath() + "/"
                                            + imageList.get(0).getUuid() + "_" + imageList.get(0).getFileName());
                                }
                            }
                        }
                    }
                }
            }

            model.addAttribute("orders", canceledOrders);
            model.addAttribute("startDate", startDate);
            model.addAttribute("endDate", endDate);
        }

        return "mypage/cancelList";
    }
}
