package com.aummn.suburb.resource;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitParamsRunner.class)
public class SuburbResourceJUnitParamsTest {

	SuburbResource suburbResource;
	
	@Before 
	public void setup() {
		 suburbResource = new SuburbResource();
	}
	
    @Test
    @Parameters({
        "20,           false",
        "Chris'park,   false",
        "Chris-park,   false",
        "mel2_222,     false",
        "m,            false",
        ",             false",
        " 20020,        false",
        " 20 ,          false",
        " 20 01,        false"
    })
	public void shouldReturnPostcodeValidity(String postcode, boolean isValid) {
    	try {
		    suburbResource.getSuburbDetailByPostcode(postcode);
    	} catch(IllegalArgumentException iae) {
    		assertThat(iae).hasMessageContaining("invalid postcode");
    		assertThat(isValid).isFalse();
    	}
    }
    

    @Test
    @Parameters({
            "mel2_222,            false",
            ",                    false",
            ",                    false",
            "1#,                  false"
    })
	public void shouldReturnSuburbNameValidity(String suburbName, boolean isValid) {
    	try {
		    suburbResource.getSuburbDetailByName(suburbName);
    	} catch(IllegalArgumentException iae) {
    		assertThat(iae).hasMessageContaining("invalid name");
    		assertThat(isValid).isFalse();
    	}
    }
    
    
    

}