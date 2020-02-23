package com.amazonaws.services.s3.model;

import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.s3.internal.Constants;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public enum Region {
    US_Standard((String) null),
    US_West("us-west-1"),
    US_West_2("us-west-2"),
    US_GovCloud("s3-us-gov-west-1"),
    EU_Ireland("eu-west-1", "EU"),
    EU_Frankfurt("eu-central-1"),
    AP_Mumbai("ap-south-1"),
    AP_Singapore("ap-southeast-1"),
    AP_Sydney("ap-southeast-2"),
    AP_Tokyo("ap-northeast-1"),
    AP_Seoul("ap-northeast-2"),
    SA_SaoPaulo("sa-east-1"),
    CN_Beijing("cn-north-1");
    
    public static final Pattern S3_REGIONAL_ENDPOINT_PATTERN = null;
    private final List<String> regionIds;

    static {
        S3_REGIONAL_ENDPOINT_PATTERN = Pattern.compile("s3[-.]([^.]+)\\.amazonaws\\.com(\\.[^.]*)?");
    }

    private Region(String... regionIds2) {
        this.regionIds = regionIds2 != null ? Arrays.asList(regionIds2) : null;
    }

    public String toString() {
        return getFirstRegionId0();
    }

    public String getFirstRegionId() {
        return getFirstRegionId0();
    }

    private String getFirstRegionId0() {
        if (this.regionIds == null || this.regionIds.size() == 0) {
            return null;
        }
        return this.regionIds.get(0);
    }

    public static Region fromValue(String s3RegionId) throws IllegalArgumentException {
        if (s3RegionId == null || s3RegionId.equals("US")) {
            return US_Standard;
        }
        for (Region region : values()) {
            List<String> regionIds2 = region.regionIds;
            if (regionIds2 != null && regionIds2.contains(s3RegionId)) {
                return region;
            }
        }
        throw new IllegalArgumentException("Cannot create enum from " + s3RegionId + " value!");
    }

    public com.amazonaws.regions.Region toAWSRegion() {
        String s3regionId = getFirstRegionId();
        if (s3regionId == null) {
            return RegionUtils.getRegionByEndpoint(Constants.S3_HOSTNAME);
        }
        return RegionUtils.getRegion(s3regionId);
    }
}
