package com.github.tk04;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// assign and seek: mostly used to replay data or fetch specific msg
public class ConsumerAssignSeek {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(ConsumerDemo.class);
        Properties props = new Properties(); //consumer properties

        String topic = "first_topic";
        // String groupId = "my-fourth-application";
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");


        //create consumer
        
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        TopicPartition paritionToRead = new TopicPartition(topic, 0);
        consumer.assign(Arrays.asList(paritionToRead));

        long offsetsToRead = 15L;
        consumer.seek(paritionToRead, offsetsToRead);
        //subscribe to topic
        // consumer.subscribe(Collections.singleton("first_topic"));
        //poll new data
        int numOfMsgs = 5;
        boolean keepReading = true;
        int numOfMsgsRead = 0;
        while(keepReading){
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            for(ConsumerRecord<String, String> record : records){
                numOfMsgsRead++;
                logger.info("Key: " + record.key() +  ", Value: " + record.value());
                logger.info("Parition: " + record.partition() + ", Offset: " + record.offset());
                if(numOfMsgsRead >= numOfMsgs){
                    keepReading = false; //stop reading
                    break;
                }
            }
        }
        consumer.close();
        logger.info("Exiting application");
    } 
}
 