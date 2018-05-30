package net.chyson.main;

import net.chyson.commandline.CmdParser;
import org.apache.commons.cli.ParseException;

/**
 * michael.chyson
 * 5/30/2018
 */
public class Transfer extends CmdParser {

    public static void main(String[] args) throws ParseException {
        initTansfer();
        commandLine = parser.parse(options, args);
        help();
    }
}
