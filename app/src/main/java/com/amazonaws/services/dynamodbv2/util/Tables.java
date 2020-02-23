package com.amazonaws.services.dynamodbv2.util;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.model.DescribeTableRequest;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.amazonaws.services.dynamodbv2.model.TableStatus;

public class Tables {
    private static final int DEFAULT_WAIT_INTERVAL = 20000;
    private static final int DEFAULT_WAIT_TIMEOUT = 600000;

    public static boolean doesTableExist(AmazonDynamoDB dynamo, String tableName) {
        try {
            return TableStatus.ACTIVE.toString().equals(dynamo.describeTable(new DescribeTableRequest(tableName)).getTable().getTableStatus());
        } catch (ResourceNotFoundException e) {
            return false;
        }
    }

    public static void waitForTableToBecomeActive(AmazonDynamoDB dynamo, String tableName) {
        waitForTableToBecomeActive(dynamo, tableName, 600000, 20000);
    }

    public static void waitForTableToBecomeActive(AmazonDynamoDB dynamo, String tableName, int timeout, int interval) {
        if (timeout < 0) {
            throw new AmazonClientException("Timeout must be >= 0");
        } else if (interval <= 0 || interval >= timeout) {
            throw new AmazonClientException("Interval must be > 0 and < timeout");
        } else {
            long endTime = System.currentTimeMillis() + ((long) timeout);
            while (System.currentTimeMillis() < endTime) {
                try {
                    TableDescription table = dynamo.describeTable(new DescribeTableRequest(tableName)).getTable();
                    if (table != null && table.getTableStatus().equals(TableStatus.ACTIVE.toString())) {
                        return;
                    }
                } catch (ResourceNotFoundException e) {
                }
                try {
                    Thread.sleep((long) interval);
                } catch (InterruptedException e2) {
                    Thread.interrupted();
                    throw new AmazonClientException("Interrupted while waiting for table to become active", e2);
                }
            }
            throw new AmazonClientException("Table " + tableName + " never became active");
        }
    }
}
