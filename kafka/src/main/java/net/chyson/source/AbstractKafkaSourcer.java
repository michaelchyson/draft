package net.chyson.source;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

/**
 * michael.chyson
 * 5/28/2018
 */
public abstract class AbstractKafkaSourcer<T> implements Sourcer, Runnable {

    private KafkaConsumer<String, String> consumer;
    private BlockingQueue<T> queue;

    public AbstractKafkaSourcer() {

    }

    public AbstractKafkaSourcer(Properties properties, BlockingQueue<T> queue, String... strings) {
        consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(strings));
        this.queue = queue;

    }


    @Override
    public ConsumerRecords<String, String> source() {
        ConsumerRecords<String, String> poll = consumer.poll(100);
        return poll;
    }


    @Override
    public void run() {
        while (true) {
            ConsumerRecords<String, String> source = source();
            if (source.isEmpty()) continue;
            T t = preprocess(source);
            try {
                queue.put(t);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public abstract T preprocess(ConsumerRecords<String, String> consumerRecords);

    public KafkaConsumer<String, String> getConsumer() {
        return consumer;
    }

    public void setConsumer(KafkaConsumer<String, String> consumer) {
        this.consumer = consumer;
    }


}
