package net.chyson.config;

import java.util.Arrays;
import java.util.Properties;


public class Hdfs {
    /**
     * common hadoop filepath and filename
     */
    private Properties common;
    /**
     * the label part in hadoop filename
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
        return "Hdfs{" +
                "common=" + common +
                ", special=" + Arrays.toString(special) +
                '}';
    }
}
