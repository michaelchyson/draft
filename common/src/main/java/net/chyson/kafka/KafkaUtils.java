package net.chyson.kafka;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.Properties;

/**
 * michael.chyson
 * 5/8/2018
 */
public class KafkaUtils {

    private static final Logger LOG = LogManager.getLogger(KafkaUtils.class);

    /**
     * pass in the kafka configuration properties and the topics
     * and get a kafka consumer
     *
     * @param properties kafka properties
     * @param topics     the topics the will be consumed
     * @return kafka consumer
     */
    public KafkaConsumer<String, String> getConsumer(Properties properties, String... topics) {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topics));
        LOG.info("consumer subscribe: " + consumer.subscription());
        return consumer;
    }


}