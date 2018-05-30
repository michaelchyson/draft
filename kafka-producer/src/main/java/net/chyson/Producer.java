package net.chyson;

import net.chyson.properties.PropertiesUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.*;
import java.util.Properties;

/**
 * michael.chyson
 * 5/17/2018
 */
public class Producer {
    public static void main(String[] args) {
        String configPath = args[0];
        String filepath = args[1];
        try {
            Properties properties = PropertiesUtils.getProperties(configPath);
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

            String topic = properties.getProperty("topic");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filepath))));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] split = line.split(" ");
                producer.send(new ProducerRecord<String, String>(topic, split[0], split[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
