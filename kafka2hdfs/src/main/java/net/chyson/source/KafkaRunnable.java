package net.chyson.source;

import net.chyson.config.Config;
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
        logger.info("kafka runnable init record queue: " + recordQueue);

        this.offsetQueue = config.getOffsetQueue();
        logger.info("kafka runnable init offset queue: " + offsetQueue);

        Properties kafka = config.getKafka();
        this.consumer = new KafkaConsumer<>(kafka);
        logger.info("kafka runnable consumer: " + consumer);
        String topic = kafka.getProperty("topic");
        consumer.subscribe(Arrays.asList(topic));
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
                    logger.debug("records added into records queue." + " record queue size: " + recordQueue.size());
                    logger.debug("kafka poll count: " + records.count());
                } catch (InterruptedException e) {
                    logger.error("interrupted while putting records into records queue");
                    logger.error(e.getMessage(), e);
                }
            }
            sync();
        }
    }


    public void sync() {
        Map<TopicPartition, OffsetAndMetadata> offset = new HashMap<>();

        while (!offsetQueue.isEmpty()) {
            Map<TopicPartition, OffsetAndMetadata> poll = offsetQueue.poll();
            offset.putAll(poll);
            logger.debug("offset map: " + offset);
        }
        consumer.commitSync(offset);

    }


}
