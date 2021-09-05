package com.aummn.suburb.resource.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(description="Output Suburb info ")
public class SuburbWebResponse {
    private Long id;
    private String name;
    private String postcode;
}
