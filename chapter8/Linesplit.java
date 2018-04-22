KStream<String, String> source = builder.stream("streams-plaintext-input");
KStream<String, String> words =
source.flatMapValues(new ValueMapper<String, Iterable<String>>(){
      @Override
      public Iterable<String> apply(String value) {
        return Arrays.asList(value.split("\\W+"));
      }
    });
