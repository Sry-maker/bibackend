package com.bibackend.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@RelationshipEntity(type = "refer")
public class REFER implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private PAPER startPaper;

    @EndNode
    private PAPER endPaper;
}
