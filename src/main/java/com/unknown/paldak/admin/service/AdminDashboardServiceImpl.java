package com.unknown.paldak.admin.service;

import com.unknown.paldak.admin.domain.DashboardVO;
import com.unknown.paldak.admin.mapper.AdminDashboardMapper;
import com.unknown.paldak.admin.util.AdminUploadPathConfig;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDashboardServiceImpl implements AdminDashboardService {

    private final AdminDashboardMapper dashboardMapper;

    @Override
    public DashboardVO getTodayRegistrations() {
    	System.out.println("3");
    	DashboardVO result = dashboardMapper.getTodayRegistrations();
    	System.out.println("3-1");
        return result;
    }

    @Override
    public DashboardVO getThisMonthRegistrations() {
        return dashboardMapper.getThisMonthRegistrations();
    }
    

    @Override
    public DashboardVO getDailyOrderCount() {
    	System.out.println("3");
    	DashboardVO result = dashboardMapper.getDailyOrderCount();
    	System.out.println("3-1");
        return result;
    }

    @Override
    public DashboardVO getMonthlyOrderCount() {
    	System.out.println("4");
    	DashboardVO result = dashboardMapper.getMonthlyOrderCount();
    	System.out.println("4-1");
        return result; 
    }
      

	@Override
	public int getNewItemsCount() {
		return dashboardMapper.getNewItemsCount();
	}

	@Override
	public int getTotalItemsCount() {
		return dashboardMapper.getTotalItemsCount();
	}

	
	@Override
	public int getReviewCountToday() {
		return dashboardMapper.getReviewCountToday();
	}

	@Override
	public int getPendingReviewCount() {
		return dashboardMapper.getPendingReviewCount();
	}

	@Override
	public int getQnaCountToday() {
		return dashboardMapper.getQnaCountToday();
	}

	@Override
	public int getPendingQnaCount() {
		return dashboardMapper.getPendingQnaCount();
	}

	
	@Override
	public int getItemsNeedOrderCount() {
		return dashboardMapper.getItemsNeedOrderCount();
	}

	@Override
	public int getTotalOrdersCount() {
		return dashboardMapper.getTotalOrdersCount();
	}

	@Override
	public int getReceivedItemsCount() {
		return dashboardMapper.getReceivedItemsCount();
	}

	@Override
	public int getWaitingItemsCount() {		
		return dashboardMapper.getWaitingItemsCount();
	}
	
	@Override
	public int getTodaySales() {
		return dashboardMapper.getTodaySales();
	}

	@Override
	public int getMonthlySales() {
		return dashboardMapper.getMonthlySales();
	}

	@Override
	public List<DashboardVO> getTodayTopFive() {
		return dashboardMapper.getTodayTopFive();
	}

	@Override
	public List<DashboardVO> getTopFiveByReviewCount() { 
		return dashboardMapper.getTopFiveByReviewCount();
	}



}
