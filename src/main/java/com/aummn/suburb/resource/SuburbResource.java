package com.aummn.suburb.resource;

import com.aummn.suburb.resource.dto.request.SuburbWebRequest;
import com.aummn.suburb.resource.dto.response.BaseWebResponse;
import com.aummn.suburb.resource.dto.response.SuburbWebResponse;
import com.aummn.suburb.service.SuburbService;
import com.aummn.suburb.service.dto.request.SuburbServiceRequest;
import com.aummn.suburb.service.dto.response.SuburbServiceResponse;
import com.aummn.suburb.validator.SuburbValidator;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.test.web.client.response.MockRestResponseCreators.withNoContent;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

/**
 * This is the resource class with Suburb API end points for suburb creation and info retrieval.
 *
 * @author James Jin
 * 
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/suburb")
public class SuburbResource {
    
    @Autowired
    private SuburbService suburbService;

    @GetMapping(
            value = "/postcode/{postcode}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
	@ApiOperation(value = "Find Suburb details by a post code",
    notes = "some Suburbs share the same post code, return a list available suburbs")
    public Single<ResponseEntity<BaseWebResponse<List<SuburbWebResponse>>>> getSuburbDetailByPostcode(@PathVariable(value = "postcode") String postcode) {
    	log.debug("getSuburbDetailByPostcode() with postcode : {}", postcode); 
    	boolean isValid = SuburbValidator.validatePostcode(postcode).isValid();
    	if(!isValid) {
    		String errorMsg = String.format("invalid postcode - %s",postcode);
    		throw new IllegalArgumentException(errorMsg);
    	}
    	return suburbService.getSuburbDetailByPostcode(postcode)
                .subscribeOn(Schedulers.io())
                .map(suburbServiceResponseDTOs -> ResponseEntity.ok(BaseWebResponse.successWithData(toSuburbWebResponses(suburbServiceResponseDTOs))));
    }
    
    private List<SuburbWebResponse> toSuburbWebResponses(List<SuburbServiceResponse> suburbServiceResponseDTOs) {
    	return suburbServiceResponseDTOs.stream()
    	.map(dto -> {
        	SuburbWebResponse suburbWebResponse = new SuburbWebResponse();
            BeanUtils.copyProperties(dto, suburbWebResponse);
            return suburbWebResponse;
    	}).collect(Collectors.toList());
    }

    @GetMapping(
            value = "/name/{name}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
	@ApiOperation(value = "Find post codes by a Suburb name",
    notes = "some Suburbs have 2 or more post codes, this API returns a list available post codes corresponding to the input Suburb name")
    public Single<ResponseEntity<BaseWebResponse<List<String>>>> getSuburbDetailByName(@PathVariable(value = "name") String name) {
    	log.debug("getSuburbDetailByName() with name : {}", name); 
    	boolean isValid = SuburbValidator.validateSuburbName(name).isValid();
    	if(!isValid) {
    		String errorMsg = String.format("invalid name - %s",name);
    		throw new IllegalArgumentException(errorMsg);
    	}
        return suburbService.getSuburbDetailByName(name)
                .subscribeOn(Schedulers.io())
                .map(suburbServiceResponseDTOs -> ResponseEntity.ok(BaseWebResponse.successWithData(toListString(suburbServiceResponseDTOs))));
    }
    
    private List<String> toListString(List<SuburbServiceResponse> suburbServiceResponseDTOs) {
    	return suburbServiceResponseDTOs.stream()
    	.map(dto -> {
            return dto.getPostcode();
    	}).collect(Collectors.toList());
    }
    
    @GetMapping(
            value = "/id/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
	@ApiOperation(value = "Find suburb by id",
    notes = "a Suburb has a unique id")
    public Single<ResponseEntity<BaseWebResponse<SuburbWebResponse>>> getSuburbDetailById(@PathVariable(value = "id") Long id) {
    	log.debug("getSuburbDetailById() with id : {}", id); 
    	boolean isValid = SuburbValidator.validateSuburbId(id).isValid();
    	if(!isValid) {
    		String errorMsg = String.format("invalid id - %s", id);
    		throw new IllegalArgumentException(errorMsg);
    	}
        return suburbService.getSuburbById(id)
                .subscribeOn(Schedulers.io())
                .map(suburbServiceResponse -> ResponseEntity.ok(BaseWebResponse.successWithData(toSuburbWebResponse(suburbServiceResponse))));
    }
    
    private SuburbWebResponse toSuburbWebResponse(SuburbServiceResponse suburbServiceResponse) {
        	SuburbWebResponse suburbWebResponse = new SuburbWebResponse();
            BeanUtils.copyProperties(suburbServiceResponse, suburbWebResponse);
            return suburbWebResponse;
    }
    
    
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(
    		 value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    ) 
	@ApiOperation(value = "Create a Suburb entry",
    notes = "return 201 if successfully created")
    public Single<ResponseEntity<Void>> addSuburb(
        @RequestBody @Valid SuburbWebRequest suburbWebRequest) {
        return suburbService.addSuburb(toSuburbServiceRequest(suburbWebRequest)).subscribeOn(Schedulers.io()).map(
            s -> ResponseEntity.created(URI.create("/api/suburb/id/" + s)).build());
                
    }
    
    private SuburbServiceRequest toSuburbServiceRequest(SuburbWebRequest suburbWebRequest) {
    	SuburbServiceRequest suburbServiceRequest = new SuburbServiceRequest();
        BeanUtils.copyProperties(suburbWebRequest, suburbServiceRequest);
        return suburbServiceRequest;
    }

}
