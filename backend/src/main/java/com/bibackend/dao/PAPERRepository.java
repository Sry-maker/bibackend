package com.bibackend.dao;

import com.bibackend.entity.PAPER;

import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PAPERRepository extends Neo4jRepository<PAPER,Long> {


}
