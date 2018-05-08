package myapps;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;

public class WordCount{
  
  public static void main(String[] args) throws Exception {
    Properties props = new Properties();
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
  
    final StreamsBuilder builder = new StreamsBuilder();
  
    builder.<String, String>stream("streams-plaintext-input")
        .flatMapValues(new ValueMapper<String, Iterable<String>>() {
          @Override
          public Iterable<String> apply(String value) {
            return Arrays.asList(value.toLowerCase(Locale.getDefault()).split("\\W+"));
          }
        })
        .groupBy(new KeyValueMapper<String, String, String>() {
          @Override
          public String apply(String key, String value) {
            return value;
          }
        })
        .count(Materialized.<String, Long, KeyValueStore<Bytes, byte[]>>as("counts-store"))
        .toStream()
        .to("streams-wordcount-output", Produced.with(Serdes.String(), Serdes.Long()));
  
    final Topology topology = builder.build();
    System.out.println(topology.describe());
  
    final KafkaStreams streams = new KafkaStreams(topology, props);
    final CountDownLatch latch = new CountDownLatch(1);
    
    // Ctrl+C를 처리하기 위한 핸들러 추가
    Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
      @Override
      public void run() {
        streams.close();
        System.out.println("topology started");
        latch.countDown();
        System.out.println("topology terminated");
      }
    });
  
    try {
      streams.start();
      latch.await();
    } catch (Throwable e) {
      System.exit(1);
    }
    System.exit(0);
  }
  
  
}

