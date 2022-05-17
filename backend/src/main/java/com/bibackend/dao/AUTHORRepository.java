package com.bibackend.dao;


import com.bibackend.entity.AUTHOR;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AUTHORRepository extends Neo4jRepository<AUTHOR,Long> {
}
