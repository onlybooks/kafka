from kafka import KafkaProducer
from kafka.errors import KafkaError

producer = KafkaProducer(acks=1,retries=1,bootstrap_servers='peter-kafka001:9092,peter-kafka002:9092,peter-kafka003:9092')

for i in range(1, 1001):
 producer.send('peter-topic', b'Apache Kafka is a distributed streaming platform - %d' % i)
