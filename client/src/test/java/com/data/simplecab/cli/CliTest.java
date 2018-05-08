package com.data.simplecab.cli;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.NoSuchFileException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.data.simplecab.cli.Cli;
import com.data.simplecab.command.CommandParams;

@RunWith(MockitoJUnitRunner.class)
public class CliTest {

    private Cli cli;

    @Before
    public void setup() {
        cli = new Cli(null);
    }

    @Test
    public void shouldLoadDeleteCache() throws URISyntaxException, IOException {
        String[] args = new String[] { "-d", "-p", "2013-12-01" };
        cli = new Cli(args);
        CommandParams params = cli.parse();
        assertEquals(true, params.getDeleteCache());
        assertEquals(false, params.getIgnoreCache());
        assertEquals("2013-12-01", params.getPickupDate());
    }

    @Test
    public void shouldLoadIgnoreCache() throws URISyntaxException, IOException {
        String[] args = new String[] { "-i" };
        cli = new Cli(args);
        CommandParams params = cli.parse();
        assertTrue(params.getIgnoreCache());
    }

    @Test(expected = NoSuchFileException.class)
    public void shouldNoSuchFileException() throws URISyntaxException, IOException {
        String[] args = new String[] { "-f", "abc.txt" };
        cli = new Cli(args);
        CommandParams params = cli.parse();
        assertTrue(params.getIgnoreCache());
        assertEquals("abc.txt", params.getInputFileName());
    }

    @Test
    public void shouldAcceptIds() throws URISyntaxException, IOException {
        String[] args = new String[] { "-m", "a", "b", "-i" };
        cli = new Cli(args);
        CommandParams params = cli.parse();
        assertTrue(params.getIgnoreCache());
        assertEquals(2, params.getMedallionIds().length);
    }

    @Test
    public void shouldHelp() throws URISyntaxException, IOException {
        String[] args = new String[] { "-h" };
        cli = new Cli(args);
        cli.parse();
    }

}
