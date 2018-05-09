package net.chyson.config;

import java.util.Arrays;
import java.util.Properties;


public class Ftp {

    /**
     * common ftp filepath and filename
     */
    private Properties common;
    /**
     * the label part in ftp filename
     */
    private Properties[] special;

    public Properties getCommon() {
        return common;
    }

    public void setCommon(Properties common) {
        this.common = common;
    }

    public Properties[] getSpecial() {
        return special;
    }

    public void setSpecial(Properties[] special) {
        this.special = special;
    }

    @Override
    public String toString() {
        return "Ftp{" +
                "common=" + common +
                ", special=" + Arrays.toString(special) +
                '}';
    }
}
