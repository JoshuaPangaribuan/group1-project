package com.group1.app.entity.enums;

public enum AccountRoles {
    UNKNOWN,
    NASABAH,
    ADMIN,
    BANK;

    @Override
    public String toString() {
        return switch (this) {
            case NASABAH -> "NASABAH";
            case ADMIN -> "ADMIN";
            case BANK -> "BANK";
            default -> "UNKNOWN";
        };
    }
}
