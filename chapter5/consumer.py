from kafka import KafkaConsumer

consumer = KafkaConsumer('peter-topic',group_id='peter-consumer',bootstrap_servers='peter-kafka001:9092,peter-kafka002:9092,peter-kafka003:9092', enable_auto_commit=True, auto_offset_reset='latest')
for message in consumer:
 print "Topic: %s, Partition: %d, Offset: %d, Key: %s, Value: %s" % (message.topic, message.partition, message.offset, message.key, message.value.decode('utf-8'))
