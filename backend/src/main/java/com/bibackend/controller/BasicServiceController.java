package com.bibackend.controller;

import com.bibackend.dao.*;
import com.bibackend.service.BasicFunctionService;
import com.bibackend.service.BasicService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("api/basicService")
@Api(tags = "基本业务查询")
public class BasicServiceController {

    @Autowired
    BasicService basicService;

    @Autowired
    BasicFunctionService basicFunctionService;


    //wht部分
    @Operation(summary = "查询关键作者 ", description = "type: 0表示使用hindex，1表示pindex 2表示upindex 3表示publishcount")
    @GetMapping("keyAuthor")
    public ResponseEntity<Object> getKeyAuthor(@Param("interest") String interest, @Param("type") Integer type) {
        return new ResponseEntity<>(basicService.getKeyAuthor(interest, type), HttpStatus.OK);
    }

    @Operation(summary = "查询关键单位")
    @GetMapping("keyAffiliation")
    public ResponseEntity<Object> getKeyAffiliationByInterest(@Param("interest") String interest) {
        return new ResponseEntity<>(basicService.getKeyAffiliationByInterest(interest), HttpStatus.OK);
    }

    @Operation(summary = "查询关键期刊")
    @GetMapping("keyVenue")
    public ResponseEntity<Object> getKeyVenueByInterest(@Param("interest") String interest) {
        return new ResponseEntity<>(basicService.getKeyVenueByInterest(interest), HttpStatus.OK);
    }

    @Operation(summary = "查询某一年的关键期刊")
    @GetMapping("keyVenueByYear")
    public ResponseEntity<Object> getKeyVenueByInterestByYear(@Param("interest") String interest, @Param("year") String year) {
        return new ResponseEntity<>(basicService.getKeyVenueByInterestByYear(interest, year), HttpStatus.OK);
    }


    //hnc部分
    @Operation(summary = "查询作者其所关联的所有关系和关联实体")
    @GetMapping("allauthor")
    public ResponseEntity<Object> getallauthor(@Param("index") String index) {
        return new ResponseEntity<>(basicFunctionService.getallauthor(index), HttpStatus.OK);
    }


    @Operation(summary = "查询paper其所关联的所有关系和关联实体")
    @GetMapping("allpaper")
    public ResponseEntity<Object> getallpaper(@Param("index") String index) {
        return new ResponseEntity<>(basicFunctionService.getallpaper(index), HttpStatus.OK);
    }


    @Operation(summary = "查询author与author之间的5跳及以内关系")
    @GetMapping("auandau")
    public ResponseEntity<Object> getallauandau(@Param("index1") String index1, @Param("index2") String index2) {
        return new ResponseEntity<>(basicFunctionService.getallauandau(index1, index2), HttpStatus.OK);
    }


    @Operation(summary = "查询paper与paper之间的5跳之内关系")
    @GetMapping("paandpa")
    public ResponseEntity<Object> getallpaandpa(@Param("index1") String index1, @Param("index2") String index2) {
        return new ResponseEntity<>(basicFunctionService.getallpaandpa(index1, index2), HttpStatus.OK);
    }


    @Operation(summary = "查询author与paper之间的5跳之内关系")
    @GetMapping("paandau")
    public ResponseEntity<Object> getallpaandpafour(@Param("index1") String index1, @Param("index2") String index2) {
        return new ResponseEntity<>(basicFunctionService.getallpaandpafour(index1, index2), HttpStatus.OK);
    }


    @Operation(summary = "根据id查询其所关联的所有关系和关联实体")
    @GetMapping("allid")
    public ResponseEntity<Object> getallid(@Param("index") Long index) {
        return new ResponseEntity<>(basicFunctionService.getallid(index), HttpStatus.OK);
    }


}
