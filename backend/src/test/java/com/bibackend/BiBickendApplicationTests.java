package com.bibackend;


import com.bibackend.dao.*;
import com.bibackend.entity.PAPER;
import com.bibackend.entity.WRITE;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
public class BiBickendApplicationTests {

    @Autowired
    PAPERRepository paperRepository;
    @Autowired
    AFFILIATIONRepository affiliationRepository;

    @Autowired
    AUTHORRepository authorRepository;

    @Autowired
    BELONG_TORepository belong_toRepository;

    @Autowired
    HAS_INTERESTRepository has_interestRepository;

    @Autowired
    CO_AUTHORRepository co_authorRepository;
    @Autowired
    INTERESTRepository  interestRepository;
    @Autowired
    PUBLISHRepository publishRepository;

    @Autowired
    REFERRepository referRepository;

    @Autowired
    VENUERepository venueRepository;
    @Autowired
    WRITERepository writeRepository;




    @Test
    void contextLoads() {
    }
    @Test
    public void test(){
//        long count = paperRepository.count();
//        System.out.println(count);
//
//        long count1 = affiliationRepository.count();
//        System.out.println(count1);
//
//        long count2 = authorRepository.count();
//        System.out.println(count2);
//
//        long count3 = interestRepository.count();
//        System.out.println(count3);
//
//        long count4 = venueRepository.count();
//        System.out.println(count4);
//
//        System.out.println("write");
//        List<Map<String, Object>> allwritenode = authorRepository.findAllwritenode("20");
//        System.out.println(allwritenode);
//        System.out.println("has_interestnode");
//        List<Map<String, Object>> allhas_interestnode = authorRepository.findAllhas_interestnode("20");
//        System.out.println(allhas_interestnode);
//        List<Map<String, Object>> allcoauthornode = authorRepository.findAllcoauthornode("20");
//        System.out.println(allcoauthornode);

        List<Map<String, Object>> allreferednode = paperRepository.findAllreferednode("512");
        System.out.println(allreferednode);

        List<Map<String, Object>> allrefernode = paperRepository.findAllrefernode("512");
        System.out.println(allrefernode);

    }

}
