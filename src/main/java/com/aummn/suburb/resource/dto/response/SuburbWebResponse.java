package com.aummn.suburb.resource.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SuburbWebResponse {
    private Long id;
    private String name;
    private String postcode;
}
