package com.aummn.suburb.service;

import com.aummn.suburb.entity.Suburb;
import com.aummn.suburb.service.dto.response.SuburbServiceResponseDTO;

import io.reactivex.Single;

import java.util.List;

/**
 * Suburb service class for retrieving suburb info from back-end repository.
 *
 * @author James Jin
 * 
 */
public interface SuburbService {
    Single<String> addSuburb(Suburb suburb);

    Single<List<SuburbServiceResponseDTO>> getSuburbDetailByPostcode(String postcode);

    Single<List<SuburbServiceResponseDTO>> getSuburbDetailByName(String name);

}