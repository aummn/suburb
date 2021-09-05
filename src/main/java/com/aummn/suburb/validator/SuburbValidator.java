package com.aummn.suburb.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SuburbValidator {

	public final static String SUBURB_NAME_PATTERN = "^[a-zA-Z0-9',.\\s-]{1,100}$";
	public final static String POSTCODE_PATTERN = "^[0-9]{4}$";
	
	public static FieldValidationResult validateSuburbName(String name) {
		boolean isValid = false;
		Pattern pattern = Pattern.compile(SUBURB_NAME_PATTERN, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(name);
	    boolean matchFound = matcher.find();
	    if(matchFound) {
	    	if(name.length() >= 1 && name.length() <= 100) {
	    		
	    	}
	      isValid = true;
	    }
		String message = isValid ? "" : "name error";
		return FieldValidationResult.builder().valid(isValid).message(message).build();
	}
	
	public static FieldValidationResult validatePostcode(String postcode) {
		boolean isValid = false;
		Pattern pattern = Pattern.compile(POSTCODE_PATTERN, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(postcode);
	    boolean matchFound = matcher.find();
	    if(matchFound) {
	      isValid = true;
	    }
		String message = isValid ? "" : "post code error";
		return FieldValidationResult.builder().valid(isValid).message(message).build();
	}
}
