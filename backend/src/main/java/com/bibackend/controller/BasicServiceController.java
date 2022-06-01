package com.bibackend.controller;

import com.bibackend.dao.*;
import com.bibackend.entity.AUTHOR;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("api/basicService")
@Api(tags = "基本业务查询")
public class BasicServiceController {
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
    INTERESTRepository interestRepository;
    @Resource
    PUBLISHRepository publishRepository;
    @Resource
    REFERRepository referRepository;
    @Resource
    VENUERepository venueRepository;
    @Resource
    WRITERepository writeRepository;


    //wht部分
    @Operation(summary = "查询关键作者 ", description = "type: 0表示使用hindex，1表示pindex 2表示upindex 3表示publishcount")
    @GetMapping("keyAuthor")
    public ResponseEntity<Object> getKeyAuthor(@Param("interest") String interest, @Param("type") Integer type) {

        List<AUTHOR> authorList = authorRepository.findAuthorByInterest(interest);
        if (type.equals(0)) {
            authorList.sort((o1, o2) -> {
                Float sum1 = Float.parseFloat(o1.getHindex());
                Float sum2 = Float.parseFloat(o2.getHindex());
                return sum2.compareTo(sum1);
            });
        } else if (type.equals(1)) {
            authorList.sort((o1, o2) -> {
                Float sum1 = Float.parseFloat(o1.getPindex());
                Float sum2 = Float.parseFloat(o2.getPindex());
                return sum2.compareTo(sum1);
            });
        } else if (type.equals(2)) {
            authorList.sort((o1, o2) -> {
                Float sum1 = Float.parseFloat(o1.getUPindex());
                Float sum2 = Float.parseFloat(o2.getUPindex());
                return sum2.compareTo(sum1);
            });
        } else if (type.equals(3)) {
            authorList.sort((o1, o2) -> {
                Float sum1 = Float.parseFloat(o1.getPublishCount());
                Float sum2 = Float.parseFloat(o2.getPublishCount());
                return sum2.compareTo(sum1);
            });
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("message", "参数错误");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(authorList, HttpStatus.OK);

    }

    @Operation(summary = "查询关键单位")
    @GetMapping("keyAffiliation")
    public ResponseEntity<Object> getKeyAffiliationByInterest(@Param("interest") String interest) {
        return new ResponseEntity<>(affiliationRepository.getKeyAffiliation(interest), HttpStatus.OK);
    }

    @Operation(summary = "查询关键期刊")
    @GetMapping("keyVenue")
    public ResponseEntity<Object> getKeyVenueByInterest(@Param("interest") String interest) {
        return new ResponseEntity<>(venueRepository.getKeyVenue(interest, "", true), HttpStatus.OK);
    }
    @Operation(summary = "查询某一年的关键期刊")
    @GetMapping("keyVenueByYear")
    public ResponseEntity<Object> getKeyVenueByInterestByYear(@Param("interest") String interest, @Param("year") String year){
        return new ResponseEntity<>(venueRepository.getKeyVenue(interest, year, false), HttpStatus.OK);
    }




    //hnc部分
    @Operation(summary = "查询作者其所关联的所有关系和关联实体")
    @GetMapping("allauthor")
    public ResponseEntity<Object> getallauthor(@Param("index") String index) {

        List<Map<String, Object>> allwritenode = authorRepository.findAllwritenode(index);
        for(int i =0;i<allwritenode.size();i++){
            Map<String, Object> temp=allwritenode.get(i);
            temp.put("relation","author-write-paper");
        }
        List<Map<String, Object>> allhas_interestnode = authorRepository.findAllhas_interestnode(index);
        for(int i =0;i<allhas_interestnode.size();i++){
            Map<String, Object> temp=allhas_interestnode.get(i);
            temp.put("relation","author-has_interestnode-interest");
        }
        List<Map<String, Object>> allcoauthornode = authorRepository.findAllcoauthornode(index);
        for(int i =0;i<allcoauthornode.size();i++){
            Map<String, Object> temp=allcoauthornode.get(i);
            temp.put("relation","author-WRITE-interest");
        }

        List<Map<String, Object>> allbelongtonode = authorRepository.findAllbelongtonode(index);
        for(int i =0;i<allbelongtonode.size();i++){
            Map<String, Object> temp=allbelongtonode.get(i);
            temp.put("relation","author-belong_to-interest");
        }


        Map<String,List> result=new HashMap<>();
        result.put("belong_to", allbelongtonode);
        result.put("write", allwritenode);
        result.put("has_interestnode", allhas_interestnode);
        result.put("coauthornode", allcoauthornode);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Operation(summary = "查询paper其所关联的所有关系和关联实体")
    @GetMapping("allpaper")
    public ResponseEntity<Object> getallpaper(@Param("index") String index) {

        List<Map<String, Object>> allreferednode = paperRepository.findAllreferednode(index);
        for(int i =0;i<allreferednode.size();i++){
            Map<String, Object> temp=allreferednode.get(i);
            temp.put("relation","paper-refer-paper");
        }
        List<Map<String, Object>> allpublishnode = paperRepository.findAllpublishnode(index);
        for(int i =0;i<allpublishnode.size();i++){
            Map<String, Object> temp=allpublishnode.get(i);
            temp.put("relation","venue-publish-paper");
        }
        List<Map<String, Object>> allwritenode = paperRepository.findAllwritenode(index);
        for(int i =0;i<allwritenode.size();i++){
            Map<String, Object> temp=allwritenode.get(i);
            temp.put("relation","author-write-paper");
        }


        Map<String,List> result=new HashMap<>();
        result.put("refernode", allreferednode);
        result.put("publishnode", allpublishnode);
        result.put("writenode", allwritenode);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Operation(summary = "查询作者与作者之间的5跳及以内（包括1跳）关系")
    @GetMapping("auandau")
    public ResponseEntity<Object> getallauandau(@Param("index1") String index1,@Param("index2") String index2) {
//        System.out.println("has_interest");
//        List<Map<String, Object>> interestRelation = interestRepository.findInterestRelation("1243827", "1340571");
//        List<Map<String, Object>> interestRelation = interestRepository.findInterestRelation(index1, index2);
//        List<Map<String, Object>> allonecoauthornode = authorRepository.findAllonecoauthornode(index1, index2);



//        List<Map<String, Object>> interestRelation = interestRepository.findInterestRelation("1243827", "1340571");
//        List<Map<String, Object>> allonecoauthornode = authorRepository.findAllonecoauthornode("1243827", "1340571");
//
//        Map<String,List> result=new HashMap<>();
//        result.put("interestRelation", interestRelation);
//        result.put("onecoauthornode", allonecoauthornode);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//        authorRepository.findAllaandanode(index1,index2);
        List<Map<String, Object>> allaandanode = authorRepository.findAllaandanode(index1, index2);
        return new ResponseEntity<>(allaandanode, HttpStatus.OK);

    }

    @Operation(summary = "查询paper与paper之间的5跳之内（包括1跳）关系")
    @GetMapping("paandpa")
    public ResponseEntity<Object> getallpaandpa(@Param("index1") String index1,@Param("index2") String index2) {
//        List<Map<String, Object>> allonereferednode = paperRepository.findAllonereferednode(index1, index2);
//        List<Map<String, Object>> authorRelation = authorRepository.findauthorRelation(index1, index2);
//        List<Map<String, Object>> allthreereferednode = paperRepository.findAllthreereferednode(index1, index2);
//        List<Map<String, Object>> authorinterestRelation = interestRepository.findauthorinterestRelation(index1, index2);
////        authorRepository.findallRelation("624678", "624878");
////        List<Map<String, Object>> authorRelation = authorRepository.findauthorRelation("624678", "624878");
//        Map<String,List> result=new HashMap<>();
//
//        result.put("onepaandpa", allonereferednode);
//        result.put("twopaandpa", authorRelation);
//        result.put("threepaandpa", allthreereferednode);
//        result.put("fourpaandpa", authorinterestRelation);
//        return new ResponseEntity<>(result, HttpStatus.OK);
        List<Map<String, Object>> findpandpnode = paperRepository.findpandpnode(index1, index2);
        return new ResponseEntity<>(findpandpnode, HttpStatus.OK);
    }
    @Operation(summary = "查询paper与paper之间的四跳关系")
    @GetMapping("paandau")
    public ResponseEntity<Object> getallpaandpafour(@Param("index1") String index1,@Param("index2") String index2) {

//        List<Map<String, Object>> authorRelation = interestRepository.findauthorinterestRelation(index1, index2);
//
////        List<Map<String, Object>> authorRelation = interestRepository.findauthorinterestRelation("1095401", "1242327");

        return new ResponseEntity<>(authorRepository.findAllaandpnode(index1,index2), HttpStatus.OK);
    }


}
