package com.amazonaws.services.s3.internal;

import java.util.Date;

public interface ObjectExpirationResult {
    Date getExpirationTime();

    String getExpirationTimeRuleId();

    void setExpirationTime(Date date);

    void setExpirationTimeRuleId(String str);
}
