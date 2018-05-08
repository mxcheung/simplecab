package com.data.simplecab.command;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.data.simplecab.command.CommandParams;
import com.data.simplecab.command.SimpleCabService;
import com.data.simplecab.command.SimpleCabServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCabServiceTest {

    private SimpleCabService simpleCabService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    ResponseEntity<Object> response;


    @Before
    public void setup() {
        simpleCabService = new SimpleCabServiceImpl(restTemplate);
    }

    @Test
    public void shouldDeleteCache() throws URISyntaxException, IOException {
        CommandParams params = new CommandParams();
        params.setDeleteCache(true);
        when(restTemplate.exchange(Matchers.any(String.class), Matchers.any(HttpMethod.class),
                Matchers.<HttpEntity<?>>any(), Matchers.<Class<Object>>any())).thenReturn(response);
        simpleCabService.deleteCache();
        verify(restTemplate, times(1)).exchange(Matchers.any(String.class), Matchers.any(HttpMethod.class),
                Matchers.<HttpEntity<?>>any(), Matchers.<Class<Object>>any());
        verifyNoMoreInteractions(restTemplate);
    }

    @Test
    public void shouldGetMedallionSummary() throws URISyntaxException, IOException {
        CommandParams params = new CommandParams();
        String[] medallionIds = new String[] { "D7D598CD99978BD012A87A76A7C891B7" };
        params.setMedallionIds(medallionIds);
        params.setPickupDate("2013-12-01");
        
        
        ResponseEntity<Map<String, Integer>>  myEntity = new ResponseEntity<Map<String, Integer>>(HttpStatus.ACCEPTED);
        when(restTemplate.exchange(Matchers.any(String.class), Matchers.any(HttpMethod.class),
                Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<Map<String, Integer>>>any())).thenReturn(myEntity);
        simpleCabService.getMedallionsSummary(java.sql.Date.valueOf(params.getPickupDate()), medallionIds);
        verify(restTemplate, times(1)).exchange(Matchers.any(String.class), Matchers.any(HttpMethod.class),
                Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<Map<String, Integer>>>any());
        verifyNoMoreInteractions(restTemplate);
    }

    
    @Test
    public void shouldGetMedallionsSummary() throws URISyntaxException, IOException {
        CommandParams params = new CommandParams();
        String[] medallionIds = new String[] { "D7D598CD99978BD012A87A76A7C891B7" };
        params.setMedallionIds(medallionIds);
        params.setPickupDate("2013-12-01");
        ResponseEntity<Map<String, Integer>>  myEntity = new ResponseEntity<Map<String, Integer>>(HttpStatus.ACCEPTED);
        when(restTemplate.exchange(Matchers.any(String.class), Matchers.any(HttpMethod.class),
                Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<Map<String, Integer>>>any())).thenReturn(myEntity);
        simpleCabService.getMedallionsSummary(java.sql.Date.valueOf(params.getPickupDate()),
                params.getIgnoreCache(), medallionIds);
        verify(restTemplate, times(1)).exchange(Matchers.any(String.class), Matchers.any(HttpMethod.class),
                Matchers.<HttpEntity<?>>any(), Matchers.<ParameterizedTypeReference<Map<String, Integer>>>any());
        verifyNoMoreInteractions(restTemplate);
    }

}
