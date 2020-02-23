package com.amazonaws.util;

import com.amazonaws.metrics.MetricType;
import com.tencent.imsdk.framework.request.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Deprecated
public class AWSRequestMetricsFullSupport extends AWSRequestMetrics {
    private static final Object COMMA_SEPARATOR = ", ";
    private static final Object KEY_VALUE_SEPARATOR = HttpRequest.HTTP_REQ_ENTITY_MERGE;
    private static final Log latencyLogger = LogFactory.getLog("com.amazonaws.latency");
    private final Map<String, TimingInfo> eventsBeingProfiled = new HashMap();
    private final Map<String, List<Object>> properties = new HashMap();

    public AWSRequestMetricsFullSupport() {
        super(TimingInfo.startTimingFullSupport());
    }

    public void startEvent(String eventName) {
        this.eventsBeingProfiled.put(eventName, TimingInfo.startTimingFullSupport(System.nanoTime()));
    }

    public void startEvent(MetricType f) {
        startEvent(f.name());
    }

    public void endEvent(String eventName) {
        TimingInfo event = this.eventsBeingProfiled.get(eventName);
        if (event == null) {
            LogFactory.getLog((Class) getClass()).warn("Trying to end an event which was never started: " + eventName);
            return;
        }
        event.endTiming();
        this.timingInfo.addSubMeasurement(eventName, TimingInfo.unmodifiableTimingInfo(event.getStartTimeNano(), Long.valueOf(event.getEndTimeNano())));
    }

    public void endEvent(MetricType f) {
        endEvent(f.name());
    }

    public void incrementCounter(String event) {
        this.timingInfo.incrementCounter(event);
    }

    public void incrementCounter(MetricType f) {
        incrementCounter(f.name());
    }

    public void setCounter(String counterName, long count) {
        this.timingInfo.setCounter(counterName, count);
    }

    public void setCounter(MetricType f, long count) {
        setCounter(f.name(), count);
    }

    public void addProperty(String propertyName, Object value) {
        List<Object> propertyList = this.properties.get(propertyName);
        if (propertyList == null) {
            propertyList = new ArrayList<>();
            this.properties.put(propertyName, propertyList);
        }
        propertyList.add(value);
    }

    public void addProperty(MetricType f, Object value) {
        addProperty(f.name(), value);
    }

    public void log() {
        if (latencyLogger.isInfoEnabled()) {
            StringBuilder builder = new StringBuilder();
            for (Map.Entry<String, List<Object>> entry : this.properties.entrySet()) {
                keyValueFormat(entry.getKey(), entry.getValue(), builder);
            }
            for (Map.Entry<String, Number> entry2 : this.timingInfo.getAllCounters().entrySet()) {
                keyValueFormat(entry2.getKey(), entry2.getValue(), builder);
            }
            for (Map.Entry<String, List<TimingInfo>> entry3 : this.timingInfo.getSubMeasurementsByName().entrySet()) {
                keyValueFormat(entry3.getKey(), entry3.getValue(), builder);
            }
            latencyLogger.info(builder.toString());
        }
    }

    private void keyValueFormat(Object key, Object value, StringBuilder builder) {
        builder.append(key).append(KEY_VALUE_SEPARATOR).append(value).append(COMMA_SEPARATOR);
    }

    public List<Object> getProperty(String propertyName) {
        return this.properties.get(propertyName);
    }

    public List<Object> getProperty(MetricType f) {
        return getProperty(f.name());
    }

    public final boolean isEnabled() {
        return true;
    }
}
