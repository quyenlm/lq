package com.amazonaws.auth.policy.conditions;

import com.amazonaws.auth.policy.Condition;
import com.amazonaws.auth.policy.conditions.StringCondition;
import com.amazonaws.services.s3.model.CannedAccessControlList;

public class S3ConditionFactory {
    public static final String CANNED_ACL_CONDITION_KEY = "s3:x-amz-acl";
    public static final String COPY_SOURCE_CONDITION_KEY = "s3:x-amz-copy-source";
    public static final String DELIMITER_CONDITION_KEY = "s3:delimiter";
    public static final String LOCATION_CONSTRAINT_CONDITION_KEY = "s3:LocationConstraint";
    public static final String MAX_KEYS_CONDITION_KEY = "s3:max-keys";
    public static final String METADATA_DIRECTIVE_CONDITION_KEY = "s3:x-amz-metadata-directive";
    public static final String PREFIX_CONDITION_KEY = "s3:prefix";
    public static final String VERSION_ID_CONDITION_KEY = "s3:VersionId";

    private S3ConditionFactory() {
    }

    public static Condition newCannedACLCondition(CannedAccessControlList cannedAcl) {
        return new StringCondition(StringCondition.StringComparisonType.StringEquals, CANNED_ACL_CONDITION_KEY, cannedAcl.toString());
    }
}
