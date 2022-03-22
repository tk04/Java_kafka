package com.github.tk04;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerKeys {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        
        final Logger logger = LoggerFactory.getLogger(ProducerCallback.class);
        Properties props = new Properties(); //producer properties
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName() );

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);



        for(int i = 0; i <10 ; i++ ){
            String topic = "first_topic";
            String value = "Hello World " + Integer.toString(i);
            String key = "id_" + Integer.toString(i);
            ProducerRecord<String, String> record = new ProducerRecord<>(topic,key, value);
            logger.info("Key: " + key);
            //send data: done asynchronously
            producer.send(record, new Callback() { // runs everytime record is send successfully or expection thrown
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if(e == null){
                        // RecordMetadata.
                        logger.info("Recieved new metadata \n" + "Topic: " + metadata.topic() + "\n" + "Partition: " + metadata.partition()  + "\n"  + "Offset: " + metadata.offset() + "\n" + "Timestamp: " + metadata.timestamp());
                    }else{
                        logger.error("Error while producing", e);
                    }
                }
                
            }).get(); // make it synchronous
            producer.flush();

            // producer.close(); // flush and close producer
        }
    }
    
    
}
