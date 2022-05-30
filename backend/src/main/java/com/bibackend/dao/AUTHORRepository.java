package com.bibackend.dao;


import com.bibackend.entity.AUTHOR;
import io.swagger.models.auth.In;
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


    //   查询作者其关联的所有关系和关联实体（write）
    @Query("match (author:AUTHOR)-[:write]->(paper:PAPER) where author.index=$index return author.name,paper.title")
    List<Map<String,Object>> findAllwritenode(@Param("index") String index);

    //   查询作者其关联的所有关系和关联实体（has_interest）
    @Query("match (author:AUTHOR)-[:has_interest]->(interest:INTEREST) where author.index=$index return author.name,interest.name")
    List<Map<String,Object>> findAllhas_interestnode(@Param("index") String index);

    //   查询作者其关联的所有关系和关联实体（coauthor）
    @Query("match (author:AUTHOR)<-[:WRITE]-(coauthor:AUTHOR) where author.index=$index return author.name,coauthor.name")
    List<Map<String,Object>> findAllcoauthornode(@Param("index") String index);



    //   查询有共同作者的paper
    @Query("MATCH (paper1:PAPER)-[:write]-(author:AUTHOR)-[:write]-(paper2:PAPER) WHERE paper1.index=$index1 AND paper2.index=$index2 RETURN paper1.title,paper2.title,author.name limit 25")
    List<Map<String,Object>> findauthorRelation(@Param("index1") String index1,@Param("index2") String index2);
}
