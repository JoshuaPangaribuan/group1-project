package com.group1.app.entity.enums;

public enum TransferType {
    CASH_IN,
    CASH_OUT;

    @Override
    public String toString() {
        switch (this) {
            case CASH_IN:
                return "CASH IN";
            case CASH_OUT:
                return "CASH OUT";
            default:
                return "UNKNOWN";
        }
    }
}
