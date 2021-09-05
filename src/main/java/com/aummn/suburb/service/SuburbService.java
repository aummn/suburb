package com.aummn.suburb.service;

import com.aummn.suburb.resource.dto.request.SuburbWebRequest;
import com.aummn.suburb.service.dto.request.SuburbServiceRequest;
import com.aummn.suburb.service.dto.response.SuburbServiceResponse;

import io.reactivex.Single;

import java.util.List;

/**
 * Suburb service class for retrieving suburb info from back-end repository.
 *
 * @author James Jin
 * 
 */
public interface SuburbService {
    Single<Long> addSuburb(SuburbServiceRequest suburbServiceRequest);

    Single<List<SuburbServiceResponse>> getSuburbDetailByPostcode(String postcode);

    Single<List<SuburbServiceResponse>> getSuburbDetailByName(String name);

}
