package com.aummn.suburb.service;

import io.reactivex.Maybe;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aummn.suburb.entity.Suburb;
import com.aummn.suburb.exception.SuburbExistsException;
import com.aummn.suburb.exception.SuburbNotFoundException;
import com.aummn.suburb.repo.SuburbRepository;
import com.aummn.suburb.service.dto.request.SuburbServiceRequest;
import com.aummn.suburb.service.dto.response.SuburbServiceResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * The Suburb service implementation class for retrieving suburb info from back-end repository.
 *
 * @author James Jin
 * 
 */
@Slf4j
@Service
public class SuburbServiceImpl implements SuburbService {

    @Autowired
    private SuburbRepository suburbRepository;


    /**
     * Get Suburb details by post code
     * 
     * @param postcode the post code of a suburb
     * 
     * @return a list containing suburb info
     * 
     * @throws SuburbNotFoundException if the corresponding suburb not found
     * 
     */
    @Override
    public Single<List<SuburbServiceResponse>> getSuburbDetailByPostcode(String postcode) {
        return findSuburbDetailByPostcode(postcode)
                .map(this::toSuburbServiceResponseList);
    }

    private Single<List<Suburb>> findSuburbDetailByPostcode(String postcode) {
        return Single.create(singleSubscriber -> {
            List<Suburb> suburbs = suburbRepository.findByPostcode(postcode);
            if(suburbs.isEmpty()) {
            	String errorMsg = new StringBuilder().append("suburb with postcode [").append(postcode).append("] not found").toString();
            	singleSubscriber.onError(new SuburbNotFoundException(errorMsg));
            } else {
            	singleSubscriber.onSuccess(suburbs);
            }
        });
    }
    
    /**
     * Get Suburb details by suburb name
     * 
     * @param name the name of a suburb
     * 
     * @return a list containing suburb info
     * 
     * @throws SuburbNotFoundException if the corresponding suburb not found
     * 
     */
    @Override
    public Single<List<SuburbServiceResponse>> getSuburbDetailByName(String name) {
        return findSuburbDetailByName(name)
                .map(this::toSuburbServiceResponseList);
    }

    private Single<List<Suburb>> findSuburbDetailByName(String name) {
        return Single.create(singleSubscriber -> {
            List<Suburb> suburbs = suburbRepository.findByNameIgnoreCase(name);
            if(suburbs.isEmpty()) {
            	String errorMsg = new StringBuilder().append("suburb with name [").append(name).append("] not found").toString();
            	singleSubscriber.onError(new SuburbNotFoundException(errorMsg));
            } else {
            	singleSubscriber.onSuccess(suburbs);
            }
            
        });
    }
    
    private List<SuburbServiceResponse> toSuburbServiceResponseList(List<Suburb> suburbs) {
        return suburbs
                .stream()
                .map(this::toSuburbServiceResponse)
                .collect(Collectors.toList());
    }

    private SuburbServiceResponse toSuburbServiceResponse(Suburb suburb) {
        SuburbServiceResponse suburbServiceResponseDTO = new SuburbServiceResponse();
        BeanUtils.copyProperties(suburb, suburbServiceResponseDTO);
        return suburbServiceResponseDTO;
    }
	
    /**
     * Get Suburb details by id
     * 
     * @param id the id of a suburb
     * 
     * @return suburb info if found 
     * 
     * @throws SuburbNotFoundException if the corresponding suburb not found
     * 
     */
    @Override
    public Single<SuburbServiceResponse> getSuburbById(Long id) {
        return findSuburbDetailById(id)
                .map(this::toSuburbServiceResponse);
    }

    private Single<Suburb> findSuburbDetailById(Long id) {
        return Single.create(singleSubscriber -> {
        	Optional<Suburb> suburbOptional = suburbRepository.findById(id);
            if(!suburbOptional.isPresent()) {
            	String errorMsg = new StringBuilder().append("suburb with id [").append(id).append("] not found").toString();
            	singleSubscriber.onError(new SuburbNotFoundException(errorMsg));
            } else {
            	singleSubscriber.onSuccess(suburbOptional.get());
            }
        });
    }
    
    /**
     * add a suburb
     * 
     * @param suburbWebRequestDTO a DTO object containing suburb info
     * 
     * @return the id of newly added suburb
     * 
     * @throws SuburbExistsException if the suburb already exists
     * 
     */
    @Override
    public Single<Long> addSuburb(SuburbServiceRequest suburbServiceRequest) {
        return saveSuburbToRepository(suburbServiceRequest);
    }

    private Single<Long> saveSuburbToRepository(SuburbServiceRequest suburbServiceRequest) {
        return Single.create(singleSubscriber -> {
            Optional<Suburb> suburbOptional = suburbRepository.findByNameAndPostcodeIgnoreCase(suburbServiceRequest.getName(), suburbServiceRequest.getPostcode());
            // throw error if suburb entry exists already
            if (suburbOptional.isPresent())
                singleSubscriber.onError(new SuburbExistsException());
            else {
            	// create new suburb entry if no entry with same name and post code
                Long addedSuburbId = suburbRepository.save(toSuburb(suburbServiceRequest)).getId();
                singleSubscriber.onSuccess(addedSuburbId);
            }
        });
    }

    private Suburb toSuburb(SuburbServiceRequest suburbServiceRequest) {
    	Suburb suburb = new Suburb();
        BeanUtils.copyProperties(suburbServiceRequest, suburb);
        return suburb;
    }

}
