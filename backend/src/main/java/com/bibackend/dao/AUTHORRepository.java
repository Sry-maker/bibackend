package com.bibackend.dao;


import com.bibackend.entity.AUTHOR;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AUTHORRepository extends Neo4jRepository<AUTHOR,Long> {

//    match(n:AUTHOR)-[r]->(nn) WHERE n.name = 'Chun-Yen Chang' RETURN n,r,nn



}
