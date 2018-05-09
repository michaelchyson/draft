package net.chyson.codis;

/**
 * michael.chyson
 * 5/8/2018
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


    public String[] getAddresses() {
        return addresses;
    }

    public void setAddresses(String[] addresses) {
        this.addresses = addresses;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public String getNxxx() {
        return nxxx;
    }

    public void setNxxx(String nxxx) {
        this.nxxx = nxxx;
    }

    public String getExpx() {
        return expx;
    }

    public void setExpx(String expx) {
        this.expx = expx;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
