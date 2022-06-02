package com.bibackend.dao;

import com.bibackend.entity.PAPER;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PAPERRepository extends Neo4jRepository<PAPER,Long> {

    //   查询paper其关联的所有关系和关联实体
    @Query("match p=(paper:PAPER)-[]-() where paper.index=$index return p")
    List<Map<String,Object>> findAllonenode(@Param("index") String index);


    @Query("MATCH p=(paper1:PAPER)-[*..5]-(paper2:PAPER) WHERE paper1.index=$index1 AND paper2.index=$index2 RETURN p")
    List<Map<String,Object>> findpandpnode(@Param("index1") String index1,@Param("index2") String index2);
}
