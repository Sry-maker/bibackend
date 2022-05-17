package com.bibackend.dao;

import com.bibackend.entity.CO_AUTHOR;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CO_AUTHORRepository extends Neo4jRepository<CO_AUTHOR,Long> {
}
