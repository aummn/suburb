package com.aummn.suburb.resource.dto.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.aummn.suburb.validator.SuburbValidator;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description="Input Suburb info ")
public class SuburbWebRequest {
	@ApiModelProperty(notes="Name shouldn't be empty and have 1 to 100 characters")
	@NotNull
	@Length(min=1, max=100)
	@Pattern(regexp=SuburbValidator.SUBURB_NAME_PATTERN)
    private String name;
    
	@ApiModelProperty(notes="Post code shouldn't be empty and need to be exact 4 digits")
    @NotNull
    @Pattern(regexp=SuburbValidator.POSTCODE_PATTERN)
    private String postcode;
}
