package au.com.data.simplecab.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import com.data.simplecab.repository.SimpleCabRepository;
import com.data.simplecab.repository.SimpleCabRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCabRepositoryTest {

    private SimpleCabRepository simpleCabRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setup() {
        simpleCabRepository = new SimpleCabRepositoryImpl(jdbcTemplate);
    }

    @Test
    public void shouldReturnCount() {
        Date pickupDate = java.sql.Date.valueOf(LocalDate.of(2013, 12, 01));
        String medallionId = "D7D598CD99978BD012A87A76A7C891B7";
        when(jdbcTemplate.queryForObject(anyString(), anyObject(), eq(Integer.class))).thenReturn(3);
        Integer count = simpleCabRepository.getCountByMedallionAndPickupDatetime(medallionId, pickupDate);
        assertEquals(3, count.intValue());
        verify(jdbcTemplate, times(1)).queryForObject(anyString(), anyObject(), eq(Integer.class));
        verifyNoMoreInteractions(jdbcTemplate);
    }

    @Test
    public void shouldClearCabTripData() {
        simpleCabRepository.clearCache();
        simpleCabRepository.clearCabTripData();
        verify(jdbcTemplate, times(1)).execute(contains("truncate table"));
        verifyNoMoreInteractions(jdbcTemplate);
    }

    @Test
    public void shouldLoadCsv() {
        String filepath = "dummy.csv";
        when(jdbcTemplate.update(anyString())).thenReturn(3);
        Integer count = simpleCabRepository.loadCSV(filepath);
        assertEquals(3, count.intValue());
        verify(jdbcTemplate, times(1)).update(contains(filepath));
        verifyNoMoreInteractions(jdbcTemplate);
    }

}
