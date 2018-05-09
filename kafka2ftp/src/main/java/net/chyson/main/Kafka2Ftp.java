package net.chyson.main;

import net.chyson.config.Config;
import net.chyson.json.JsonUtils;
import net.chyson.sink.FtpRunnable;
import net.chyson.source.KafkaRunnable;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Kafka2Ftp {
    private static Logger logger = LogManager.getLogger(Kafka2Ftp.class);


    public static void main(String[] args) {
        try {
            String path = args[0];
            int index = Integer.valueOf(args[1]);
            Config config = JsonUtils.parse(path, Config.class);

            Properties queue = config.getQueue();
            int recordQueueSize = (int) queue.get("record.queue.size");
            int offsetQueueSize = (int) queue.get("offset.queue.size");
            BlockingQueue<ConsumerRecords<String, String>> recordQueue = new LinkedBlockingQueue<>(recordQueueSize);
            BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> offsetQueue = new LinkedBlockingQueue<>(offsetQueueSize);

            config.setRecordQueue(recordQueue);
            config.setOffsetQueue(offsetQueue);
            config.setIndex(index);

            new Thread(new KafkaRunnable(config)).start();
            new Thread(new FtpRunnable(config)).start();

        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }


    }


}
