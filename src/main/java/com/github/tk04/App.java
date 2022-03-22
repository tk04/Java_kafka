package com.github.tk04;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        
        Properties props = new Properties(); //producer properties
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName() );

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);


        ProducerRecord<String, String> record = new ProducerRecord<>("first_topic", "Hello World");
        //send data: done asynchronously
        producer.send(record);
        producer.close(); // flush and close producer
    }
}
