package com.bibackend.dao;

import com.bibackend.entity.INTEREST;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface INTERESTRepository extends Neo4jRepository<INTEREST,Long> {
//    MATCH p=(n:AUTHOR)-[:has_interest]-(i:INTEREST)-[:has_interest]-(m:AUTHOR) where n.index="1243827" and m.index="1340571" RETURN n.name,m.name,i.name limit 25

    //   查询有共同interest的作者
    @Query("MATCH (author1:AUTHOR)-[:has_interest]-(interest:INTEREST)-[:has_interest]-(author2:AUTHOR) WHERE author1.index=$index1 AND author2.index=$index2 RETURN author1.name,author2.name,interest.name limit 25")
    List<Map<String,Object>> findInterestRelation(@Param("index1") String index1,@Param("index2") String index2);


    //   查询有共同interest的作者写的文章，共4跳
    @Query("MATCH (paper1:PAPER)-[:write]-(author1:AUTHOR)-[:has_interest]-(interest:INTEREST)-[:has_interest]-(author2:AUTHOR)-[:write]-(paper2:PAPER) WHERE paper1.index=$index1 AND paper2.index=$index2 RETURN 'paper1-write-author1-has_interest-INTEREST-has_interest-author2-write-paper2',paper1.title,paper2.title,author1.name,author2.name,interest.name limit 25")
    List<Map<String,Object>> findauthorinterestRelation(@Param("index1") String index1,@Param("index2") String index2);


    //

}
