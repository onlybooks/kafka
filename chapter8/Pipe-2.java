final CountDownLatch latch = new CountDownLatch(1);

// Ctrl+C를 처리하기 위한 핸들러 추가
Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
  @Override
  public void run() {
    streams.close();
    latch.countDown();
  }
})

try {
  streams.start();
  latch.await();
} catch (Throwable e) {
  System.exit(1)
}
System.exit(0)

final KafkaStreams streams = new KafkaStreams(topology, props)
