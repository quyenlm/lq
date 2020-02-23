package com.amazonaws.services.s3.internal;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.internal.ObjectExpirationResult;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ObjectExpirationHeaderHandler<T extends ObjectExpirationResult> implements HeaderHandler<T> {
    private static final Pattern datePattern = Pattern.compile("expiry-date=\"(.*?)\"");
    private static final Log log = LogFactory.getLog(ObjectExpirationHeaderHandler.class);
    private static final Pattern rulePattern = Pattern.compile("rule-id=\"(.*?)\"");

    public void handle(T result, HttpResponse response) {
        String expirationHeader = response.getHeaders().get(Headers.EXPIRATION);
        if (expirationHeader != null) {
            result.setExpirationTime(parseDate(expirationHeader));
            result.setExpirationTimeRuleId(parseRuleId(expirationHeader));
        }
    }

    private String parseRuleId(String expirationHeader) {
        Matcher matcher = rulePattern.matcher(expirationHeader);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private Date parseDate(String expirationHeader) {
        Matcher matcher = datePattern.matcher(expirationHeader);
        if (matcher.find()) {
            try {
                return ServiceUtils.parseRfc822Date(matcher.group(1));
            } catch (Exception exception) {
                log.warn("Error parsing expiry-date from x-amz-expiration header.", exception);
            }
        }
        return null;
    }
}
