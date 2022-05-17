package com.bibackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RelationshipEntity(type = "has_interest")
public class HAS_INTEREST implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
}
