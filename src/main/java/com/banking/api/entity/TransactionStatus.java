package com.banking.api.entity;

public enum TransactionStatus {
    SUCCESS("SUCCESS"),
    IN_PROGRESS("IN_PROGRESS"),
    FAILED("FAILED");

    private final String name;

    TransactionStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isSuccess() {
        return equals(SUCCESS);
    }

    public boolean isInProgress() {
        return equals(IN_PROGRESS);
    }

    public boolean isFailed() {
        return equals(FAILED);
    }
}
