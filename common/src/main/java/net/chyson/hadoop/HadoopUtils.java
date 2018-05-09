package net.chyson.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * michael.chyson
 * 5/8/2018
 */
public class HadoopUtils {

    private static final Logger LOG = LogManager.getLogger(HadoopUtils.class);

    /**
     * default, it use the hadoop configuration in the resource
     *
     * @return hadoop filesystem
     */
    public static FileSystem getHadoopFileSystem() {
        try {
            Configuration conf = new Configuration();
            conf.set("fs.hdfs.impl", DistributedFileSystem.class.getName());
            return FileSystem.get(conf);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
        return null;
    }
}
