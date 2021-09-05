package com.aummn.suburb.validator;


import lombok.Data;
import lombok.Builder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FieldValidationResult {
	private boolean valid = false;
	private String message;
}
