from kafka import KafkaConsumer
consumer = KafkaConsumer('peter-topic',group_id='peter-consumer',bootstrap_servers='peter-kafka001:9092,peter-kafka002:9092,peter-kafka003:9092',auto_offset_reset='earliest')
for message in consumer:
	print "Message: %s" % message.value)
