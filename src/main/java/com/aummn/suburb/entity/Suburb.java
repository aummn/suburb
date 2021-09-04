package com.aummn.suburb.entity;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "suburb")
@Getter
@Setter
@NoArgsConstructor
public class Suburb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;
    
    @Column(name = "postCode")
    private String postCode;

    public Suburb(final String name, final String postCode) {
        this.name = name;
        this.postCode = postCode;
    }
}
