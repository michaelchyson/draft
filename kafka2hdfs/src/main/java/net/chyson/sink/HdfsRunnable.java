package net.chyson.sink;


import net.chyson.config.Config;
import net.chyson.config.Hdfs;
import net.chyson.filename.PeriodFilename;
import net.chyson.hadoop.HadoopUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class HdfsRunnable implements Runnable {

    private static final Logger logger = LogManager.getLogger(HdfsRunnable.class);

    /**
     * generate hadoop filename at fixed rate
     */
    private PeriodFilename periodFilename;

    private BlockingQueue<ConsumerRecords<String, String>> recordQueue;
    private BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> offsetQueue;

    private FileSystem fs;
    private OutputStream out;
    private String filenameBuffer;
    private Map<TopicPartition, OffsetAndMetadata> offsetMap = new HashMap<>();

    private long count = 0L;


    public HdfsRunnable(Config config) {
        this.recordQueue = config.getRecordQueue();
        this.offsetQueue = config.getOffsetQueue();
        this.periodFilename = new PeriodFilename(config);
        this.fs = HadoopUtils.getHadoopFileSystem();
    }

    @Override
    public void run() {
        //guarantee the hadoop file system is properly initialized.
        logger.info("hdfs runnable started.");
        if (fs == null) {
            logger.error("get hdfs error and exit.");
            System.exit(1);
        }

        while (true) {
            try {
                String filename = periodFilename.getFilename();

                ConsumerRecords<String, String> poll = recordQueue.poll(1, TimeUnit.SECONDS);

                logger.debug("filename: " + filename);
                if (poll != null && out != null) {
                    logger.debug("poll data from record queue: " + poll.count());
                    for (ConsumerRecord<String, String> record : poll) {
                        out.write((record.value() + "\n").getBytes("UTF-8"));
                        TopicPartition key = new TopicPartition(record.topic(), record.partition());
                        OffsetAndMetadata value = new OffsetAndMetadata(record.offset() + 1, "");
                        offsetMap.put(key, value);
                    }
                    count += poll.count();
                }

                if (!filename.equals(filenameBuffer)) {
                    close();
                    offsetMap = new HashMap<>();
                    filenameBuffer = filename;
                    try {
                        out = fs.create(new Path(filenameBuffer));
                        logger.debug("create new output stream: " + out);
                    } catch (IOException e) {
                        logger.error("error while creating output stream.");
                        logger.error(e.getMessage(), e);
                    }
                }
            } catch (InterruptedException e1) {
                logger.error("interrupted while polling data from queue");
                logger.error(e1.getMessage(), e1);
            } catch (UnsupportedEncodingException e2) {
                logger.error("unsupported encoding ");
                logger.error(e2.getMessage(), e2);
            } catch (IOException e3) {
                logger.error("error happened while writing data into hadoop");
                logger.error(e3.getMessage(), e3);
            }
        }

    }


    private void close() {
        if (out != null) {
            try {
                out.close();
                logger.info("hadoop path" + filenameBuffer + "; count: " + count);
                count = 0L;
                Path path = new Path(filenameBuffer);
                Path newPath = new Path(filenameBuffer.substring(0, filenameBuffer.length() - 4));
                fs.rename(path, newPath);
                offsetQueue.put(offsetMap);
            } catch (IOException e) {
                logger.error("error while closing hadoop out stream");
                logger.error(e.getMessage(), e);
            } catch (InterruptedException e2) {
                logger.error(e2.getMessage(), e2);
            }
        }
    }
}
