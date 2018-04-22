from google.cloud import pubsub

project = 'machinbig'
topicname = 'MY_TOPIC_NAME'
publisher = pubsub.PublisherClient()
topic = 'projects/{project_id}/topics/{topic}'.format(
    project_id=project,
    topic=topicname,
)

try:
    project_path = publisher.project_path(project)
    for elem in publisher.list_topics(project_path):
        if topic in elem.name:
            publisher.delete_topic(elem.name)

    response = publisher.create_topic(topic)
    print "Topic {topic} is created".format(topic=response)
except Exception as e:
    print e

topic_path = publisher.topic_path(project, topicname)
for n in range(1, 10):
    data = u'Message number {}'.format(n)
    # Data must be a bytestring
    data = data.encode('utf-8')
    publisher.publish(topic_path, data=data)
