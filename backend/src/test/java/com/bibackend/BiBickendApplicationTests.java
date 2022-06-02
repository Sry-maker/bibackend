package com.bibackend;


import com.bibackend.dao.*;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.internal.InternalRelationship;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

//        List<Map<String, Object>> allreferednode = paperRepository.findAllreferednode("512");
//        System.out.println(allreferednode);
//
//        List<Map<String, Object>> allrefernode = paperRepository.findAllrefernode("512");
//        System.out.println(allrefernode);
//        List<Map<String, Object>> maps = authorRepository.findallRelation("624678", "624878");
//        System.out.println(maps);
//        List<Map<String, Object>> findtestnode = paperRepository.findtestnode("624678", "624878");
//        System.out.println(findtestnode.toString());
        List<Map<String, String>> data = new ArrayList<>();
        List<Map<String,String>>  links=new ArrayList<>();
        List<Map<String, Object>> allttestnode = paperRepository.findpandpnode("513", "1135138");
        for (Map<String, Object> temp:allttestnode){
//        Map<String, Object> temp=allttestnode.get(0);
//        Object p = temp.get("p");
//        Object p1 = p.toString();
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

                System.out.println("start");


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
                System.out.println("end");

                InternalRelationship relationship= (InternalRelationship) p.relationship();
                Map<String, String> link = new HashMap<>();
                long startNodeId=relationship.startNodeId();
                long endNodeId = relationship.endNodeId();
                String type = relationship.type();
                link.put("source",String.valueOf(startNodeId));
                link.put("target",String.valueOf(endNodeId));
                link.put("type",type);
                links.add(link);
//           Map<String, Object> test= (Map<String, Object>) st.get("properties");
            }
        }
       Map<String, Object> result= new HashMap<>();
        result.put("data",data);
        result.put("links",links);
        System.out.println(allttestnode.toString());

    }

}
