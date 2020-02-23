package com.amazonaws.services.dynamodbv2.model.transform;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.util.json.AwsJsonWriter;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

class AttributeValueJsonMarshaller {
    private static AttributeValueJsonMarshaller instance;

    AttributeValueJsonMarshaller() {
    }

    public void marshall(AttributeValue attributeValue, AwsJsonWriter jsonWriter) throws Exception {
        jsonWriter.beginObject();
        if (attributeValue.getS() != null) {
            String s = attributeValue.getS();
            jsonWriter.name("S");
            jsonWriter.value(s);
        }
        if (attributeValue.getN() != null) {
            String n = attributeValue.getN();
            jsonWriter.name("N");
            jsonWriter.value(n);
        }
        if (attributeValue.getB() != null) {
            ByteBuffer b = attributeValue.getB();
            jsonWriter.name("B");
            jsonWriter.value(b);
        }
        if (attributeValue.getSS() != null) {
            List<String> sS = attributeValue.getSS();
            jsonWriter.name("SS");
            jsonWriter.beginArray();
            for (String sSItem : sS) {
                if (sSItem != null) {
                    jsonWriter.value(sSItem);
                }
            }
            jsonWriter.endArray();
        }
        if (attributeValue.getNS() != null) {
            List<String> nS = attributeValue.getNS();
            jsonWriter.name("NS");
            jsonWriter.beginArray();
            for (String nSItem : nS) {
                if (nSItem != null) {
                    jsonWriter.value(nSItem);
                }
            }
            jsonWriter.endArray();
        }
        if (attributeValue.getBS() != null) {
            List<ByteBuffer> bS = attributeValue.getBS();
            jsonWriter.name("BS");
            jsonWriter.beginArray();
            for (ByteBuffer bSItem : bS) {
                if (bSItem != null) {
                    jsonWriter.value(bSItem);
                }
            }
            jsonWriter.endArray();
        }
        if (attributeValue.getM() != null) {
            Map<String, AttributeValue> m = attributeValue.getM();
            jsonWriter.name("M");
            jsonWriter.beginObject();
            for (Map.Entry<String, AttributeValue> mEntry : m.entrySet()) {
                AttributeValue mValue = mEntry.getValue();
                if (mValue != null) {
                    jsonWriter.name(mEntry.getKey());
                    getInstance().marshall(mValue, jsonWriter);
                }
            }
            jsonWriter.endObject();
        }
        if (attributeValue.getL() != null) {
            List<AttributeValue> l = attributeValue.getL();
            jsonWriter.name("L");
            jsonWriter.beginArray();
            for (AttributeValue lItem : l) {
                if (lItem != null) {
                    getInstance().marshall(lItem, jsonWriter);
                }
            }
            jsonWriter.endArray();
        }
        if (attributeValue.getNULL() != null) {
            Boolean nULL = attributeValue.getNULL();
            jsonWriter.name("NULL");
            jsonWriter.value(nULL.booleanValue());
        }
        if (attributeValue.getBOOL() != null) {
            Boolean bOOL = attributeValue.getBOOL();
            jsonWriter.name("BOOL");
            jsonWriter.value(bOOL.booleanValue());
        }
        jsonWriter.endObject();
    }

    public static AttributeValueJsonMarshaller getInstance() {
        if (instance == null) {
            instance = new AttributeValueJsonMarshaller();
        }
        return instance;
    }
}
