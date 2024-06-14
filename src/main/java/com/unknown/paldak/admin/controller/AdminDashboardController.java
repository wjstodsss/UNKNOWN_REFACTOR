package com.unknown.paldak.admin.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.unknown.paldak.admin.common.domain.Criteria;
import com.unknown.paldak.admin.domain.DashboardVO;
import com.unknown.paldak.admin.service.AdminDashboardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/dashboard/*")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;
    
    @GetMapping("/main")
	public String list(Criteria cri, Model model) {
    	DashboardVO registrations = dashboardService.getTodayRegistrations();
    	DashboardVO thisMonthRegistrations = dashboardService.getThisMonthRegistrations();
    	DashboardVO dailyOrderCount = dashboardService.getDailyOrderCount();
    	DashboardVO monthlyOrderCount = dashboardService.getMonthlyOrderCount();
    	Integer newItemsCount = dashboardService.getNewItemsCount();
    	Integer totalItemsCount = dashboardService.getTotalItemsCount();
    	Integer reviewCountToday = dashboardService.getReviewCountToday();
    	Integer pendingReviewCount = dashboardService.getPendingReviewCount();
    	Integer qnaCountToday = dashboardService.getQnaCountToday();
    	Integer pendingQnaCount = dashboardService.getPendingQnaCount();
    	Integer itemsNeedOrderCount = dashboardService.getItemsNeedOrderCount();
        Integer totalOrdersCount = dashboardService.getTotalOrdersCount();
        Integer receivedItemsCount = dashboardService.getReceivedItemsCount();
        Integer waitingItemsCount = dashboardService.getWaitingItemsCount();
        Integer todaySales = dashboardService.getTodaySales();
        Integer monthlySales = dashboardService.getMonthlySales();
        List<DashboardVO> todayTopFive = dashboardService.getTodayTopFive();
        
        List<DashboardVO> topFiveByReviewCount = dashboardService.getTopFiveByReviewCount();
       
    	model.addAttribute("todayRegistrations", registrations);
    	model.addAttribute("thisMonthRegistrations", thisMonthRegistrations);
    	model.addAttribute("dailyOrderCount", dailyOrderCount);
    	model.addAttribute("monthlyOrderCount", monthlyOrderCount);
    	model.addAttribute("newItemsCount", newItemsCount);
    	model.addAttribute("totalItemsCount", totalItemsCount);
    	model.addAttribute("reviewCountToday", reviewCountToday);
    	model.addAttribute("pendingReviewCount", pendingReviewCount);
    	model.addAttribute("qnaCountToday", qnaCountToday);
    	model.addAttribute("pendingQnaCount", pendingQnaCount);
    	model.addAttribute("itemsNeedOrderCount", itemsNeedOrderCount);
    	model.addAttribute("totalOrdersCount", totalOrdersCount);
    	model.addAttribute("receivedItemsCount", receivedItemsCount);
    	model.addAttribute("waitingItemsCount", waitingItemsCount);
    	model.addAttribute("todaySales", todaySales);
    	model.addAttribute("monthlySales", monthlySales);
    	model.addAttribute("todayTopFive", todayTopFive);
    	model.addAttribute("topFiveByReviewCount", topFiveByReviewCount);
        return "admin/dashboard";
	}
    
    
    @GetMapping("/todayRegistrations")
    public ResponseEntity<DashboardVO> getTodayRegistrations() {
        DashboardVO registrations = dashboardService.getTodayRegistrations();
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }

    @GetMapping("/thisMonthRegistrations")
    public ResponseEntity<DashboardVO> getThisMonthRegistrations() {
       DashboardVO registrations = dashboardService.getThisMonthRegistrations();
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }

    @GetMapping("/dailyOrderCoun")
    public ResponseEntity<DashboardVO> getDailyOrderCount() {
        DashboardVO registrations = dashboardService.getDailyOrderCount();
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }

    @GetMapping("/monthlyOrderCount")
    public ResponseEntity<DashboardVO> getMonthlyOrderCount() {
       DashboardVO registrations = dashboardService.getMonthlyOrderCount();
        return new ResponseEntity<>(registrations, HttpStatus.OK);
    }
   
}

