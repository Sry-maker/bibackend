package com.bibackend.dao;


import com.bibackend.entity.PUBLISH;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PUBLISHRepository extends Neo4jRepository<PUBLISH,Long> {
}
