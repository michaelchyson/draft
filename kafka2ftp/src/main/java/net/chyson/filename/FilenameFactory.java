package net.chyson.filename;


import net.chyson.config.Config;

/**
 * used to create the hdfs filename class
 * <p>

 */
public class FilenameFactory {

    private FilenameFactory() {
    }


    public static FileNameInterface getInstance(Config config) {
        return new PeriodFilename(config);
    }


}
