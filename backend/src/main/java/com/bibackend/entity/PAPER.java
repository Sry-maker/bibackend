package com.bibackend.entity;


import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;

import java.io.Serializable;

@Data
@Builder
@NodeEntity(label = "PAPER")
public class PAPER implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String affiliations;

    @Property
    private String authors;

    @Property
    private String index;

    @Property
    private String title;

    @Property
    private String venue;

    @Property
    private String year;



}
