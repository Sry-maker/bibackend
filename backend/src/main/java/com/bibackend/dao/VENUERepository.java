package com.bibackend.dao;

import com.bibackend.entity.VENUE;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface VENUERepository extends Neo4jRepository<VENUE,Long> {
}
