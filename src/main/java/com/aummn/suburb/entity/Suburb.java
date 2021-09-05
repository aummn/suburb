package com.aummn.suburb.entity;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Suburb domain object representing a Suburb.
 *
 * @author James Jin
 *
 */
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
    
    @Column(name = "postcode")
    private String postcode;

    public Suburb(final String name, final String postcode) {
        this.name = name;
        this.postcode = postcode;
    }
}
