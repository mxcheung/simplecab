package com.data.simplecab;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.client.RestTemplate;

import com.data.simplecab.cli.Cli;
import com.data.simplecab.command.CommandExecutor;
import com.data.simplecab.command.CommandParams;
import com.data.simplecab.command.SimpleCabService;
import com.data.simplecab.command.SimpleCabServiceImpl;

public class Client {

    private static RestTemplate restTemplate = new RestTemplate();
    private static SimpleCabService simpleCabService = new SimpleCabServiceImpl(restTemplate);

    private static CommandExecutor executor = new CommandExecutor(simpleCabService);

    public static void main(String[] args) throws URISyntaxException, IOException {
        CommandParams params = new Cli(args).parse();
        executor.execute(params);
    }
}
