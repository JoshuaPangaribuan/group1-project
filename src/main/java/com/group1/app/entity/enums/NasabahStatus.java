package com.group1.app.entity.enums;

public enum NasabahStatus {
    UNKNOWN,
    ACTIVE,
    PENDING_ACTIVE, // State Awal
    INACTIVE;

    @Override
    public String toString() {
        return switch (this) {
            case ACTIVE -> "ACTIVE";
            case INACTIVE -> "INACTIVE";
            case PENDING_ACTIVE -> "PENDING_ACTIVE";
            default -> "UNKNOWN";
        };
    }
}
