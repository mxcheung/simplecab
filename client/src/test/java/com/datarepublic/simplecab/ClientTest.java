package com.datarepublic.simplecab;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientTest {

    private Client  client ;

    
    @Before
    public void setup() {
        client = new Client();
    }

    @Test
    public void shouldRunMain() throws URISyntaxException, IOException {
        String[] args = new String[] { "-i"};
        client.main(args);
    }
    
    
}
