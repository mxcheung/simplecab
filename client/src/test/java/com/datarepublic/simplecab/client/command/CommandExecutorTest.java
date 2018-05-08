package com.datarepublic.simplecab.client.command;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.datarepublic.simplecab.client.command.CommandExecutor;
import com.datarepublic.simplecab.client.command.CommandParams;
import com.datarepublic.simplecab.client.command.SimpleCabService;

@RunWith(MockitoJUnitRunner.class)
public class CommandExecutorTest {

    private CommandExecutor commandExecutor;

    @Mock
    private SimpleCabService simpleCabService;

    @Before
    public void setup() {
        commandExecutor = new CommandExecutor(simpleCabService);
    }

    @Test
    public void shouldDeleteCache() throws URISyntaxException, IOException {
        CommandParams params = new CommandParams();
        params.setDeleteCache(true);
        commandExecutor.execute(params);
        verify(simpleCabService, times(1)).deleteCache();
        verifyNoMoreInteractions(simpleCabService);
    }

    @Test
    public void shouldgetMedallionSummary() throws URISyntaxException, IOException {
        CommandParams params = new CommandParams();
        String[] medallionIds = new String[] { "D7D598CD99978BD012A87A76A7C891B7" };
        params.setMedallionIds(medallionIds);
        params.setPickupDate("2013-12-01");

        Map<String, Integer> data = new HashMap<String, Integer>();
        data.put("D7D598CD99978BD012A87A76A7C891B7", 3);
        when(simpleCabService.getMedallionsSummary(java.sql.Date.valueOf(params.getPickupDate()),
                params.getIgnoreCache(), medallionIds)).thenReturn(data);
        commandExecutor.execute(params);
        verify(simpleCabService, times(1)).getMedallionsSummary(java.sql.Date.valueOf(params.getPickupDate()),
                params.getIgnoreCache(), medallionIds);
        verifyNoMoreInteractions(simpleCabService);
    }

}
