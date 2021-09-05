package com.aummn.suburb.validator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class SuburbValidatorJUnitParamsTest {

    @Test
    @Parameters({
            "melbourne,    true",
            "mel2,         true",
            "Chris'park,   true",
            "Chris-park,   true",
            "mel2_222,     false",
            " m,            true",
            ",             false",
            "mel2 syd qld, true",
            "  perth underline's park , true",
            "mel2 syd qld-,      true",
            "  perth underlineP1 @, false"
    })
	public void shouldReturnSuburbNameIsValid(String suburbName, boolean isValid) {
		FieldValidationResult result = SuburbValidator.validateSuburbName(suburbName);
		assertThat(result.isValid()).isEqualTo(isValid);
    }
    
    
    @Test
    @Parameters({
            "1000,         true",
            "20,           false",
            "Chris'park,   false",
            "Chris-park,   false",
            "mel2_222,     false",
            "m,            false",
            ",             false",
            "10001,        false",
            "  2000,        true",
            " 20020,        false",
            " 20 ,          false",
            " 2090   ,      true",
            " 20 01,        false"
    })
	public void shouldReturnPostcodeIsValid(String postcode, boolean isValid) {
		FieldValidationResult result = SuburbValidator.validatePostcode(postcode);
		assertThat(result.isValid()).isEqualTo(isValid);
    }
    
    @Test
    @Parameters({
            "1000,         true",
            "20,           true",
            "10001,        true",
            "  2000,        true",
            " 20020,        true",
            " 20 ,          true",
            " 2090   ,      true",
            "-1,            false",
            "0,             false"
    })
	public void shouldReturnSuburbIdIsValid(Long id, boolean isValid) {
		FieldValidationResult result = SuburbValidator.validateSuburbId(id);
		assertThat(result.isValid()).isEqualTo(isValid);
    }
}