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
@NodeEntity(label = "AUTHOR")
public class AUTHOR implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String Hindex;

    @Property
    private String Pindex;

    @Property
    private String UPindex;

    @Property
    private String affiliations;

    @Property
    private String index;

    @Property
    private String interests;

    @Property
    private String name;

    @Property
    private String publishCount;

    @Property
    private String referenceCount;



}
