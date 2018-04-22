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

KTable<String, Long> counts =
source.flatMapValues(new ValueMapper<String, Iterable<String>>() {
  @Override
  public Iterable<String> apply(String value) {
