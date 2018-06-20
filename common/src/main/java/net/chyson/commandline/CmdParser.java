package net.chyson.commandline;

import org.apache.commons.cli.*;

/**
 * michael.chyson
 * 5/30/2018
 */
public class CmdParser {

    protected static Options options = new Options();
    protected static CommandLineParser parser = new BasicParser();
    protected static CommandLine commandLine;


    protected static void initTansfer() {
        options.addOption("h", "help", false, "Print this usage information");
        options.addOption("su", "source", true, "source configuration file path");
        options.addOption("sn", "sink", true, "sink configuration file path");
        options.addOption("p", "precess", true, "process configuration file path");
    }


    protected static void help() {
        if (commandLine.hasOption('h')) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("command line syntax", options);
            System.exit(0);
        }
    }


    public static void main(String[] args) throws ParseException {
        initTansfer();
        commandLine = parser.parse(options, args);
        help();
        if (commandLine.hasOption('f')) {
            String file = commandLine.getOptionValue('f');
            System.out.println(file);
        }
    }
}
