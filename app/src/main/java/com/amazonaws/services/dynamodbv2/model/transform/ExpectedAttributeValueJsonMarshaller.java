package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.util.json.AwsJsonWriter;
import java.util.List;

class ExpectedAttributeValueJsonMarshaller {
    private static ExpectedAttributeValueJsonMarshaller instance;

    ExpectedAttributeValueJsonMarshaller() {
    }

    public void marshall(ExpectedAttributeValue expectedAttributeValue, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (expectedAttributeValue.getValue() != null) {
            AttributeValue value = expectedAttributeValue.getValue();
            jsonWriter.name("Value");
            AttributeValueJsonMarshaller.getInstance().marshall(value, jsonWriter);
        }
        if (expectedAttributeValue.getExists() != null) {
            Boolean exists = expectedAttributeValue.getExists();
            jsonWriter.name("Exists");
            jsonWriter.value(exists.booleanValue());
        }
        if (expectedAttributeValue.getComparisonOperator() != null) {
            String comparisonOperator = expectedAttributeValue.getComparisonOperator();
            jsonWriter.name("ComparisonOperator");
            jsonWriter.value(comparisonOperator);
        }
        if (expectedAttributeValue.getAttributeValueList() != null) {
            List<AttributeValue> attributeValueList = expectedAttributeValue.getAttributeValueList();
            jsonWriter.name("AttributeValueList");
            jsonWriter.beginArray();
            for (AttributeValue attributeValueListItem : attributeValueList) {
                if (attributeValueListItem != null) {
                    AttributeValueJsonMarshaller.getInstance().marshall(attributeValueListItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        jsonWriter.endObject();
    }

    public static ExpectedAttributeValueJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new ExpectedAttributeValueJsonMarshaller();
        }
        return instance;
    }
}
