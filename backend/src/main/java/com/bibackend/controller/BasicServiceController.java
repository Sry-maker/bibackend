package com.bibackend.controller;

import com.bibackend.dao.*;
import com.bibackend.entity.AUTHOR;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.internal.InternalRelationship;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Map<String, String>> data = new ArrayList<>();
        List<Map<String,String>>  links=new ArrayList<>();
        List<Map<String, Object>> allttestnode = authorRepository.findAllaandanode(index1,index2);
        for (Map<String, Object> temp:allttestnode){
            InternalPath.SelfContainedSegment[] ps=(InternalPath.SelfContainedSegment[]) temp.get("p");
            for (InternalPath.SelfContainedSegment p : ps) {
                InternalNode st= (InternalNode) p.start();
                Map<String, Object> sttest= st.asMap();
                Map<String, String> stret = new HashMap<>();
                for (Map.Entry<String, Object> entry : sttest.entrySet()) {
                    stret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> labels = (List<String>) st.labels();
                String q=labels.get(0);
                if(labels.get(0).equals("PAPER")){
                    stret.put("category","0");
                }
                else if(labels.get(0).equals("AUTHOR")){
                    stret.put("category","1");
                }
                else if(labels.get(0).equals("INTEREST")){
                    stret.put("category","2");
                }
                else if(labels.get(0).equals("AFFILIATION")){
                    stret.put("category","3");
                }
                else if(labels.get(0).equals("VENUE")){
                    stret.put("category","4");
                }
                boolean isEmpty=stret.containsKey("name");
                if(isEmpty==true){
                    String truename=stret.get("name");
                    stret.put("truename",truename);
                }
                long tempid = st.id();
                String id = String.valueOf(tempid);
                stret.put("name",id);
                boolean stexist=false;
                for (Map<String,String> newMap : data){
                    if(newMap.get("name").equals(stret.get("name"))){
                        stexist=true;
                    }
                }
                if(stexist==false){
                    data.add(stret);
                }


                InternalNode end= (InternalNode) p.end();
                Map<String, Object> endtest= end.asMap();
                Map<String, String> endret = new HashMap<>();
                for (Map.Entry<String, Object> entry : endtest.entrySet()) {
                    endret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> endlabels = (List<String>) end.labels();
                String endq=endlabels.get(0);
                if(endlabels.get(0).equals("PAPER")){
                    endret.put("category","0");
                }
                else if(endlabels.get(0).equals("AUTHOR")){
                    endret.put("category","1");
                }
                else if(endlabels.get(0).equals("INTEREST")){
                    endret.put("category","2");
                }
                else if(endlabels.get(0).equals("AFFILIATION")){
                    endret.put("category","3");
                }
                else if(endlabels.get(0).equals("VENUE")){
                    endret.put("category","4");
                }
                boolean endisEmpty=endret.containsKey("name");
                if(endisEmpty==true){
                    String endtruename=endret.get("name");
                    endret.put("truename",endtruename);
                }
                long endtempid = end.id();
                String endid = String.valueOf(endtempid);
                endret.put("name",endid);
                boolean endexist=false;
                for (Map<String,String> newMap : data){
                    if(newMap.get("name").equals(endret.get("name"))){
                        endexist=true;
                    }
                }
                if(endexist==false){
                    data.add(endret);
                }


                InternalRelationship relationship= (InternalRelationship) p.relationship();
                Map<String, String> link = new HashMap<>();
                long startNodeId=relationship.startNodeId();
                long endNodeId = relationship.endNodeId();
                String type = relationship.type();
                link.put("source",String.valueOf(startNodeId));
                link.put("target",String.valueOf(endNodeId));
                link.put("type",type);
                links.add(link);

            }
        }
        Map<String, Object> result= new HashMap<>();
        result.put("data",data);
        result.put("links",links);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @Operation(summary = "查询paper与paper之间的5跳之内（包括1跳）关系")
    @GetMapping("paandpa")
    public ResponseEntity<Object> getallpaandpa(@Param("index1") String index1,@Param("index2") String index2) {
        List<Map<String, String>> data = new ArrayList<>();
        List<Map<String,String>>  links=new ArrayList<>();
        List<Map<String, Object>> allttestnode = paperRepository.findpandpnode(index1,index2);
        for (Map<String, Object> temp:allttestnode){
            InternalPath.SelfContainedSegment[] ps=(InternalPath.SelfContainedSegment[]) temp.get("p");
            for (InternalPath.SelfContainedSegment p : ps) {
                InternalNode st= (InternalNode) p.start();
                Map<String, Object> sttest= st.asMap();
                Map<String, String> stret = new HashMap<>();
                for (Map.Entry<String, Object> entry : sttest.entrySet()) {
                    stret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> labels = (List<String>) st.labels();
                String q=labels.get(0);
                if(labels.get(0).equals("PAPER")){
                    stret.put("category","0");
                }
                else if(labels.get(0).equals("AUTHOR")){
                    stret.put("category","1");
                }
                else if(labels.get(0).equals("INTEREST")){
                    stret.put("category","2");
                }
                else if(labels.get(0).equals("AFFILIATION")){
                    stret.put("category","3");
                }
                else if(labels.get(0).equals("VENUE")){
                    stret.put("category","4");
                }
                boolean isEmpty=stret.containsKey("name");
                if(isEmpty==true){
                    String truename=stret.get("name");
                    stret.put("truename",truename);
                }
                long tempid = st.id();
                String id = String.valueOf(tempid);
                stret.put("name",id);
                boolean stexist=false;
                for (Map<String,String> newMap : data){
                    if(newMap.get("name").equals(stret.get("name"))){
                        stexist=true;
                    }
                }
                if(stexist==false){
                    data.add(stret);
                }


                InternalNode end= (InternalNode) p.end();
                Map<String, Object> endtest= end.asMap();
                Map<String, String> endret = new HashMap<>();
                for (Map.Entry<String, Object> entry : endtest.entrySet()) {
                    endret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> endlabels = (List<String>) end.labels();
                String endq=endlabels.get(0);
                if(endlabels.get(0).equals("PAPER")){
                    endret.put("category","0");
                }
                else if(endlabels.get(0).equals("AUTHOR")){
                    endret.put("category","1");
                }
                else if(endlabels.get(0).equals("INTEREST")){
                    endret.put("category","2");
                }
                else if(endlabels.get(0).equals("AFFILIATION")){
                    endret.put("category","3");
                }
                else if(endlabels.get(0).equals("VENUE")){
                    endret.put("category","4");
                }
                boolean endisEmpty=endret.containsKey("name");
                if(endisEmpty==true){
                    String endtruename=endret.get("name");
                    endret.put("truename",endtruename);
                }
                long endtempid = end.id();
                String endid = String.valueOf(endtempid);
                endret.put("name",endid);
                boolean endexist=false;
                for (Map<String,String> newMap : data){
                    if(newMap.get("name").equals(endret.get("name"))){
                        endexist=true;
                    }
                }
                if(endexist==false){
                    data.add(endret);
                }


                InternalRelationship relationship= (InternalRelationship) p.relationship();
                Map<String, String> link = new HashMap<>();
                long startNodeId=relationship.startNodeId();
                long endNodeId = relationship.endNodeId();
                String type = relationship.type();
                link.put("source",String.valueOf(startNodeId));
                link.put("target",String.valueOf(endNodeId));
                link.put("type",type);
                links.add(link);

            }
        }
        Map<String, Object> result= new HashMap<>();
        result.put("data",data);
        result.put("links",links);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @Operation(summary = "查询paper与paper之间的四跳关系")
    @GetMapping("paandau")
    public ResponseEntity<Object> getallpaandpafour(@Param("index1") String index1,@Param("index2") String index2) {
        List<Map<String, String>> data = new ArrayList<>();
        List<Map<String,String>>  links=new ArrayList<>();
        List<Map<String, Object>> allttestnode = authorRepository.findAllaandpnode(index1,index2);
        for (Map<String, Object> temp:allttestnode){
            InternalPath.SelfContainedSegment[] ps=(InternalPath.SelfContainedSegment[]) temp.get("p");
            for (InternalPath.SelfContainedSegment p : ps) {
                InternalNode st= (InternalNode) p.start();
                Map<String, Object> sttest= st.asMap();
                Map<String, String> stret = new HashMap<>();
                for (Map.Entry<String, Object> entry : sttest.entrySet()) {
                    stret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> labels = (List<String>) st.labels();
                String q=labels.get(0);
                if(labels.get(0).equals("PAPER")){
                    stret.put("category","0");
                }
                else if(labels.get(0).equals("AUTHOR")){
                    stret.put("category","1");
                }
                else if(labels.get(0).equals("INTEREST")){
                    stret.put("category","2");
                }
                else if(labels.get(0).equals("AFFILIATION")){
                    stret.put("category","3");
                }
                else if(labels.get(0).equals("VENUE")){
                    stret.put("category","4");
                }
                boolean isEmpty=stret.containsKey("name");
                if(isEmpty==true){
                    String truename=stret.get("name");
                    stret.put("truename",truename);
                }
                long tempid = st.id();
                String id = String.valueOf(tempid);
                stret.put("name",id);
                boolean stexist=false;
                for (Map<String,String> newMap : data){
                    if(newMap.get("name").equals(stret.get("name"))){
                        stexist=true;
                    }
                }
                if(stexist==false){
                    data.add(stret);
                }


                InternalNode end= (InternalNode) p.end();
                Map<String, Object> endtest= end.asMap();
                Map<String, String> endret = new HashMap<>();
                for (Map.Entry<String, Object> entry : endtest.entrySet()) {
                    endret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> endlabels = (List<String>) end.labels();
                String endq=endlabels.get(0);
                if(endlabels.get(0).equals("PAPER")){
                    endret.put("category","0");
                }
                else if(endlabels.get(0).equals("AUTHOR")){
                    endret.put("category","1");
                }
                else if(endlabels.get(0).equals("INTEREST")){
                    endret.put("category","2");
                }
                else if(endlabels.get(0).equals("AFFILIATION")){
                    endret.put("category","3");
                }
                else if(endlabels.get(0).equals("VENUE")){
                    endret.put("category","4");
                }
                boolean endisEmpty=endret.containsKey("name");
                if(endisEmpty==true){
                    String endtruename=endret.get("name");
                    endret.put("truename",endtruename);
                }
                long endtempid = end.id();
                String endid = String.valueOf(endtempid);
                endret.put("name",endid);
                boolean endexist=false;
                for (Map<String,String> newMap : data){
                    if(newMap.get("name").equals(endret.get("name"))){
                        endexist=true;
                    }
                }
                if(endexist==false){
                    data.add(endret);
                }


                InternalRelationship relationship= (InternalRelationship) p.relationship();
                Map<String, String> link = new HashMap<>();
                long startNodeId=relationship.startNodeId();
                long endNodeId = relationship.endNodeId();
                String type = relationship.type();
                link.put("source",String.valueOf(startNodeId));
                link.put("target",String.valueOf(endNodeId));
                link.put("type",type);
                links.add(link);

            }
        }
        Map<String, Object> result= new HashMap<>();
        result.put("data",data);
        result.put("links",links);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
