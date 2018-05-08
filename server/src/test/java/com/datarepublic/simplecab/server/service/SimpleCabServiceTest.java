package com.datarepublic.simplecab.server.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.datarepublic.simplecab.server.repository.SimpleCabRepository;
import com.datarepublic.simplecab.server.service.SimpleCabService;
import com.datarepublic.simplecab.server.service.SimpleCabServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCabServiceTest {

    private SimpleCabService simpleCabService;

    @Mock
    private SimpleCabRepository simpleCabRepository;

    @Before
    public void setup() {
        simpleCabService = new SimpleCabServiceImpl(simpleCabRepository);
    }

    @Test
    public void shouldReturnCountForSingleMedallion() {
        Date pickupDate = java.sql.Date.valueOf(LocalDate.of(2013, 12, 01));
        String medallionId = "D7D598CD99978BD012A87A76A7C891B7";
        when(simpleCabRepository.getCountByMedallionAndPickupDatetime(medallionId, pickupDate)).thenReturn(3);
        Integer count = simpleCabService.getCountByMedallionAndPickupDatetime(medallionId, pickupDate);
        assertEquals(3, count.intValue());
        verify(simpleCabRepository, times(1)).getCountByMedallionAndPickupDatetime(medallionId, pickupDate);
        verifyNoMoreInteractions(simpleCabRepository);
    }

    @Test
    public void shouldReturnCountForListMedallion() {
        Date pickupDate = java.sql.Date.valueOf(LocalDate.of(2013, 12, 01));
        String medallionId = "D7D598CD99978BD012A87A76A7C891B7";
        List<String> medallionIds = new ArrayList<String>();
        medallionIds.add(medallionId);
        Map<String, Integer> data = new HashMap<String, Integer>();
        data.put(medallionId, 3);
        when(simpleCabRepository.getCountByMedallionAndPickupDatetime(medallionId, pickupDate)).thenReturn(3);
        Map<String, Integer> result = simpleCabService.getCountByMedallionsAndPickupDatetime(medallionIds, pickupDate);
        Integer count = result.get(medallionId);
        assertEquals(3, count.intValue());
        verify(simpleCabRepository, times(1)).getCountByMedallionAndPickupDatetime(medallionId, pickupDate);
        verifyNoMoreInteractions(simpleCabRepository);
    }

    @Test
    public void shouldResetAllEntries() {
        simpleCabService.clearCache();
        verify(simpleCabRepository, times(1)).clearCache();
        verifyNoMoreInteractions(simpleCabRepository);
    }

    @Test
    public void shouldLoadCsv() {
        String filepath = "dummy";
        simpleCabService.loadCSV(filepath);
        verify(simpleCabRepository, times(1)).clearCache();
        verify(simpleCabRepository, times(1)).clearCabTripData();
        verify(simpleCabRepository, times(1)).loadCSV(filepath);
        verifyNoMoreInteractions(simpleCabRepository);
    }

}
