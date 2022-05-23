package com.bibackend.dao;

import com.bibackend.entity.AFFILIATION;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface AFFILIATIONRepository extends Neo4jRepository<AFFILIATION,Long> {


    @Query("MATCH (interest:INTEREST{name:$interestName})<-[r1:has_interest]-(author:AUTHOR)-[r2:belong_to]->(affiliation:AFFILIATION)  \n" +
            "with interest,affiliation,count(author) as ct " +
            "return affiliation,ct " +
            "order by ct desc " +
            "limit 10 ")
    List<Map<String, Object>> getKeyAffiliation(@Param("interestName") String interestName);



}
