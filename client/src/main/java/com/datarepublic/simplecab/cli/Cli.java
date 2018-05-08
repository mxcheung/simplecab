package com.datarepublic.simplecab.cli;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datarepublic.simplecab.command.CommandParams;

public class Cli {
    private static final Logger LOGGER = LoggerFactory.getLogger(Cli.class);
    private String[] args = null;
    private Options options = getOptions();


    public Cli(String[] args) {
        this.args = args;
    }

    public CommandParams parse() throws URISyntaxException, IOException {

        CommandLineParser parser = new DefaultParser();
        CommandParams params = new CommandParams();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("h"))
                help();
            else {
                if (cmd.hasOption("d")) {
                    params.setDeleteCache(true);
                }
                
                if (cmd.hasOption("i")) {
                    params.setIgnoreCache(true);
                }
                
                if (cmd.hasOption("p")) {
                    params.setPickupDate(cmd.getOptionValue("p"));
                }
                
                if (cmd.hasOption("f")) {
                    params.setInputFileName(cmd.getOptionValue("f"));
                    String[] medallionIds = readFile(params.getInputFileName());
                    LOGGER.info("\nLoad from file medallionIds count: {}", medallionIds.length);
                    params.setMedallionIds(medallionIds);
                }
                
                if (cmd.hasOption("m")) {
                    params.setMedallionIds(cmd.getOptionValues("m"));
                }
            }



        } catch (ParseException e) {
            LOGGER.error("Failed to parse comand line properties", e);
            help();
        }
        return params;
    }

    private String[] readFile(String filepath) throws URISyntaxException, IOException {
        File file = new File(filepath);
        Path path = Paths.get(file.toURI());
        Stream<String> lines = Files.lines(path);
        String[] input = lines.toArray(size -> new String[size]);
        lines.close();
        return input;
    }

    public void help() {
        // This prints out some help
        HelpFormatter formater = new HelpFormatter();
        formater.printHelp("Main", options);
    //    System.exit(0);
    }

    private Options getOptions() {
        Options options = new Options();
        options.addOption("h", "help", false, "show help.");
        options.addOption("d", "deleteCache", false, "Delete cache.");
        options.addOption("i", "ignoreCache", false, "Ignore cache.");
        options.addOption("m", "medallionIds", true, "medallion ids.");
        options.addOption("p", "pickupDate", true, "Pickup Date yyyy-MM-dd.");
        options.addOption("f", "inputFileName", true, "inputFileName with ids.");
        options.getOption("m").setArgs(Option.UNLIMITED_VALUES);
        return options;
    }

}
