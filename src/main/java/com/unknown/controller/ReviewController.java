package com.unknown.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.unknown.model.Criteria;
import com.unknown.model.ItemVO;
import com.unknown.model.MemberVO;
import com.unknown.model.PageDTO;
import com.unknown.model.ReviewVO;
import com.unknown.service.ItemService;
import com.unknown.service.MemberService;
import com.unknown.service.ReviewService;
import com.unknown.util.FileUploadManager;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
@Controller
@Log4j
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final FileUploadManager fileUploadManager;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/review/{memberId}")
    public String review(@PathVariable("memberId") String memberId, Criteria cri, Model model) {
        log.info("list by memberId: " + memberId);

        MemberVO member = memberService.getMemberInfo(memberId);
        model.addAttribute("member", member);

        List<ReviewVO> list = reviewService.getListByMemberId(memberId, cri);
        model.addAttribute("list", list);
        model.addAttribute("pageMaker", new PageDTO(cri, reviewService.getTotalByMemberId(memberId, cri)));

        return "review/review";
    }

    @GetMapping("/register")
    public void register(Model model, HttpSession session) {
        List<ItemVO> items = itemService.getAllItems();
        model.addAttribute("items", items);

        // 로그인된 사용자 정보 가져오기
        String memberId = (String) session.getAttribute("memberId");
        model.addAttribute("memberId", memberId);
    }

    @PostMapping("/register")
    public String register(@RequestParam("uploadFile") MultipartFile[] uploadFile, ReviewVO reviewVO, RedirectAttributes rttr, HttpSession session) {
        if (!uploadFile[0].isEmpty()) {
            String imageURL = fileUploadManager.uploadFiles(uploadFile);
            reviewVO.setReviewImageURL(imageURL);
        } else {
            reviewVO.setReviewImageURL("default");
        }
        
        // 세션에서 memberId 가져오기
        String memberId = (String) session.getAttribute("memberId");
        reviewVO.setReviewWriter(memberId);

        reviewService.register(reviewVO);
        rttr.addFlashAttribute("result", reviewVO.getReviewId());

        return "redirect:/review/review/" + reviewVO.getReviewWriter();
    }

    @PostMapping("/modify")
    public String modify(@RequestParam("uploadFile") MultipartFile[] uploadFile, ReviewVO reviewVO, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
        if (!uploadFile[0].isEmpty()) {
            String imgURL = fileUploadManager.uploadFiles(uploadFile);
            reviewVO.setReviewImageURL(imgURL);
        }
        log.info("modify:" + reviewVO);
        if (reviewService.modify(reviewVO)) {
            rttr.addFlashAttribute("result", "success");
        }
        rttr.addAttribute("pageNum", cri.getPageNum());
        rttr.addAttribute("amount", cri.getAmount());
        rttr.addAttribute("type", cri.getType());
        rttr.addAttribute("keyword", cri.getKeyword());
        return "redirect:/review/review/" + reviewVO.getReviewWriter();
    }

    @GetMapping("/get")
    public String get(@RequestParam("reviewId") Long reviewId, @ModelAttribute("cri") Criteria cri, Model model) {
        ReviewVO review = reviewService.get(reviewId);
        model.addAttribute("review", review);

        // itemName 추가
        String itemName = reviewService.getItemNameById(review.getItemId());
        model.addAttribute("itemName", itemName);

        model.addAttribute("cri", cri);
        return "review/get"; // get.jsp로 리턴
    }

    @GetMapping("/modify")
    public String modify(@RequestParam("reviewId") Long reviewId, @ModelAttribute("cri") Criteria cri, Model model) {
        ReviewVO review = reviewService.get(reviewId);
        model.addAttribute("review", review);

        // itemName 추가
        String itemName = reviewService.getItemNameById(review.getItemId());
        model.addAttribute("itemName", itemName);

        model.addAttribute("cri", cri);
        return "review/modify"; // modify.jsp로 리턴
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("reviewId") Long reviewId, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
        log.info("remove...." + reviewId);
        String reviewWriter = reviewService.getReviewWriterByReviewId(reviewId); // 리뷰 작성자 아이디 가져오기
        if (reviewService.remove(reviewId)) {
            rttr.addFlashAttribute("result", "success");
        }
        rttr.addAttribute("pageNum", cri.getPageNum());
        rttr.addAttribute("amount", cri.getAmount());
        rttr.addAttribute("type", cri.getType());
        rttr.addAttribute("keyword", cri.getKeyword());
        return "redirect:/review/review/" + reviewWriter;
    }
}
