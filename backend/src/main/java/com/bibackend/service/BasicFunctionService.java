package com.bibackend.service;

import com.bibackend.dao.AUTHORRepository;
import com.bibackend.dao.PAPERRepository;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.internal.InternalPath;
import org.neo4j.driver.internal.InternalRelationship;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "basic-function")
public class BasicFunctionService {
    @Resource
    AUTHORRepository authorRepository;
    @Resource
    PAPERRepository paperRepository;

    @Cacheable(key = "'author-relation-index-' + #p0")
    public Map<String, Object> getallauthor(String index) {
        List<Map<String, String>> data = new ArrayList<>();
        List<Map<String, String>> links = new ArrayList<>();
        List<Map<String, Object>> allttestnode = authorRepository.findAllonetonode(index);
        for (Map<String, Object> temp : allttestnode) {
            InternalPath.SelfContainedSegment[] ps = (InternalPath.SelfContainedSegment[]) temp.get("p");
            for (InternalPath.SelfContainedSegment p : ps) {
                InternalNode st = (InternalNode) p.start();
                Map<String, Object> sttest = st.asMap();
                Map<String, String> stret = new HashMap<>();
                for (Map.Entry<String, Object> entry : sttest.entrySet()) {
                    stret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> labels = (List<String>) st.labels();
                String q = labels.get(0);
                if (labels.get(0).equals("PAPER")) {
                    stret.put("category", "0");
                } else if (labels.get(0).equals("AUTHOR")) {
                    stret.put("category", "1");
                } else if (labels.get(0).equals("INTEREST")) {
                    stret.put("category", "2");
                } else if (labels.get(0).equals("AFFILIATION")) {
                    stret.put("category", "3");
                } else if (labels.get(0).equals("VENUE")) {
                    stret.put("category", "4");
                }
                boolean isEmpty = stret.containsKey("name");
                if (isEmpty == true) {
                    String truename = stret.get("name");
                    stret.put("truename", truename);
                }
                long tempid = st.id();
                String id = String.valueOf(tempid);
                stret.put("name", id);
                boolean stexist = false;
                for (Map<String, String> newMap : data) {
                    if (newMap.get("name").equals(stret.get("name"))) {
                        stexist = true;
                    }
                }
                if (stexist == false) {
                    data.add(stret);
                }


                InternalNode end = (InternalNode) p.end();
                Map<String, Object> endtest = end.asMap();
                Map<String, String> endret = new HashMap<>();
                for (Map.Entry<String, Object> entry : endtest.entrySet()) {
                    endret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> endlabels = (List<String>) end.labels();
                String endq = endlabels.get(0);
                if (endlabels.get(0).equals("PAPER")) {
                    endret.put("category", "0");
                } else if (endlabels.get(0).equals("AUTHOR")) {
                    endret.put("category", "1");
                } else if (endlabels.get(0).equals("INTEREST")) {
                    endret.put("category", "2");
                } else if (endlabels.get(0).equals("AFFILIATION")) {
                    endret.put("category", "3");
                } else if (endlabels.get(0).equals("VENUE")) {
                    endret.put("category", "4");
                }
                boolean endisEmpty = endret.containsKey("name");
                if (endisEmpty == true) {
                    String endtruename = endret.get("name");
                    endret.put("truename", endtruename);
                }
                long endtempid = end.id();
                String endid = String.valueOf(endtempid);
                endret.put("name", endid);
                boolean endexist = false;
                for (Map<String, String> newMap : data) {
                    if (newMap.get("name").equals(endret.get("name"))) {
                        endexist = true;
                    }
                }
                if (endexist == false) {
                    data.add(endret);
                }


                InternalRelationship relationship = (InternalRelationship) p.relationship();
                Map<String, String> link = new HashMap<>();
                long startNodeId = relationship.startNodeId();
                long endNodeId = relationship.endNodeId();
                String type = relationship.type();
                link.put("source", String.valueOf(startNodeId));
                link.put("target", String.valueOf(endNodeId));
                link.put("type", type);
                links.add(link);

            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("links", links);
        return result;
    }

    @Cacheable(key = "'paper-relation-index-' + #p0")
    public Map<String, Object> getallpaper(String index) {
        List<Map<String, String>> data = new ArrayList<>();
        List<Map<String, String>> links = new ArrayList<>();
        List<Map<String, Object>> allttestnode = paperRepository.findAllonenode(index);
        for (Map<String, Object> temp : allttestnode) {
            InternalPath.SelfContainedSegment[] ps = (InternalPath.SelfContainedSegment[]) temp.get("p");
            for (InternalPath.SelfContainedSegment p : ps) {
                InternalNode st = (InternalNode) p.start();
                Map<String, Object> sttest = st.asMap();
                Map<String, String> stret = new HashMap<>();
                for (Map.Entry<String, Object> entry : sttest.entrySet()) {
                    stret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> labels = (List<String>) st.labels();
                String q = labels.get(0);
                if (labels.get(0).equals("PAPER")) {
                    stret.put("category", "0");
                } else if (labels.get(0).equals("AUTHOR")) {
                    stret.put("category", "1");
                } else if (labels.get(0).equals("INTEREST")) {
                    stret.put("category", "2");
                } else if (labels.get(0).equals("AFFILIATION")) {
                    stret.put("category", "3");
                } else if (labels.get(0).equals("VENUE")) {
                    stret.put("category", "4");
                }
                boolean isEmpty = stret.containsKey("name");
                if (isEmpty == true) {
                    String truename = stret.get("name");
                    stret.put("truename", truename);
                }
                long tempid = st.id();
                String id = String.valueOf(tempid);
                stret.put("name", id);
                boolean stexist = false;
                for (Map<String, String> newMap : data) {
                    if (newMap.get("name").equals(stret.get("name"))) {
                        stexist = true;
                    }
                }
                if (stexist == false) {
                    data.add(stret);
                }


                InternalNode end = (InternalNode) p.end();
                Map<String, Object> endtest = end.asMap();
                Map<String, String> endret = new HashMap<>();
                for (Map.Entry<String, Object> entry : endtest.entrySet()) {
                    endret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> endlabels = (List<String>) end.labels();
                String endq = endlabels.get(0);
                if (endlabels.get(0).equals("PAPER")) {
                    endret.put("category", "0");
                } else if (endlabels.get(0).equals("AUTHOR")) {
                    endret.put("category", "1");
                } else if (endlabels.get(0).equals("INTEREST")) {
                    endret.put("category", "2");
                } else if (endlabels.get(0).equals("AFFILIATION")) {
                    endret.put("category", "3");
                } else if (endlabels.get(0).equals("VENUE")) {
                    endret.put("category", "4");
                }
                boolean endisEmpty = endret.containsKey("name");
                if (endisEmpty == true) {
                    String endtruename = endret.get("name");
                    endret.put("truename", endtruename);
                }
                long endtempid = end.id();
                String endid = String.valueOf(endtempid);
                endret.put("name", endid);
                boolean endexist = false;
                for (Map<String, String> newMap : data) {
                    if (newMap.get("name").equals(endret.get("name"))) {
                        endexist = true;
                    }
                }
                if (endexist == false) {
                    data.add(endret);
                }


                InternalRelationship relationship = (InternalRelationship) p.relationship();
                Map<String, String> link = new HashMap<>();
                long startNodeId = relationship.startNodeId();
                long endNodeId = relationship.endNodeId();
                String type = relationship.type();
                link.put("source", String.valueOf(startNodeId));
                link.put("target", String.valueOf(endNodeId));
                link.put("type", type);
                links.add(link);

            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("links", links);
        return result;

    }

    @Cacheable(key = "'a-a-relation-index1-' + #p0 + '-index2-' + #p1")
    public Map<String, Object> getallauandau(String index1, String index2) {
        List<Map<String, String>> data = new ArrayList<>();
        List<Map<String, String>> links = new ArrayList<>();
        List<Map<String, Object>> allttestnode = authorRepository.findAllaandanode(index1, index2);
        for (Map<String, Object> temp : allttestnode) {
            InternalPath.SelfContainedSegment[] ps = (InternalPath.SelfContainedSegment[]) temp.get("p");
            for (InternalPath.SelfContainedSegment p : ps) {
                InternalNode st = (InternalNode) p.start();
                Map<String, Object> sttest = st.asMap();
                Map<String, String> stret = new HashMap<>();
                for (Map.Entry<String, Object> entry : sttest.entrySet()) {
                    stret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> labels = (List<String>) st.labels();
                String q = labels.get(0);
                if (labels.get(0).equals("PAPER")) {
                    stret.put("category", "0");
                } else if (labels.get(0).equals("AUTHOR")) {
                    stret.put("category", "1");
                } else if (labels.get(0).equals("INTEREST")) {
                    stret.put("category", "2");
                } else if (labels.get(0).equals("AFFILIATION")) {
                    stret.put("category", "3");
                } else if (labels.get(0).equals("VENUE")) {
                    stret.put("category", "4");
                }
                boolean isEmpty = stret.containsKey("name");
                if (isEmpty == true) {
                    String truename = stret.get("name");
                    stret.put("truename", truename);
                }
                long tempid = st.id();
                String id = String.valueOf(tempid);
                stret.put("name", id);
                boolean stexist = false;
                for (Map<String, String> newMap : data) {
                    if (newMap.get("name").equals(stret.get("name"))) {
                        stexist = true;
                    }
                }
                if (stexist == false) {
                    data.add(stret);
                }


                InternalNode end = (InternalNode) p.end();
                Map<String, Object> endtest = end.asMap();
                Map<String, String> endret = new HashMap<>();
                for (Map.Entry<String, Object> entry : endtest.entrySet()) {
                    endret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> endlabels = (List<String>) end.labels();
                String endq = endlabels.get(0);
                if (endlabels.get(0).equals("PAPER")) {
                    endret.put("category", "0");
                } else if (endlabels.get(0).equals("AUTHOR")) {
                    endret.put("category", "1");
                } else if (endlabels.get(0).equals("INTEREST")) {
                    endret.put("category", "2");
                } else if (endlabels.get(0).equals("AFFILIATION")) {
                    endret.put("category", "3");
                } else if (endlabels.get(0).equals("VENUE")) {
                    endret.put("category", "4");
                }
                boolean endisEmpty = endret.containsKey("name");
                if (endisEmpty == true) {
                    String endtruename = endret.get("name");
                    endret.put("truename", endtruename);
                }
                long endtempid = end.id();
                String endid = String.valueOf(endtempid);
                endret.put("name", endid);
                boolean endexist = false;
                for (Map<String, String> newMap : data) {
                    if (newMap.get("name").equals(endret.get("name"))) {
                        endexist = true;
                    }
                }
                if (endexist == false) {
                    data.add(endret);
                }


                InternalRelationship relationship = (InternalRelationship) p.relationship();
                Map<String, String> link = new HashMap<>();
                long startNodeId = relationship.startNodeId();
                long endNodeId = relationship.endNodeId();
                String type = relationship.type();
                link.put("source", String.valueOf(startNodeId));
                link.put("target", String.valueOf(endNodeId));
                link.put("type", type);
                links.add(link);

            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("links", links);
        return result;
    }

    @Cacheable(key = "'p-p-relation-index1-' + #p0 + '-index2-' + #p1")
    public Map<String, Object> getallpaandpa(String index1, String index2) {
        List<Map<String, String>> data = new ArrayList<>();
        List<Map<String, String>> links = new ArrayList<>();
        List<Map<String, Object>> allttestnode = paperRepository.findpandpnode(index1, index2);
        for (Map<String, Object> temp : allttestnode) {
            InternalPath.SelfContainedSegment[] ps = (InternalPath.SelfContainedSegment[]) temp.get("p");
            for (InternalPath.SelfContainedSegment p : ps) {
                InternalNode st = (InternalNode) p.start();
                Map<String, Object> sttest = st.asMap();
                Map<String, String> stret = new HashMap<>();
                for (Map.Entry<String, Object> entry : sttest.entrySet()) {
                    stret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> labels = (List<String>) st.labels();
                String q = labels.get(0);
                if (labels.get(0).equals("PAPER")) {
                    stret.put("category", "0");
                } else if (labels.get(0).equals("AUTHOR")) {
                    stret.put("category", "1");
                } else if (labels.get(0).equals("INTEREST")) {
                    stret.put("category", "2");
                } else if (labels.get(0).equals("AFFILIATION")) {
                    stret.put("category", "3");
                } else if (labels.get(0).equals("VENUE")) {
                    stret.put("category", "4");
                }
                boolean isEmpty = stret.containsKey("name");
                if (isEmpty == true) {
                    String truename = stret.get("name");
                    stret.put("truename", truename);
                }
                long tempid = st.id();
                String id = String.valueOf(tempid);
                stret.put("name", id);
                boolean stexist = false;
                for (Map<String, String> newMap : data) {
                    if (newMap.get("name").equals(stret.get("name"))) {
                        stexist = true;
                    }
                }
                if (stexist == false) {
                    data.add(stret);
                }


                InternalNode end = (InternalNode) p.end();
                Map<String, Object> endtest = end.asMap();
                Map<String, String> endret = new HashMap<>();
                for (Map.Entry<String, Object> entry : endtest.entrySet()) {
                    endret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> endlabels = (List<String>) end.labels();
                String endq = endlabels.get(0);
                if (endlabels.get(0).equals("PAPER")) {
                    endret.put("category", "0");
                } else if (endlabels.get(0).equals("AUTHOR")) {
                    endret.put("category", "1");
                } else if (endlabels.get(0).equals("INTEREST")) {
                    endret.put("category", "2");
                } else if (endlabels.get(0).equals("AFFILIATION")) {
                    endret.put("category", "3");
                } else if (endlabels.get(0).equals("VENUE")) {
                    endret.put("category", "4");
                }
                boolean endisEmpty = endret.containsKey("name");
                if (endisEmpty == true) {
                    String endtruename = endret.get("name");
                    endret.put("truename", endtruename);
                }
                long endtempid = end.id();
                String endid = String.valueOf(endtempid);
                endret.put("name", endid);
                boolean endexist = false;
                for (Map<String, String> newMap : data) {
                    if (newMap.get("name").equals(endret.get("name"))) {
                        endexist = true;
                    }
                }
                if (endexist == false) {
                    data.add(endret);
                }


                InternalRelationship relationship = (InternalRelationship) p.relationship();
                Map<String, String> link = new HashMap<>();
                long startNodeId = relationship.startNodeId();
                long endNodeId = relationship.endNodeId();
                String type = relationship.type();
                link.put("source", String.valueOf(startNodeId));
                link.put("target", String.valueOf(endNodeId));
                link.put("type", type);
                links.add(link);

            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("links", links);
        return result;
    }

    @Cacheable(key = "'p-a-relation-index1-' + #p0 + '-index2-' + #p1")
    public Map<String, Object> getallpaandpafour(String index1, String index2) {
        List<Map<String, String>> data = new ArrayList<>();
        List<Map<String, String>> links = new ArrayList<>();
        List<Map<String, Object>> allttestnode = authorRepository.findAllaandpnode(index1, index2);
        for (Map<String, Object> temp : allttestnode) {
            InternalPath.SelfContainedSegment[] ps = (InternalPath.SelfContainedSegment[]) temp.get("p");
            for (InternalPath.SelfContainedSegment p : ps) {
                InternalNode st = (InternalNode) p.start();
                Map<String, Object> sttest = st.asMap();
                Map<String, String> stret = new HashMap<>();
                for (Map.Entry<String, Object> entry : sttest.entrySet()) {
                    stret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> labels = (List<String>) st.labels();
                String q = labels.get(0);
                if (labels.get(0).equals("PAPER")) {
                    stret.put("category", "0");
                } else if (labels.get(0).equals("AUTHOR")) {
                    stret.put("category", "1");
                } else if (labels.get(0).equals("INTEREST")) {
                    stret.put("category", "2");
                } else if (labels.get(0).equals("AFFILIATION")) {
                    stret.put("category", "3");
                } else if (labels.get(0).equals("VENUE")) {
                    stret.put("category", "4");
                }
                boolean isEmpty = stret.containsKey("name");
                if (isEmpty == true) {
                    String truename = stret.get("name");
                    stret.put("truename", truename);
                }
                long tempid = st.id();
                String id = String.valueOf(tempid);
                stret.put("name", id);
                boolean stexist = false;
                for (Map<String, String> newMap : data) {
                    if (newMap.get("name").equals(stret.get("name"))) {
                        stexist = true;
                    }
                }
                if (stexist == false) {
                    data.add(stret);
                }


                InternalNode end = (InternalNode) p.end();
                Map<String, Object> endtest = end.asMap();
                Map<String, String> endret = new HashMap<>();
                for (Map.Entry<String, Object> entry : endtest.entrySet()) {
                    endret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> endlabels = (List<String>) end.labels();
                String endq = endlabels.get(0);
                if (endlabels.get(0).equals("PAPER")) {
                    endret.put("category", "0");
                } else if (endlabels.get(0).equals("AUTHOR")) {
                    endret.put("category", "1");
                } else if (endlabels.get(0).equals("INTEREST")) {
                    endret.put("category", "2");
                } else if (endlabels.get(0).equals("AFFILIATION")) {
                    endret.put("category", "3");
                } else if (endlabels.get(0).equals("VENUE")) {
                    endret.put("category", "4");
                }
                boolean endisEmpty = endret.containsKey("name");
                if (endisEmpty == true) {
                    String endtruename = endret.get("name");
                    endret.put("truename", endtruename);
                }
                long endtempid = end.id();
                String endid = String.valueOf(endtempid);
                endret.put("name", endid);
                boolean endexist = false;
                for (Map<String, String> newMap : data) {
                    if (newMap.get("name").equals(endret.get("name"))) {
                        endexist = true;
                    }
                }
                if (endexist == false) {
                    data.add(endret);
                }


                InternalRelationship relationship = (InternalRelationship) p.relationship();
                Map<String, String> link = new HashMap<>();
                long startNodeId = relationship.startNodeId();
                long endNodeId = relationship.endNodeId();
                String type = relationship.type();
                link.put("source", String.valueOf(startNodeId));
                link.put("target", String.valueOf(endNodeId));
                link.put("type", type);
                links.add(link);

            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("links", links);
        return result;
    }

    @Cacheable(key = "'id-relation-index-' + #p0")
    public Map<String, Object> getallid(Long index) {
        List<Map<String, String>> data = new ArrayList<>();
        List<Map<String, String>> links = new ArrayList<>();
        List<Map<String, Object>> allttestnode = authorRepository.findAllidnode(index);
        for (Map<String, Object> temp : allttestnode) {
            InternalPath.SelfContainedSegment[] ps = (InternalPath.SelfContainedSegment[]) temp.get("p");
            for (InternalPath.SelfContainedSegment p : ps) {
                InternalNode st = (InternalNode) p.start();
                Map<String, Object> sttest = st.asMap();
                Map<String, String> stret = new HashMap<>();
                for (Map.Entry<String, Object> entry : sttest.entrySet()) {
                    stret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> labels = (List<String>) st.labels();
                String q = labels.get(0);
                if (labels.get(0).equals("PAPER")) {
                    stret.put("category", "0");
                } else if (labels.get(0).equals("AUTHOR")) {
                    stret.put("category", "1");
                } else if (labels.get(0).equals("INTEREST")) {
                    stret.put("category", "2");
                } else if (labels.get(0).equals("AFFILIATION")) {
                    stret.put("category", "3");
                } else if (labels.get(0).equals("VENUE")) {
                    stret.put("category", "4");
                }
                boolean isEmpty = stret.containsKey("name");
                if (isEmpty == true) {
                    String truename = stret.get("name");
                    stret.put("truename", truename);
                }
                long tempid = st.id();
                String id = String.valueOf(tempid);
                stret.put("name", id);
                boolean stexist = false;
                for (Map<String, String> newMap : data) {
                    if (newMap.get("name").equals(stret.get("name"))) {
                        stexist = true;
                    }
                }
                if (stexist == false) {
                    data.add(stret);
                }


                InternalNode end = (InternalNode) p.end();
                Map<String, Object> endtest = end.asMap();
                Map<String, String> endret = new HashMap<>();
                for (Map.Entry<String, Object> entry : endtest.entrySet()) {
                    endret.put(entry.getKey(), (String) entry.getValue());
                }
                List<String> endlabels = (List<String>) end.labels();
                String endq = endlabels.get(0);
                if (endlabels.get(0).equals("PAPER")) {
                    endret.put("category", "0");
                } else if (endlabels.get(0).equals("AUTHOR")) {
                    endret.put("category", "1");
                } else if (endlabels.get(0).equals("INTEREST")) {
                    endret.put("category", "2");
                } else if (endlabels.get(0).equals("AFFILIATION")) {
                    endret.put("category", "3");
                } else if (endlabels.get(0).equals("VENUE")) {
                    endret.put("category", "4");
                }
                boolean endisEmpty = endret.containsKey("name");
                if (endisEmpty == true) {
                    String endtruename = endret.get("name");
                    endret.put("truename", endtruename);
                }
                long endtempid = end.id();
                String endid = String.valueOf(endtempid);
                endret.put("name", endid);
                boolean endexist = false;
                for (Map<String, String> newMap : data) {
                    if (newMap.get("name").equals(endret.get("name"))) {
                        endexist = true;
                    }
                }
                if (endexist == false) {
                    data.add(endret);
                }


                InternalRelationship relationship = (InternalRelationship) p.relationship();
                Map<String, String> link = new HashMap<>();
                long startNodeId = relationship.startNodeId();
                long endNodeId = relationship.endNodeId();
                String type = relationship.type();
                link.put("source", String.valueOf(startNodeId));
                link.put("target", String.valueOf(endNodeId));
                link.put("type", type);
                links.add(link);

            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("data", data);
        result.put("links", links);
        return result;
    }

}
