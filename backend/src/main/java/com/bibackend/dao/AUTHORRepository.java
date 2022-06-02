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



    @Query("MATCH p=(author1:AUTHOR)-[*..5]-(author2:AUTHOR) WHERE author1.index=$index1 AND author2.index=$index2 RETURN p")
    List<Map<String,Object>> findAllaandanode(@Param("index1") String index1,@Param("index2") String index2);


    @Query("MATCH p=(author:AUTHOR)-[*..5]-(paper:PAPER) WHERE author.index=$index1 AND paper.index=$index2 RETURN p")
    List<Map<String,Object>> findAllaandpnode(@Param("index1") String index1,@Param("index2") String index2);

}
