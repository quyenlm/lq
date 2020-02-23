package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.ItemCollectionMetrics;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.MapUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class ItemCollectionMetricsJsonUnmarshaller implements Unmarshaller<ItemCollectionMetrics, JsonUnmarshallerContext> {
    private static ItemCollectionMetricsJsonUnmarshaller instance;

    ItemCollectionMetricsJsonUnmarshaller() {
    }

    public ItemCollectionMetrics unmarshall(JsonUnmarshallerContext context) throws Exception {
        ItemCollectionMetrics itemCollectionMetrics = new ItemCollectionMetrics();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ItemCollectionKey")) {
                itemCollectionMetrics.setItemCollectionKey(new MapUnmarshaller(AttributeValueJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("SizeEstimateRangeGB")) {
                itemCollectionMetrics.setSizeEstimateRangeGB(new ListUnmarshaller(SimpleTypeJsonUnmarshallers.DoubleJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return itemCollectionMetrics;
    }

    public static ItemCollectionMetricsJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ItemCollectionMetricsJsonUnmarshaller();
        }
        return instance;
    }
}
