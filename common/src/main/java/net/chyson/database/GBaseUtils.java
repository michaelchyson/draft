package net.chyson.database;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * michael.chyson
 * 5/8/2018
 */
public class GBaseUtils {


    /**
     * pass in username, password, url
     * the driver is gbase driver: com.gbase.jdbc.Driver
     *
     * @param username gbase username
     * @param password gbase password
     * @param url      like jdbc:gbase://192.168.28.8:5258
     * @return database connection
     */
    public static Connection getConnection(String username, String password, String url) {
        try {
            String driver = "com.gbase.jdbc.Driver";
            Class.forName(driver);
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        return null;
    }
}
