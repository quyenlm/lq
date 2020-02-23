package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.transform.JsonUnmarshallerContext;
import com.amazonaws.transform.ListUnmarshaller;
import com.amazonaws.transform.SimpleTypeJsonUnmarshallers;
import com.amazonaws.transform.Unmarshaller;
import com.amazonaws.util.json.AwsJsonReader;

class ConditionJsonUnmarshaller implements Unmarshaller<Condition, JsonUnmarshallerContext> {
    private static ConditionJsonUnmarshaller instance;

    ConditionJsonUnmarshaller() {
    }

    public Condition unmarshall(JsonUnmarshallerContext context) throws Exception {
        Condition condition = new Condition();
        AwsJsonReader reader = context.getReader();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("AttributeValueList")) {
                condition.setAttributeValueList(new ListUnmarshaller(AttributeValueJsonUnmarshaller.getInstance()).unmarshall(context));
            } else if (name.equals("ComparisonOperator")) {
                condition.setComparisonOperator(SimpleTypeJsonUnmarshallers.StringJsonUnmarshaller.getInstance().unmarshall(context));
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return condition;
    }

    public static ConditionJsonUnmarshaller getInstance() {
        if (instance == null) {
            instance = new ConditionJsonUnmarshaller();
        }
        return instance;
    }
}
