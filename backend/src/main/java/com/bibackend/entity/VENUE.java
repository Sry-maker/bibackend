package com.bibackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Property;

import java.io.Serializable;

@Data
@Builder
@NodeEntity(label = "VENUE")
@NoArgsConstructor
@AllArgsConstructor
public class VENUE implements Serializable{
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String name;
}
