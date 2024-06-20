package com.unknown.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unknown.model.AttachImageVO;
import com.unknown.model.Criteria;
import com.unknown.model.ItemVO;
import com.unknown.model.MemberVO;
import com.unknown.model.OrderCancelDTO;
import com.unknown.model.OrderDTO;
import com.unknown.model.OrderItemDTO;
import com.unknown.model.QNAVO;
import com.unknown.model.ReviewVO;
import com.unknown.service.AttachService;
import com.unknown.service.MemberService;
import com.unknown.service.OrderService;
import com.unknown.service.QNAService;
import com.unknown.service.ReviewService;

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
    private final QNAService qnaService;
    private final ReviewService reviewService;


    @GetMapping("/main")
    public String main(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("memberId");
        log.debug("Session memberId: {}", memberId);

        if (memberId != null) {
            // �ֱ� 3���� �ֹ� ����
            LocalDate endDate = LocalDate.now();
            LocalDate startDate = endDate.minusMonths(3);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String startDateStr = startDate.format(formatter);
            String endDateStr = endDate.format(formatter);
            List<OrderDTO> recentOrders = orderService.getOrdersByMemberIdAndDateRange(memberId, startDateStr, endDateStr);
            model.addAttribute("recentOrders", recentOrders);

            // �ֱ� �� ��ǰ
            @SuppressWarnings("unchecked")
            List<ItemVO> recentItems = (List<ItemVO>) session.getAttribute("recentItems");
            model.addAttribute("recentItems", recentItems);

            // ���� ����
            List<ReviewVO> reviewList = reviewService.getListByMemberId(memberId, new Criteria(1, 5)); // �ֱ� 5�� ���� ��������
            model.addAttribute("reviewList", reviewList);

            // Q&A ����
            List<QNAVO> qnaList = qnaService.getListByMemberId(memberId, new Criteria(1, 5)); // �ֱ� 5�� Q&A ��������
            model.addAttribute("qnaList", qnaList);

            // ȸ�� ����
            MemberVO member = memberService.getMemberInfo(memberId);
            model.addAttribute("member", member);
        }

        return "mypage/main";
    }

    @GetMapping("/orderList")
    public String orderList(HttpSession session, @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate, Model model) {
        String memberId = (String) session.getAttribute("memberId");
        log.debug("Fetching orders for memberId: {}", memberId);

        if (memberId != null) {
            int selectedRange = 1; // �⺻�� ����
            if (startDate == null || endDate == null) {
                LocalDate endDateDefault = LocalDate.now();
                LocalDate startDateDefault = endDateDefault.minusMonths(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                startDate = startDateDefault.format(formatter);
                endDate = endDateDefault.format(formatter);
            } else {
                LocalDate parsedEndDate = LocalDate.parse(endDate);
                endDate = parsedEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate startDateParsed = LocalDate.parse(startDate);
                selectedRange = (int) ChronoUnit.MONTHS.between(startDateParsed, parsedEndDate);
            }

            LocalDate realEndDate = LocalDate.parse(endDate).plusDays(1);
            String realEndDateStr = realEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            List<OrderDTO> orders = orderService.getOrdersByMemberIdAndDateRange(memberId, startDate, realEndDateStr);
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
            model.addAttribute("endDate", endDate); // Ŭ���̾�Ʈ�� ������ endDate
            model.addAttribute("realEndDate", realEndDateStr); // ���� ���͸��� ����� endDate
            model.addAttribute("selectedRange", selectedRange); // ���õ� �Ⱓ
        }

        return "mypage/orderList";
    }
    
    /* �ֹ� ��� */
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

            // ����Ʈ ���� ������ ������ ����Ʈ
            List<Map<String, Object>> pointHistoryList = new ArrayList<>();

            // ��� ����Ʈ ���� �߰�
            for (OrderDTO order : orderList) {
                if (order.getUsePoint() != 0) {
                    Map<String, Object> history = new HashMap<>();
                    history.put("date", order.getOrderDate());
                    history.put("description", "��� ����Ʈ");
                    history.put("type", "����");
                    history.put("amount", -order.getUsePoint());
                    history.put("expirationDate", order.getOrderDate());
                    pointHistoryList.add(history);
                }
            }

            // ���� ����Ʈ ���� �߰�
            for (OrderItemDTO orderItem : orderItemList) {
                Map<String, Object> history = new HashMap<>();
                history.put("date", orderItem.getOrderDate());
                history.put("description", "���� ����Ʈ");
                history.put("type", "����");
                history.put("amount", orderItem.getEarnPoint());
                history.put("expirationDate", orderItem.getOrderDate());
                pointHistoryList.add(history);
            }

            // ȸ������ ����Ʈ ���� �߰�
            Map<String, Object> signupHistory = new HashMap<>();
            signupHistory.put("date", member.getRegDate());
            signupHistory.put("description", "ȸ������ ����Ʈ");
            signupHistory.put("type", "����");
            signupHistory.put("amount", 5000);
            signupHistory.put("expirationDate", member.getRegDate());
            pointHistoryList.add(signupHistory);

            // ��¥�� �������� �������� ����
            pointHistoryList.sort((p1, p2) -> ((Date) p2.get("date")).compareTo((Date) p1.get("date")));

            model.addAttribute("member", member);
            model.addAttribute("pointHistoryList", pointHistoryList);
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
    
    // ȸ�� ���� ������ ���������� MemberController�� ����
    
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
            int selectedRange = 1; // �⺻�� ����
            if (startDate == null || endDate == null) {
                LocalDate endDateDefault = LocalDate.now();
                LocalDate startDateDefault = endDateDefault.minusMonths(1);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                startDate = startDateDefault.format(formatter);
                endDate = endDateDefault.format(formatter);
            } else {
                LocalDate parsedEndDate = LocalDate.parse(endDate);
                endDate = parsedEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalDate startDateParsed = LocalDate.parse(startDate);
                selectedRange = (int) ChronoUnit.MONTHS.between(startDateParsed, parsedEndDate);
            }

            LocalDate realEndDate = LocalDate.parse(endDate).plusDays(1);
            String realEndDateStr = realEndDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            List<OrderDTO> canceledOrders = orderService.getCanceledOrdersByMemberIdAndDateRange(memberId, startDate, realEndDateStr);
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
            model.addAttribute("endDate", endDate); // Ŭ���̾�Ʈ�� ������ endDate
            model.addAttribute("realEndDate", realEndDateStr); // ���� ���͸��� ����� endDate
            model.addAttribute("selectedRange", selectedRange); // ���õ� �Ⱓ
        }

        return "mypage/cancelList";
    }


}
