package com.bibackend.dao;

import com.bibackend.entity.INTEREST;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface INTERESTRepository extends Neo4jRepository<INTEREST,Long> {

}
