package com.bibackend.controller;


import com.bibackend.dao.*;
import io.swagger.annotations.Api;
import javax.annotation.Resource;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/author")
@Api(tags = "author")
public class AUTHORController {
    @Resource
    PAPERRepository paperRepository;
    @Resource
    AFFILIATIONRepository affiliationRepository;

    @Resource
    AUTHORRepository authorRepository;

    @Resource
    BELONG_TORepository belong_toRepository;

    @Resource
    HAS_INTERESTRepository has_interestRepository;

    @Resource
    CO_AUTHORRepository co_authorRepository;
    @Resource
    INTERESTRepository  interestRepository;
    @Resource
    PUBLISHRepository publishRepository;

    @Resource
    REFERRepository referRepository;

    @Resource
    VENUERepository venueRepository;
    @Resource
    WRITERepository writeRepository;

    @Operation(summary = "AUTHOR其关联的所有关系和关联实体")
    @GetMapping("authorlink")
    public ResponseEntity<Object> getauthorlink(@Param("author") String author) {

        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }





}
