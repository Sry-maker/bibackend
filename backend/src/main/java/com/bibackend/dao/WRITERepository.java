package com.bibackend.dao;

import com.bibackend.entity.WRITE;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WRITERepository extends Neo4jRepository<WRITE,Long> {
}
