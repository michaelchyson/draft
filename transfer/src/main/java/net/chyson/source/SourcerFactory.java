package net.chyson.source;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;

/**
 * michael.chyson
 * 5/29/2018
 */
public class SourcerFactory {
    public static Sourcer getInstance(Properties properties, BlockingQueue<Map<String, Map<String, String>>> queue, String... strings) {
        return new KafkaSourcer<>(properties, queue, strings);
    }
}
