import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaBookProducerKey {
  public static void main(String[] args) {
    Properties props = new Properties();
    props.put("bootstrap.servers", "peter-kafka001:9092,peter-kafka002:9092,peter-kafka003:9092");
    props.put("acks", "1");
    props.put("compression.type", "gzip");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    Producer<String, String> producer = new KafkaProducer<>(props);
    String testTopic = "peter-topic2";
    String oddKey = "1";
    String evenKey = "2";

    for (int i = 1; i < 11; i++) {
      if (i % 2 == 1) {
        producer.send(new ProducerRecord<String, String>(testTopic, oddKey, String.format("%d - Apache Kafka is a distributed streaming platform - key=" + oddKey, i)));
      } else {
        producer.send(new ProducerRecord<String, String>(testTopic, evenKey, String.format("%d - Apache Kafka is a distributed streaming platform - key=" + evenKey, i)));
      }
    }

    producer.close();
  }
}
