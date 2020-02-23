package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ItemCollectionMetrics;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;
import java.util.Map;

class ItemCollectionMetricsJsonMarshaller {
    private static ItemCollectionMetricsJsonMarshaller instance;

    ItemCollectionMetricsJsonMarshaller() {
    }

    public void marshall(ItemCollectionMetrics itemCollectionMetrics, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (itemCollectionMetrics.getItemCollectionKey() != null) {
            Map<String, AttributeValue> itemCollectionKey = itemCollectionMetrics.getItemCollectionKey();
            jsonWriter.name("ItemCollectionKey");
            jsonWriter.beginObject();
            for (Map.Entry<String, AttributeValue> itemCollectionKeyEntry : itemCollectionKey.entrySet()) {
                AttributeValue itemCollectionKeyValue = itemCollectionKeyEntry.getValue();
                if (itemCollectionKeyValue != null) {
                    jsonWriter.name(itemCollectionKeyEntry.getKey());
                    AttributeValueJsonMarshaller.getInstance().marshall(itemCollectionKeyValue, jsonWriter);
                }
            }
            jsonWriter.endObject();
        }
        if (itemCollectionMetrics.getSizeEstimateRangeGB() != null) {
            List<Double> sizeEstimateRangeGB = itemCollectionMetrics.getSizeEstimateRangeGB();
            jsonWriter.name("SizeEstimateRangeGB");
            jsonWriter.beginArray();
            for (Double sizeEstimateRangeGBItem : sizeEstimateRangeGB) {
                if (sizeEstimateRangeGBItem != null) {
                    jsonWriter.value((Number) sizeEstimateRangeGBItem);
                }
            }
            jsonWriter.endArray();
        }
        jsonWriter.endObject();
    }

    public static ItemCollectionMetricsJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ItemCollectionMetricsJsonMarshaller();
        }
        return instance;
    }
}
