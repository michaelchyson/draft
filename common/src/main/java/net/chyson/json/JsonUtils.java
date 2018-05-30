package net.chyson.json;


import com.alibaba.fastjson.JSON;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


/**
 * michael.chyson
 * 5/8/2018
 */
public class JsonUtils {


    private static final Logger LOG = LogManager.getLogger(JsonUtils.class);

    /**
     * pass in a json configuration file
     * and parse into the T object
     *
     * @param path  configuration file path
     * @param clazz the Class you want parse the json file into
     * @param <T>   the class you want to parse into
     * @return the configuration file object
     */
    public static <T> T parse(String path, Class<T> clazz) {
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            String text = IOUtils.toString(is, "utf-8");
            T t = JSON.parseObject(text, clazz);
            LOG.info(t);
            return t;
        } catch (FileNotFoundException e1) {
            LOG.error(e1.getMessage(), e1);
        } catch (IOException e2) {
            LOG.error(e2.getMessage(), e2);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOG.error(e.getMessage(), e);
                }
            }
        }
        return null;
    }

}
