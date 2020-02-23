package com.amazonaws.services.s3.model.transform;

import com.amazonaws.services.s3.internal.Constants;
import com.amazonaws.services.s3.internal.XmlWriter;
import com.amazonaws.services.s3.model.RequestPaymentConfiguration;

public class RequestPaymentConfigurationXmlFactory {
    public byte[] convertToXmlByteArray(RequestPaymentConfiguration requestPaymentConfiguration) {
        XmlWriter xml = new XmlWriter();
        xml.start("RequestPaymentConfiguration", "xmlns", Constants.XML_NAMESPACE);
        RequestPaymentConfiguration.Payer payer = requestPaymentConfiguration.getPayer();
        if (payer != null) {
            XmlWriter payerDocumentElement = xml.start("Payer");
            payerDocumentElement.value(payer.toString());
            payerDocumentElement.end();
        }
        xml.end();
        return xml.getBytes();
    }
}
