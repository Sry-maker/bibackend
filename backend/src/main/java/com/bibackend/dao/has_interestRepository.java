package com.bibackend.dao;

import com.bibackend.entity.HAS_INTEREST;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface HAS_INTERESTRepository extends Neo4jRepository<HAS_INTEREST,Long> {
}
