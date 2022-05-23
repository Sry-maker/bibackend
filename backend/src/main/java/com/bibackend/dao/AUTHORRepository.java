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

}
