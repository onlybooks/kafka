from kafka import KafkaProducer

producer = KafkaProducer(acks=1, compression_type='gzip', bootstrap_servers='peter-kafka001:9092,peter-kafka002:9092,peter-kafka003:9092')
for i in range(1, 11):
	if i % 2 == 1:
		producer.send('peter-topic2', key='1', value='%d - Apache Kafka is a distributed streaming platform - key=1' % i)
 	else:
		producer.send('peter-topic2', key='2', value='%d - Apache Kafka is a distributed streaming platform - key=2' % i)
