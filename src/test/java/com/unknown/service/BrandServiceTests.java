package com.unknown.service;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unknown.model.BrandVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class BrandServiceTests {
    
    @Autowired
    private BrandService service;
    
//    @Test
//    public void authorEnrollTest() throws Exception {
//        BrandVO author = new BrandVO();
//        author.setNationId("01");
//        author.setAuthorName("테스트");
//        author.setAuthorIntro("테스트 소개");
//        
//        service.authorEnroll(author);
//    }
}
