package com.amazonaws.services.s3.internal;

import com.amazonaws.http.HttpResponse;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.internal.ObjectRestoreResult;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ObjectRestoreHeaderHandler<T extends ObjectRestoreResult> implements HeaderHandler<T> {
    private static final Pattern datePattern = Pattern.compile("expiry-date=\"(.*?)\"");
    private static final Log log = LogFactory.getLog(ObjectRestoreHeaderHandler.class);
    private static final Pattern ongoingPattern = Pattern.compile("ongoing-request=\"(.*?)\"");

    public void handle(T result, HttpResponse response) {
        String restoreHeader = response.getHeaders().get(Headers.RESTORE);
        if (restoreHeader != null) {
            result.setRestoreExpirationTime(parseDate(restoreHeader));
            result.setOngoingRestore(parseBoolean(restoreHeader).booleanValue());
        }
    }

    private Date parseDate(String restoreHeader) {
        Matcher matcher = datePattern.matcher(restoreHeader);
        if (matcher.find()) {
            try {
                return ServiceUtils.parseRfc822Date(matcher.group(1));
            } catch (Exception exception) {
                log.warn("Error parsing expiry-date from x-amz-restore header.", exception);
            }
        }
        return null;
    }

    private Boolean parseBoolean(String restoreHeader) {
        Matcher matcher = ongoingPattern.matcher(restoreHeader);
        if (matcher.find()) {
            return Boolean.valueOf(Boolean.parseBoolean(matcher.group(1)));
        }
        return null;
    }
}
