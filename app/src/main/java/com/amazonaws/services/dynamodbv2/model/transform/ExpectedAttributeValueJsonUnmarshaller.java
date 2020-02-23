package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class ExpectedAttributeValueJsonUnmarshaller implements Unmarshaller<ExpectedAttributeValue, JsonUnmarshallerContext> {
    private static ExpectedAttributeValueJsonUnmarshaller instance;

    ExpectedAttributeValueJsonUnmarshaller() {
    }

    public ExpectedAttributeValue unmarshall(JsonUnmarshallerContext context) throws Exception {
        ExpectedAttributeValue expectedAttributeValue = new ExpectedAttributeValue();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("Value")) {
                expectedAttributeValue.setValue(AttributeValueJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("Exists")) {
                expectedAttributeValue.setExists(SimpleTypeJsonUnmarshallers.BooleanJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("ComparisonOperator")) {
                expectedAttributeValue.setComparisonOperator(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else if (name.equals("AttributeValueList")) {
                expectedAttributeValue.setAttributeValueList(new ListUnmarshaller(AttributeValueJsonUnmarshaller.getInstance()).unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return expectedAttributeValue;
    }

    public static ExpectedAttributeValueJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ExpectedAttributeValueJsonUnmarshaller();
        }
        return instance;
    }
}
