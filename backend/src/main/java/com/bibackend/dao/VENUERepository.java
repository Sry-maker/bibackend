package com.bibackend.dao;

import com.bibackend.entity.VENUE;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface VENUERepository extends Neo4jRepository<VENUE,Long> {
    @Query("match p=(interest:INTEREST{name:$interestName})<-[r1:has_interest]-(author:AUTHOR)-[r2:write]->(paper:PAPER)<-[r3:publish]-(venue:VENUE) " +
            "where $tag or $year in labels(paper) " +
            "with venue, count(distinct paper) as paperCount " +
            "return venue,paperCount " +
            "order by paperCount desc " +
            "limit 15 ")
    //tag 用来短路
    List<Map<String, Object>> getKeyVenue(@Param("interestName") String interestName, @Param("year") String year, @Param("tag") boolean tag);

}
