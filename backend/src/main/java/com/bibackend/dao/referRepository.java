package com.bibackend.dao;


import com.bibackend.entity.REFER;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface REFERRepository extends Neo4jRepository<REFER,Long> {
}
