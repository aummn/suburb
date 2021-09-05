package com.aummn.suburb.service;

import com.aummn.suburb.entity.Suburb;
import com.aummn.suburb.exception.SuburbExistsException;
import com.aummn.suburb.exception.SuburbNotFoundException;
import com.aummn.suburb.repo.SuburbRepository;
import com.aummn.suburb.resource.dto.request.SuburbWebRequest;
import com.aummn.suburb.service.dto.request.SuburbServiceRequest;
import com.aummn.suburb.service.dto.response.SuburbServiceResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SuburbServiceImplTest {

    @Mock
    private SuburbRepository suburbRepository;

    @InjectMocks
    private SuburbServiceImpl suburbService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void givenANewSuburb_wehnAddSuburb_thenReturnSingleOfAddedSuburbId() {
        when(suburbRepository.findByNameAndPostcodeIgnoreCase(anyString(), anyString()))
                .thenReturn(Optional.empty());
        when(suburbRepository.save(any(Suburb.class)))
                .thenReturn(new Suburb(1L, "Southbank", "3006"));

        suburbService.addSuburb(new SuburbServiceRequest("Southbank", "3006"))
                .test()
                .assertComplete()
                .assertNoErrors()
                .assertValue(1L)
                .awaitTerminalEvent();

        InOrder inOrder = inOrder(suburbRepository);
        inOrder.verify(suburbRepository, times(1)).findByNameAndPostcodeIgnoreCase(anyString(), anyString());
        inOrder.verify(suburbRepository, times(1)).save(any(Suburb.class));
    }

    @Test
    public void givenAnExistingSuburb_whenAddSuburb_andWithSamePostcode_thenThrowSuburbExistsException() {
        when(suburbRepository.findByNameAndPostcodeIgnoreCase(anyString(), anyString()))
                .thenReturn(Optional.of(new Suburb(1L, "Southbank", "3006")));

        suburbService.addSuburb(new SuburbServiceRequest("Southbank", "3006"))
                .test()
                .assertNotComplete()
                .assertError(SuburbExistsException.class)
                .awaitTerminalEvent();

        InOrder inOrder = inOrder(suburbRepository);
        inOrder.verify(suburbRepository, times(1)).findByNameAndPostcodeIgnoreCase(anyString(), anyString());
        inOrder.verify(suburbRepository, never()).save(any(Suburb.class));
    }

    @Test
    public void givenAPostcode_whenGetSuburbDetailByPostcode_SuburbNotFound_thenThrowSuburbNotFoundException() {
        when(suburbRepository.findByPostcode(anyString()))
                .thenReturn(Collections.emptyList());

        suburbService.getSuburbDetailByPostcode("3006")
                .test()
                .assertNotComplete()
                .assertError(SuburbNotFoundException.class)
                .awaitTerminalEvent();

        verify(suburbRepository, times(1)).findByPostcode(anyString());
    }

    @Test
    public void givenAPostcode_whenGetSuburbDetailByPostcode_SuburbFound_thenReturnSuburbs() {
        Suburb s1 = new Suburb(1L, "Southbank", "3006");
        Suburb s2 = new Suburb(2L, "Melbourne", "3000");
        Suburb s3 = new Suburb(3L, "Carlton", "3001");
        List<Suburb> suburbs = Arrays.asList(s1, s2, s3);
        
        SuburbServiceResponse s1dto = new SuburbServiceResponse(1L, "Southbank", "3006");
        SuburbServiceResponse s2dto = new SuburbServiceResponse(2L, "Melbourne", "3000");
        SuburbServiceResponse s3dto = new SuburbServiceResponse(3L, "Carlton", "3001");
        List<SuburbServiceResponse> dtos = Arrays.asList(s1dto, s2dto, s3dto);
        
    	
    	when(suburbRepository.findByPostcode(anyString()))
                .thenReturn(suburbs);

        suburbService.getSuburbDetailByPostcode("3006")
                .test()
                .assertComplete()
                .assertNoErrors()
                .assertValues(dtos)
                .awaitTerminalEvent();

        verify(suburbRepository, times(1)).findByPostcode(anyString());

    }
    
    @Test
    public void givenASuburbName_whenGetSuburbDetailByName_SuburbNotFound_thenThrowSuburbNotFoundException() {
        when(suburbRepository.findByNameIgnoreCase(anyString()))
                .thenReturn(Collections.emptyList());

        suburbService.getSuburbDetailByName("Southbank")
                .test()
                .assertNotComplete()
                .assertError(SuburbNotFoundException.class)
                .awaitTerminalEvent();

        verify(suburbRepository, times(1)).findByNameIgnoreCase(anyString());
    }
    
    @Test
    public void givenASuburbName_whenGetSuburbDetailByName_SuburbFound_thenReturnSuburbs() {
        Suburb s1 = new Suburb(1L, "Southbank", "3006");
        Suburb s2 = new Suburb(2L, "Melbourne", "3000");
        Suburb s3 = new Suburb(3L, "Carlton", "3001");
        List<Suburb> suburbs = Arrays.asList(s1, s2, s3);
        
        SuburbServiceResponse s1dto = new SuburbServiceResponse(1L, "Southbank", "3006");
        SuburbServiceResponse s2dto = new SuburbServiceResponse(2L, "Melbourne", "3000");
        SuburbServiceResponse s3dto = new SuburbServiceResponse(3L, "Carlton", "3001");
        List<SuburbServiceResponse> dtos = Arrays.asList(s1dto, s2dto, s3dto);
        
    	
    	when(suburbRepository.findByNameIgnoreCase(anyString()))
                .thenReturn(suburbs);

        suburbService.getSuburbDetailByName("Carlton")
                .test()
                .assertComplete()
                .assertNoErrors()
                .assertValues(dtos)
                .awaitTerminalEvent();

        verify(suburbRepository, times(1)).findByNameIgnoreCase(anyString());

    }
    
    @Test
    public void givenASuburbId_whenGetSuburbById_SuburbNotFound_thenThrowSuburbNotFoundException() {
        when(suburbRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        suburbService.getSuburbById(1L)
                .test()
                .assertNotComplete()
                .assertError(SuburbNotFoundException.class)
                .awaitTerminalEvent();

        verify(suburbRepository, times(1)).findById(anyLong());
    }
    
    @Test
    public void givenASuburbId_whenGetSuburbById_SuburbFound_thenReturnSuburb() {
        Suburb s1 = new Suburb(1L, "Southbank", "3006");
        SuburbServiceResponse s1dto = new SuburbServiceResponse(1L, "Southbank", "3006");       
    	
    	when(suburbRepository.findById(anyLong()))
                .thenReturn(Optional.of(s1));

        suburbService.getSuburbById(1L)
                .test()
                .assertComplete()
                .assertNoErrors()
                .assertValue(s1dto)
                .awaitTerminalEvent();

        verify(suburbRepository, times(1)).findById(anyLong());

    }    
    
}