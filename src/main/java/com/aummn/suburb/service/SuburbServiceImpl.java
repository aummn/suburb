package com.aummn.suburb.service;

import io.reactivex.Single;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aummn.suburb.entity.Suburb;
import com.aummn.suburb.repo.SuburbRepository;
import com.aummn.suburb.resource.dto.request.SuburbWebRequestDTO;
import com.aummn.suburb.service.dto.response.SuburbServiceResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

/**
 * The Suburb service implementation class for retrieving suburb info from back-end repository.
 *
 * @author James Jin
 * 
 */
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
     * @throws EntityNotFoundException if the corresponding suburb not found
     * 
     */
    @Override
    public Single<List<SuburbServiceResponseDTO>> getSuburbDetailByPostcode(String postcode) {
        return findSuburbDetailByPostcode(postcode)
                .map(this::toSuburbServiceResponseDTOList);
    }

    private Single<List<Suburb>> findSuburbDetailByPostcode(String postcode) {
        return Single.create(singleSubscriber -> {
            List<Suburb> suburbs = suburbRepository.findByPostcode(postcode);
            if(suburbs.isEmpty()) {
            	singleSubscriber.onError(new EntityNotFoundException());
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
     * @throws EntityNotFoundException if the corresponding suburb not found
     * 
     */
    @Override
    public Single<List<SuburbServiceResponseDTO>> getSuburbDetailByName(String name) {
        return findSuburbDetailByName(name)
                .map(this::toSuburbServiceResponseDTOList);
    }

    private Single<List<Suburb>> findSuburbDetailByName(String name) {
        return Single.create(singleSubscriber -> {
            List<Suburb> suburbs = suburbRepository.findByName(name);
            if(suburbs.isEmpty()) {
            	singleSubscriber.onError(new EntityNotFoundException());
            } else {
            	singleSubscriber.onSuccess(suburbs);
            }
            
        });
    }
    
    private List<SuburbServiceResponseDTO> toSuburbServiceResponseDTOList(List<Suburb> suburbs) {
        return suburbs
                .stream()
                .map(this::toSuburbServiceResponseDTO)
                .collect(Collectors.toList());
    }

    private SuburbServiceResponseDTO toSuburbServiceResponseDTO(Suburb suburb) {
        SuburbServiceResponseDTO suburbServiceResponseDTO = new SuburbServiceResponseDTO();
        BeanUtils.copyProperties(suburb, suburbServiceResponseDTO);
        return suburbServiceResponseDTO;
    }
	
    /**
     * add a suburb
     * 
     * @param suburbWebRequestDTO a DTO object containing suburb info
     * 
     * @return the id of newly added suburb
     * 
     * @throws EntityExistsException if the suburb already exists
     * 
     */
    @Override
    public Single<Long> addSuburb(SuburbWebRequestDTO suburbWebRequestDTO) {
        return saveSuburbToRepository(suburbWebRequestDTO);
    }

    private Single<Long> saveSuburbToRepository(SuburbWebRequestDTO suburbWebRequestDTO) {
        return Single.create(singleSubscriber -> {
            Optional<Suburb> suburbOptional = suburbRepository.findByNameAndPostcode(suburbWebRequestDTO.getName(), suburbWebRequestDTO.getPostcode());
            // throw error if suburb entry exists already
            if (suburbOptional.isPresent())
                singleSubscriber.onError(new EntityExistsException());
            else {
            	// create new suburb entry if no entry with same name and post code
                Long addedSuburbId = suburbRepository.save(toSuburb(suburbWebRequestDTO)).getId();
                singleSubscriber.onSuccess(addedSuburbId);
            }
        });
    }

    private Suburb toSuburb(SuburbWebRequestDTO suburbWebRequestDTO) {
    	Suburb suburb = new Suburb();
        BeanUtils.copyProperties(suburbWebRequestDTO, suburb);
        return suburb;
    }

}
