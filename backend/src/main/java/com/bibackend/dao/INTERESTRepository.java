package com.bibackend.dao;

import com.bibackend.entity.INTEREST;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface INTERESTRepository extends Neo4jRepository<INTEREST,Long> {
}
