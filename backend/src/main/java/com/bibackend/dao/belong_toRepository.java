package com.bibackend.dao;
import com.bibackend.entity.BELONG_TO;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BELONG_TORepository  extends Neo4jRepository<BELONG_TO,Long> {
}
