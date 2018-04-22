from google.cloud import pubsub
import time

project = 'machinbig'
topicname = 'MY_TOPIC_NAME'
subscription_name = 'MY_SUBSCRIPTION_NAME'

subscriber = pubsub.SubscriberClient()

topic_path = subscriber.topic_path(project, topicname)

try:
    subscription = subscriber.create_subscription(name, topic_path)
except Exception as e:
    print(e)

subscription_path = subscriber.subscription_path(project, subscription_name)


def callback(message):
    print('Received message: {}'.format(message))
    message.ack()

subscriber.subscribe(subscription_path, callback=callback)

print('Listening for messages on {}'.format(subscription_path))

while True: 
    time.sleep(60)
