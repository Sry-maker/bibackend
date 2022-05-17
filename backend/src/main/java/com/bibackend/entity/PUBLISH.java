package com.bibackend.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RelationshipEntity(type = "publish")
public class PUBLISH implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private VENUE venue;

    @EndNode
    private PAPER paper;
}
