package net.chyson.main;

import net.chyson.commandline.CmdParser;
import net.chyson.json.JsonUtils;
import org.apache.commons.cli.ParseException;

import java.util.Map;

/**
 * michael.chyson
 * 5/30/2018
 */
public class Transfer extends CmdParser {

    public static void main(String[] args) throws ParseException {
        initTansfer();
        commandLine = parser.parse(options, args);
        help();
        Object source = source(Map.class);
        Map map = (Map) source;
        System.out.println(map.get("hello"));

    }

    private static Object source(Class clazz) {
        if (commandLine.hasOption("su")) {
            String source = commandLine.getOptionValue("su");
            System.out.println(source);
            return JsonUtils.parse(source, clazz);
        }
        return null;
    }
}
