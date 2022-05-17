package com.bibackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RelationshipEntity(type = "WRITE")
public class CO_AUTHOR implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private Integer count;
}
