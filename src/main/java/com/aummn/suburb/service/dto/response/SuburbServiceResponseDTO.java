package com.aummn.suburb.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuburbServiceResponseDTO {
    private long id;
    private String name;
    private String postcode;
}
