from kafka import KafkaProducer

producer = KafkaProducer(bootstrap_servers='peter-kafka001:9092,peter-kafka002:9092,peter-kafka003:9092')

producer.send('peter-topic', 'Apache Kafka is a distributed streaming platform')
