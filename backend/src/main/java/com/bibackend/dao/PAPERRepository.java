package com.bibackend.dao;

import com.bibackend.entity.PAPER;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface PAPERRepository extends Neo4jRepository<PAPER,Long> {
    //   查询paper其关联的所有关系和关联实体（被引用别人）
    @Query("match (referedpaper:PAPER)<-[:refer]-(paper:PAPER) where referedpaper.index=$index return referedpaper.title,paper.title")
    List<Map<String,Object>> findAllreferednode(@Param("index") String index);
    //   查询paper其关联的所有关系和关联实体（引用别人）
    @Query("match (paper:PAPER)-[:refer]->(referedpaper:PAPER) where paper.index=$index return paper.title,referedpaper.title")
    List<Map<String,Object>> findAllrefernode(@Param("index") String index);
}
