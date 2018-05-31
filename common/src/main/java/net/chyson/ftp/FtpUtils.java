package net.chyson.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

/**
 * michael.chyson
 * 5/8/2018
 */
public class FtpUtils {

    /**
     * the properties should contain host,port,username,password,default.directory properties
     * this method parse the host, port, username, password and default.directory
     * and return a ftp client
     *
     * @param properties a properties that contain host,port,username,password,default.directory
     * @return ftp client
     */
    public static FTPClient getFtpClient(Properties properties) {
        FTPClient ftpClient = new FTPClient();
        String host = properties.getProperty("host");
        int port = (int) properties.get("port");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");
        String defaultDirectory = properties.getProperty("default.directory");

        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.changeWorkingDirectory(defaultDirectory);
            return ftpClient;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
