package com.datarepublic.simplecab.client;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.web.client.RestTemplate;

import com.datarepublic.simplecab.client.cli.Cli;
import com.datarepublic.simplecab.client.command.CommandExecutor;
import com.datarepublic.simplecab.client.command.CommandParams;
import com.datarepublic.simplecab.client.command.SimpleCabService;
import com.datarepublic.simplecab.client.command.SimpleCabServiceImpl;

public class Client {

    private static RestTemplate restTemplate = new RestTemplate();
    private static SimpleCabService simpleCabService = new SimpleCabServiceImpl(restTemplate);

    private static CommandExecutor executor = new CommandExecutor(simpleCabService);

    public static void main(String[] args) throws URISyntaxException, IOException {
        CommandParams params = new Cli(args).parse();
        executor.execute(params);
    }
}