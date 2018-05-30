package net.chyson.source;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;

/**
 * michael.chyson
 * 5/29/2018
 */
public class KafkaSourcer<T> extends AbstractKafkaSourcer<T> {


    public KafkaSourcer(Properties properties, BlockingQueue<T> queue, String... strings) {
        super(properties, queue, strings);
    }

    @Override
    public T preprocess(ConsumerRecords<String, String> consumerRecords) {
        for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {

        }
        return null;
    }
}
