package com.bibackend.dao;

import com.bibackend.entity.AFFILIATION;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface AFFILIATIONRepository extends Neo4jRepository<AFFILIATION,Long> {
}
