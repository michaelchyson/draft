package net.chyson.config;

/**
 * michael.chyson
 * 5/30/2018
 */
public class CodisConfig {
    private String[] addresses;
    private int connectionTimeout;
    //socket timeout
    private int soTimeout;
    //NX -- Only set the key if it does not already exist.
    //XX -- Only set the key if it already exist.
    private String nxxx;
    //expire time units: EX = seconds; PX = milliseconds
    private String expx;
    //the time the data kept in the codis
    private int timeout;
    //codis prefix
    private String prefix;
}
