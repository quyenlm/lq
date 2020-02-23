package com.amazonaws.services.s3.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BucketNotificationConfiguration {
    private List<TopicConfiguration> topicConfigurations;

    public BucketNotificationConfiguration() {
        this.topicConfigurations = null;
        this.topicConfigurations = new ArrayList(1);
    }

    public BucketNotificationConfiguration(Collection<TopicConfiguration> topicConfigurations2) {
        this.topicConfigurations = null;
        this.topicConfigurations = new ArrayList(1);
        this.topicConfigurations.addAll(topicConfigurations2);
    }

    public BucketNotificationConfiguration withTopicConfigurations(TopicConfiguration... topicConfigurations2) {
        this.topicConfigurations.clear();
        for (TopicConfiguration add : topicConfigurations2) {
            this.topicConfigurations.add(add);
        }
        return this;
    }

    public void setTopicConfigurations(Collection<TopicConfiguration> topicConfigurations2) {
        this.topicConfigurations.clear();
        this.topicConfigurations.addAll(topicConfigurations2);
    }

    public List<TopicConfiguration> getTopicConfigurations() {
        return this.topicConfigurations;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        sb.append("TopicConfigurations: " + getTopicConfigurations());
        sb.append("}");
        return sb.toString();
    }

    public static class TopicConfiguration {
        private final String event;
        private final String topic;

        public TopicConfiguration(String topic2, String event2) {
            this.topic = topic2;
            this.event = event2;
        }

        public String getTopic() {
            return this.topic;
        }

        public String getEvent() {
            return this.event;
        }

        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append("{");
            sb.append("Topic: " + getTopic() + ", ");
            sb.append("Event: " + getEvent() + ", ");
            sb.append("}");
            return sb.toString();
        }
    }
}
