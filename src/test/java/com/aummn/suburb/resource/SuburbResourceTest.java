package com.aummn.suburb.resource;

import com.aummn.suburb.exception.SuburbExistsException;
import com.aummn.suburb.exception.SuburbNotFoundException;
import com.aummn.suburb.resource.dto.request.SuburbWebRequest;
import com.aummn.suburb.service.SuburbService;
import com.aummn.suburb.service.dto.response.SuburbServiceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SuburbResource.class)
public class SuburbResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SuburbService suburbService;

    @Test
    public void givenASuburbRequest_whenAddSuburb_Success_thenReturn201() throws Exception {
        when(suburbService.addSuburb(any()))
                .thenReturn(Single.just(1L));

        MvcResult mvcResult = mockMvc.perform(post("/api/suburb")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new SuburbWebRequest())))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.data", nullValue()));

        verify(suburbService, times(1)).addSuburb(any());
    }

    @Test
    public void givenASuburbRequest_whenAddSuburb_SuburbExist_thenReturn403SuburbConflicts() throws Exception {
        when(suburbService.addSuburb(any()))
                .thenReturn(Single.error(new SuburbExistsException()));

        MvcResult mvcResult = mockMvc.perform(post("/api/suburb")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new SuburbWebRequest())))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error.status", equalTo(HttpStatus.CONFLICT.value())))
                .andExpect(jsonPath("$.data", nullValue()));

        verify(suburbService, times(1)).addSuburb(any());
    }

    @Test
    public void givenAPostcode_whenGetSuburbDetailByPostcode_andGetAListOfSuburbDTO_thenReturn200WithSuburbWebResponses() throws Exception {
        
        SuburbServiceResponse s1dto = new SuburbServiceResponse(1L, "Southbank", "3006");
        SuburbServiceResponse s2dto = new SuburbServiceResponse(2L, "Melbourne", "3000");
        SuburbServiceResponse s3dto = new SuburbServiceResponse(3L, "Carlton", "3001");
        List<SuburbServiceResponse> dtos = Arrays.asList(s1dto, s2dto, s3dto);
        
    	when(suburbService.getSuburbDetailByPostcode(anyString()))
                .thenReturn(Single.just(dtos));

        MvcResult mvcResult = mockMvc.perform(get("/api/suburb/postcode/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.data.length()", equalTo(3)));

        verify(suburbService, times(1)).getSuburbDetailByPostcode(anyString());
    }

    @Test
    public void givenAPostcode_whenGetSuburbDetailByPostcode_andPostcodeNotFound_thenReturn404SuburbNotFound() throws Exception {
        when(suburbService.getSuburbDetailByPostcode(anyString()))
                .thenReturn(Single.error(new SuburbNotFoundException("suburb with postcode [1] not found")));

        MvcResult mvcResult = mockMvc.perform(get("/api/suburb/postcode/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.status", equalTo(404)))
                .andExpect(jsonPath("$.error.message", equalTo("suburb with postcode [1] not found")))
                .andExpect(jsonPath("$.data", nullValue()));

        verify(suburbService, times(1)).getSuburbDetailByPostcode(anyString());
    }
    
    @Test
    public void givenASuburbName_whengetGuburbDetailByName_andGetAListOfSuburbDTO_thenReturn200WithSuburbWebResponses() throws Exception {
        
        SuburbServiceResponse s1dto = new SuburbServiceResponse(1L, "Southbank", "3006");
        SuburbServiceResponse s2dto = new SuburbServiceResponse(2L, "Melbourne", "3000");
        SuburbServiceResponse s3dto = new SuburbServiceResponse(3L, "Carlton", "3001");
        List<SuburbServiceResponse> dtos = Arrays.asList(s1dto, s2dto, s3dto);
        
    	when(suburbService.getSuburbDetailByName(anyString()))
                .thenReturn(Single.just(dtos));

        MvcResult mvcResult = mockMvc.perform(get("/api/suburb/name/Southbank")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error", nullValue()))
                .andExpect(jsonPath("$.data.length()", equalTo(3)));

        verify(suburbService, times(1)).getSuburbDetailByName(anyString());
    }
    
    @Test
    public void givenASuburbName_whenGetSuburbDetailByName_andNameNotFound_thenReturn404SuburbNotFound() throws Exception {
        when(suburbService.getSuburbDetailByName(anyString()))
                .thenReturn(Single.error(new SuburbNotFoundException("suburb with name [Carlton] not found")));

        MvcResult mvcResult = mockMvc.perform(get("/api/suburb/name/Carlton")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.status", equalTo(404)))
                .andExpect(jsonPath("$.error.message", equalTo("suburb with name [Carlton] not found")))
                .andExpect(jsonPath("$.data", nullValue()));

        verify(suburbService, times(1)).getSuburbDetailByName(anyString());
    }

}