public KsqlEngine(final KsqlConfig ksqlConfig, final KafkaTopicClient topicClient) { 
  Objects.requireNonNull(ksqlConfig, "Streams properties map cannot be null as it may be mutated later on");  
  this.ksqlConfig = ksqlConfig;
  this.metaStore = new MetaStoreImpl();
  this.topicClient = topicClient; 
  this.ddlCommandExec = new DDLCommandExec(metaStore);
  this.queryEngine = new QueryEngine(this, new CommandFactories(topicClient));
  this.persistentQueries = new HashMap<>();
  this.liveQueries = new HashSet<>();
  this.functionRegistry = new FunctionRegistry();
  ) }
