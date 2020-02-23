package com.amazonaws.auth.policy.actions;

import com.amazonaws.auth.policy.Action;

public enum DynamoDBv2Actions implements Action {
    AllDynamoDBv2Actions("dynamodb:*"),
    BatchGetItem("dynamodb:BatchGetItem"),
    BatchWriteItem("dynamodb:BatchWriteItem"),
    CreateTable("dynamodb:CreateTable"),
    DeleteItem("dynamodb:DeleteItem"),
    DeleteTable("dynamodb:DeleteTable"),
    DescribeLimits("dynamodb:DescribeLimits"),
    DescribeTable("dynamodb:DescribeTable"),
    GetItem("dynamodb:GetItem"),
    ListTables("dynamodb:ListTables"),
    PutItem("dynamodb:PutItem"),
    Query("dynamodb:Query"),
    Scan("dynamodb:Scan"),
    UpdateItem("dynamodb:UpdateItem"),
    UpdateTable("dynamodb:UpdateTable");
    
    private final String action;

    private DynamoDBv2Actions(String action2) {
        this.action = action2;
    }

    public String getActionName() {
        return this.action;
    }
}
