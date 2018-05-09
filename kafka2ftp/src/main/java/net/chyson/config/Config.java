package net.chyson.config;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;


public class Config {

    /**
     * holding consumer records
     */
    private BlockingQueue<ConsumerRecords<String, String>> recordQueue;
    /**
     * holding offset that should sync into kafka
     */
    private BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> offsetQueue;
    /**
     * kafka properties config
     */
    private Properties kafka;
    private Ftp ftp;
    /**
     * specify the queue size
     */
    private Properties queue;
    /**
     * the label part in the hadoop file system
     */
    private int index;

    public BlockingQueue<ConsumerRecords<String, String>> getRecordQueue() {
        return recordQueue;
    }

    public void setRecordQueue(BlockingQueue<ConsumerRecords<String, String>> recordQueue) {
        this.recordQueue = recordQueue;
    }

    public BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> getOffsetQueue() {
        return offsetQueue;
    }

    public void setOffsetQueue(BlockingQueue<Map<TopicPartition, OffsetAndMetadata>> offsetQueue) {
        this.offsetQueue = offsetQueue;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Properties getQueue() {
        return queue;
    }

    public void setQueue(Properties queue) {
        this.queue = queue;
    }

    public Properties getKafka() {
        return kafka;
    }

    public void setKafka(Properties kafka) {
        this.kafka = kafka;
    }

    public Ftp getFtp() {
        return ftp;
    }

    public void setFtp(Ftp ftp) {
        this.ftp = ftp;
    }

    @Override
    public String toString() {
        return "Config{" +
                "recordQueue=" + recordQueue +
                ", offsetQueue=" + offsetQueue +
                ", kafka=" + kafka +
                ", ftp=" + ftp +
                ", queue=" + queue +
                ", label=" + index +
                '}';
    }


}
