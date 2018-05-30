package net.chyson;

import net.chyson.commandline.CmdParser;
import org.apache.commons.cli.ParseException;

/**
 * michael.chyson
 * 5/30/2018
 */
public class CmdTest extends CmdParser {

    public static void main(String[] args) throws ParseException {

        initTansfer();
        options.addOption("a", "added", false, "This is a added feature");
        commandLine = parser.parse(options, args);
        help();


    }

}
