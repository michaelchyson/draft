package net.chyson.sink;


import net.chyson.config.Config;
import net.chyson.filename.PeriodFilename;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class FtpRunnable implements Runnable {

    private static final Logger logger = LogManager.getLogger(FtpRunnable.class);

    private PeriodFilename periodFilename;

    private BlockingQueue<ConsumerRecords<String, String>> recordQueue;
    private BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> offsetQueue;

    private FTPClient ftpClient = new FTPClient();
    private OutputStream out = null;
    private String filenameBuffer = null;
    private Map<TopicPartition, OffsetAndMetadata> offsetMap = new HashMap<>();

    private String host;
    private int port;
    private String username;
    private String password;
    private String path;

    private long count = 0L;

    public FtpRunnable(Config config) {
        this.recordQueue = config.getRecordQueue();
        this.offsetQueue = config.getOffsetQueue();
        this.periodFilename = new PeriodFilename(config);

        this.host = config.getFtp().getCommon().getProperty("host");
        this.port = (int) config.getFtp().getCommon().get("port");
        this.username = config.getFtp().getCommon().getProperty("username");
        this.password = config.getFtp().getCommon().getProperty("password");
        this.path = config.getFtp().getCommon().getProperty("path");
    }


    @Override
    public void run() {
        logger.info("ftp runnable started.");
        logger.debug("ftp client object: " + ftpClient);

        while (true) {
            try {
                //get filename before poll to ensure if there is no data it will write a empty file
                String filename = periodFilename.getFilename();
                logger.debug("record queue size before polling data from it: " + recordQueue.size());
                ConsumerRecords<String, String> poll = recordQueue.poll(1, TimeUnit.SECONDS);
                if (poll != null && out != null) {
                    count += poll.count();
                    logger.debug("poll records count: " + poll.count());
                    for (ConsumerRecord<String, String> record : poll) {
                        out.write((record.value() + "\n").getBytes("UTF-8"));
                        TopicPartition key = new TopicPartition(record.topic(), record.partition());
                        OffsetAndMetadata value = new OffsetAndMetadata(record.offset() + 1, "");
                        offsetMap.put(key, value);
                    }
                }

                if (!filename.equals(filenameBuffer)) {
                    logger.debug("filename: " + filename);
                    try {
                        getFtpClient();
                        rename();
                        offsetMap = new HashMap<>();
                        filenameBuffer = filename;
                        logger.debug("filename before creating output stream: " + filenameBuffer);
                        logger.debug("ftp client used to create output stream: " + ftpClient);
                        out = ftpClient.storeFileStream(filename);
                        logger.debug("output stream created: " + out);
                    } catch (SocketException e1) {
                        logger.error(e1.getMessage(), e1);
                        ftpClient.logout();
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }

    }

    private void rename() {
        if (out != null) {
            try {
                out.close();
                logger.info("write " + count + " records into " + filenameBuffer + ".");
                count = 0;
                ftpClient.rename(filenameBuffer, filenameBuffer.substring(0, filenameBuffer.length() - 4));
                logger.debug("filename rename from " + filenameBuffer + " to " + filenameBuffer.substring(0, filenameBuffer.length() - 4));
                offsetQueue.put(offsetMap);
            } catch (IOException e) {
                logger.error("error while closing ftp out stream or renaming filename");
                logger.error(e.getMessage(), e);
            } catch (InterruptedException e1) {
                logger.error("error while putting data into offset queue");
                logger.error(e1.getMessage(), e1);
            }
        }
    }


    private void getFtpClient() {
        try {
            ftpClient.connect(host, port);
            ftpClient.login(username, password);
            ftpClient.changeWorkingDirectory(path);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
