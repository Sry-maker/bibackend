package com.bibackend.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RelationshipEntity(type = "write")
public class WRITE implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private AUTHOR author;

    @EndNode
    private PAPER paper;


}
