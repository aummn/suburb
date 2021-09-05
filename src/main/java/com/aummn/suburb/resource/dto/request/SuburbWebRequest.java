package com.aummn.suburb.resource.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.aummn.suburb.validator.SuburbValidator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SuburbWebRequest {
	@NotNull
	@Length(min=1, max=100)
	@Pattern(regexp=SuburbValidator.SUBURB_NAME_PATTERN)
    private String name;
    
    @NotNull
    @Pattern(regexp=SuburbValidator.POSTCODE_PATTERN)
    private String postcode;
}
