package com.datarepublic.simplecab.server.service;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.datarepublic.simplecab.server.service.SimpleCabController;
import com.datarepublic.simplecab.server.service.SimpleCabService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SimpleCabController.class, secure = false)
public class SimpleCabControllerTest  {

    private static final String COUNT_BY_MEDALLION_AND_PICKUP_DATE = "/countByMedallionAndPickupDate";
    private static final String COUNT_BY_MEDALLIONS_AND_PICKUP_DATE = "/countByMedallionsAndPickupDate";
    private static final String CLEAR_CACHE_MAPPING = "/clearCache";
    private static final String LOAD_CSV_MAPPING = "/loadCSV";
    private static final String BASE_CONTEXT = "/cab";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimpleCabService simpleCabService;

    @Before
    public void setup() {
    }

    

    @Test
    public void shouldGetCountByMedallionAndPickupDatetime() throws Exception {
        String medallionId = "abc";
        LocalDate pickupDate = LocalDate.of(2014, Month.JANUARY, 1);
        MockHttpServletResponse response = countByMedallionAndPickupDate(COUNT_BY_MEDALLION_AND_PICKUP_DATE,  medallionId, pickupDate);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
    
    @Test
    public void shouldGetCountByMedallionsAndPickupDatetime() throws Exception {
        LocalDate pickupDate = LocalDate.of(2014, Month.JANUARY, 1);
        String[] medallionIds  = {"abc"};
        MockHttpServletResponse response = countByMedallionsAndPickupDate(COUNT_BY_MEDALLIONS_AND_PICKUP_DATE,  medallionIds, pickupDate);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
    
    @Test
    public void shouldClearCache() throws Exception {
        MockHttpServletResponse response = clearCache(CLEAR_CACHE_MAPPING);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
    
    @Test
    public void shouldLoadCsv() throws Exception {
        String filepath = "filepath";
        when(simpleCabService.loadCSV("filepath")).thenReturn(3);
        MockHttpServletResponse response = loadCSV(LOAD_CSV_MAPPING, filepath);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("3", response.getContentAsString());
    }
    
    
    private MockHttpServletResponse countByMedallionAndPickupDate(String endpoint, String medallionId, LocalDate pickupDate) throws JsonProcessingException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        String plainTextJson = "";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_CONTEXT + endpoint).accept(MediaType.APPLICATION_JSON)
                .param("medallionId", medallionId)
                .param("pickupDate", pickupDate.toString())
                .content(plainTextJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        return result.getResponse();
    }

    
    private MockHttpServletResponse countByMedallionsAndPickupDate(String endpoint,  String[] medallionIds, LocalDate pickupDate) throws JsonProcessingException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        String plainTextJson = "";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(BASE_CONTEXT + endpoint).accept(MediaType.APPLICATION_JSON)
                .param("medallionIds", medallionIds)
                .param("pickupDate", pickupDate.toString())
                .content(plainTextJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        return result.getResponse();
    }

    
    private MockHttpServletResponse clearCache(String endpoint) throws JsonProcessingException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        String plainTextJson = "";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_CONTEXT + endpoint).accept(MediaType.APPLICATION_JSON)
                .content(plainTextJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        return result.getResponse();
    }

    
    private MockHttpServletResponse loadCSV(String endpoint, String filepath) throws JsonProcessingException, Exception {
        ObjectMapper mapper = new ObjectMapper();
        String plainTextJson = "";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(BASE_CONTEXT + endpoint).accept(MediaType.APPLICATION_JSON).param("filepath", filepath)
                .content(plainTextJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        return result.getResponse();
    }

    
    

}
