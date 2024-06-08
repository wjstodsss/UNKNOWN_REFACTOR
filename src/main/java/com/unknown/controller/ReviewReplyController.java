package com.unknown.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.unknown.model.Criteria;
import com.unknown.model.ReviewReplyVO;
import com.unknown.service.ReviewReplyService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
@RestController
@RequestMapping("/replies/*")
@AllArgsConstructor
@NoArgsConstructor
@Log4j
public class ReviewReplyController {
    @Autowired
    private ReviewReplyService service;

    @GetMapping("/test")
    public String testReply() {
        return "test"; // @RestController 문자열만 전송
    }

    @PostMapping(value = "/new", consumes = "application/json", produces = { MediaType.TEXT_HTML_VALUE })
    public ResponseEntity<String> create(@RequestBody ReviewReplyVO vo) {
        log.info("ReplyVO: " + vo);
        int insertCount = service.register(vo);
        return insertCount == 1 ? new ResponseEntity<String>("success", HttpStatus.OK)
                : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/pages/{reviewId}/{page}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<ReviewReplyVO>> getList(@PathVariable("reviewId") Long reviewId, @PathVariable("page") int page) {
        log.info("ppppppppppppppppppppppppppppppps"+reviewId);
    	log.info("getList.......... reviewId: " + reviewId + ", page: " + page);
        Criteria cri = new Criteria(page, 10);
        return new ResponseEntity<List<ReviewReplyVO>>(service.getList(cri, reviewId), HttpStatus.OK);
    }

    @GetMapping(value = "/{replyId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ReviewReplyVO> get(@PathVariable("replyId") Long replyId) {
    	log.info("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy"+replyId);
        log.info("get: " + replyId);
        return new ResponseEntity<>(service.get(replyId), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{replyId}", produces = { MediaType.TEXT_PLAIN_VALUE })
    public ResponseEntity<String> remove(@PathVariable("replyId") Long replyId) {
    	log.info("remove: " + replyId);
        return service.remove(replyId) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{replyId}", consumes = "application/json", produces = {
            MediaType.TEXT_PLAIN_VALUE })
    public ResponseEntity<String> modify(@RequestBody ReviewReplyVO vo, @PathVariable("replyId") Long replyId) {
        vo.setReplyId(replyId);
        log.info("modify: " + vo);
        return service.modify(vo) == 1 ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
