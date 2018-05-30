package net.chyson.properties;

import org.apache.commons.beanutils.BeanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;


/**
 * Created by Michael Chyson on 2017/11/16.
 */
public class PropertiesUtils {
    /**
     * @param name
     * @return
     * @throws IOException
     */
    public static Properties getProperties(String name) throws IOException {
        Properties properties = new Properties();
        InputStream in = null;
        in = ClassLoader.getSystemResourceAsStream(name);
        properties.load(new InputStreamReader(in));
        return properties;
    }

    /**
     * @param object
     * @param properties
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void populate(Object object, Map properties) throws InvocationTargetException, IllegalAccessException {
        BeanUtils.populate(object, properties);
    }

    /**
     * @param object
     * @param name
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void populate(Object object, String name) throws IOException, InvocationTargetException, IllegalAccessException {
        Properties properties = getProperties(name);
        BeanUtils.populate(object, properties);
    }
}
