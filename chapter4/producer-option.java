import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaBookProducer1 {
  public static void main(String[] args) {
    Properties props = new Properties();
    props.put("bootstrap.servers", "peter-kafka001:9092,peter-kafka002:9092,peter-kafka003:9092");
    props.put("acks", "1");
    props.put("compression.type", "gzip");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    Producer<String, String> producer = new KafkaProducer<>(props);
    producer.send(new ProducerRecord<String, String>("peter-topic", "Apache Kafka is a distributed streaming platform"));
    producer.close();
  }
}
