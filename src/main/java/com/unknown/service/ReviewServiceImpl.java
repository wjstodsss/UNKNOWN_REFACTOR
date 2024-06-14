package com.unknown.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unknown.mapper.ReviewMapper;
import com.unknown.model.Criteria;
import com.unknown.model.ReviewVO;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewMapper mapper;
	
	@Override
	public ReviewVO get(Long reviewId) {
		 log.info("get....." + reviewId);
	      return mapper.read(reviewId);
	}

	@Override
	public boolean modify(ReviewVO Review) {
		log.info("modify.... " + Review);
	     return mapper.update(Review)==1;
	}


	@Override
	public boolean remove(Long reviewId) {
		log.info("remove...." + reviewId);
	     return mapper.delete(reviewId)==1;
	}



	@Override
	public List<ReviewVO> getList(Criteria cri) {
		log.info("getList.....");
		return mapper.getListWithPaging(cri);
	}

	@Override
	public void register(ReviewVO Review) {
		log.info("register.... " + Review);
		mapper.insertSelectKey(Review);
		
	}
	
	@Override
	public int getTotal(Criteria cri) {
		return mapper.getTotalCount(cri);
	}
	
	@Override
	public List<ReviewVO> getListByItemId(int itemId) {
	    log.info("getListByItemId.....");
	    return mapper.getListByItemId(itemId);
	}
	

    @Override
    public List<ReviewVO> getListByMemberId(String memberId, Criteria cri) {
    	int amount = cri.getAmount();
    	int pageNum = cri.getPageNum();
    	List<ReviewVO> result = mapper.getListByMemberId(memberId, amount,  pageNum);

    	return result;
    }
    
    @Override
    public int getTotalByMemberId(String memberId, Criteria cri) {
        return mapper.getTotalByMemberId(memberId, cri);
    }
    
    @Override
    public String getItemNameById(int itemId) {
        return mapper.getItemNameById(itemId);
    }
    
    @Override
    public String getReviewWriterByReviewId(Long reviewId) {
        return mapper.getReviewWriterByReviewId(reviewId);
    }
	
}
