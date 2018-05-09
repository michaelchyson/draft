package net.chyson.main;

import com.alibaba.fastjson.JSON;

import net.chyson.config.Config;
import net.chyson.config.Hdfs;
import net.chyson.json.JsonUtils;
import net.chyson.sink.HdfsRunnable;
import net.chyson.source.KafkaRunnable;
import org.apache.commons.io.IOUtils;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class Kafka2Hdfs {

    private static Logger logger = LogManager.getLogger(Kafka2Hdfs.class);

    public static void main(String[] args) {

        try {
            String path = args[0];
            int label = Integer.valueOf(args[1]);
//            String path = "C:\\Users\\amain\\IdeaProjects\\draft\\kafka2hdfs\\src\\main\\resources\\mc.json";
//            int label = 0;
            Config config = JsonUtils.parse(path, Config.class);


            Properties queue = config.getQueue();
            int recordQueueSize = (int) queue.get("record.queue.size");
            int offsetQueueSize = (int) queue.get("offset.queue.size");
            BlockingQueue<ConsumerRecords<String, String>> recordQueue = new LinkedBlockingQueue<>(recordQueueSize);
            BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> offsetQueue = new LinkedBlockingQueue<>(offsetQueueSize);
            config.setRecordQueue(recordQueue);
            config.setOffsetQueue(offsetQueue);
            config.setLabel(label);


            new Thread(new KafkaRunnable(config)).start();
            new Thread(new HdfsRunnable(config)).start();
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
        }


    }


}
