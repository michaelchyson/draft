package net.chyson.source;

import net.chyson.config.Config;
import net.chyson.kafka.KafkaUtils;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;


public class KafkaRunnable extends Thread {
    private static final Logger logger = LogManager.getLogger(KafkaRunnable.class);

    private BlockingQueue<ConsumerRecords<String, String>> recordQueue;
    private BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> offsetQueue;
    private KafkaConsumer<String, String> consumer;


    public KafkaRunnable(Config config) {
        this.recordQueue = config.getRecordQueue();
        this.offsetQueue = config.getOffsetQueue();

        Properties kafka = config.getKafka();
        String topic = kafka.getProperty("topic");
        this.consumer = KafkaUtils.getConsumer(kafka, topic);
        logger.info("consumer subscribe: " + consumer.subscription());

    }

    @Override
    public void run() {
        logger.info("kafka runnable started.");

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            if (!records.isEmpty()) {
                try {
                    recordQueue.put(records);
                } catch (InterruptedException e) {
                    logger.error("interrupted while putting records into records queue");
                    logger.error(e.getMessage(), e);
                }
            }
            sync();
        }
    }


    private void sync() {
        Map<TopicPartition, OffsetAndMetadata> offset = new HashMap<>();

        while (!offsetQueue.isEmpty()) {
            Map<TopicPartition, OffsetAndMetadata> poll = offsetQueue.poll();
            offset.putAll(poll);
            logger.debug("offset map: " + offset);
        }
        consumer.commitSync(offset);

    }


}
