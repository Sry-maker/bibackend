package com.bibackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    @StartNode
    private AUTHOR startAuthor;

    @EndNode
    private AUTHOR endAuthor;
}
