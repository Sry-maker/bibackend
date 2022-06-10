package com.bibackend.dao;


import com.bibackend.entity.AUTHOR;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface AUTHORRepository extends Neo4jRepository<AUTHOR,Long> {

//    match(n:AUTHOR)-[r]->(nn) WHERE n.name = 'Chun-Yen Chang' RETURN n,r,nn
    // 查询某个领域的作者
    @Query("match (interest:INTEREST{name:$interestName})<-[r:has_interest]-(author:AUTHOR)" +
            "return author " +
            "limit 15")
    List<AUTHOR> findAuthorByInterest(@Param("interestName") String interestName);



    //   查询作者其关联的所有关系和关联实体
    @Query("match p=(author:AUTHOR)-[]-() where author.index=$index return p")
    List<Map<String,Object>> findAllonetonode(@Param("index") String index);



    @Query("MATCH p=(author1:AUTHOR)-[*..4]-(author2:AUTHOR) WHERE author1.index=$index1 AND author2.index=$index2 RETURN p limit 10")
    List<Map<String,Object>> findAllaandanode(@Param("index1") String index1,@Param("index2") String index2);


    @Query("MATCH p=(author:AUTHOR)-[*..4]-(paper:PAPER) WHERE author.index=$index1 AND paper.index=$index2 RETURN p limit 10")
    List<Map<String,Object>> findAllaandpnode(@Param("index1") String index1,@Param("index2") String index2);

//根据id，返回所有一跳关系
//    MATCH p=(n)-[]-() where id(n)=4118488 RETURN p
    @Query("MATCH (n)-[]-() " +
            "where id(n)=$id " +
            "with count(*) as cnt " +
            "call { " +
            "with cnt " +
            "MATCH p=(n)-[]-()  " +
            "where id(n)=$id " +
            "with p, rand() as r " +
            "where r < 20.0 / cnt " +
            "return p " +
            "limit 20 " +
            "} " +
            "return p")
    List<Map<String,Object>> findAllidnode(@Param("id") Long id);

}
