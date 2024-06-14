package com.unknown.paldak.admin.common.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	private static final int CURRENT_PAGE_COUNT = 1;
	private static final int MIN_PAGE_COUNT = 1;
	private static final double PRECISION_RATIO = 1.0;

	
    private int startPage;
    private int endPage;
    private boolean prev, next;
    
    private int total; //전체 페이지 갯수 
    private Criteria cri; // 몇페이지 , 페이지당 갯수
    
    public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int) (Math.ceil(cri.getPageNum() / (double) cri.getAmount())) * cri.getAmount();
		// 아래와 같은 이유로 한페이지 보여줄 게시글의 수를 부동소수점처리하여 값을 올림하기 위해서 필요
		
		this.startPage = this.endPage - (cri.getAmount() - CURRENT_PAGE_COUNT);
		int realEnd = (int) (Math.ceil((total * PRECISION_RATIO) / cri.getAmount()));
		// 1.0으로 해야 올림을 할 수 있어 예를 들어 5/2는 2이고 5.0/2는 2.5가 돼서 3이 되지 그렇다고 덧셈을 해선 안되
		//total/cri.getAmount() + 1 이런 경우 딱 나누어 떨어지는 경우 한페이지가 더 생성돼서 문제가 되
		

		if (realEnd <= this.endPage) {
			this.endPage = realEnd;
		}

		this.prev = this.startPage > MIN_PAGE_COUNT;

		this.next = this.endPage < realEnd;

    }
}
