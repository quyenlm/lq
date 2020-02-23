package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

class ConditionJsonMarshaller {
    private static ConditionJsonMarshaller instance;

    ConditionJsonMarshaller() {
    }

    public void marshall(Condition condition, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (condition.getAttributeValueList() != null) {
            List<AttributeValue> attributeValueList = condition.getAttributeValueList();
            jsonWriter.name("AttributeValueList");
            jsonWriter.beginArray();
            for (AttributeValue attributeValueListItem : attributeValueList) {
                if (attributeValueListItem != null) {
                    AttributeValueJsonMarshaller.getInstance().marshall(attributeValueListItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        if (condition.getComparisonOperator() != null) {
            String comparisonOperator = condition.getComparisonOperator();
            jsonWriter.name("ComparisonOperator");
            jsonWriter.value(comparisonOperator);
        }
        jsonWriter.endObject();
    }

    public static ConditionJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ConditionJsonMarshaller();
        }
        return instance;
    }
}
