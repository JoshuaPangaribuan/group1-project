package com.group1.app.entity;

import com.group1.app.entity.enums.TransferType;

public class TransferHistory {
    private String fromAccountName;
    private String fromBankLabel;
    private String toAccountName;
    private String toBankLabel;
    private Double amount;
    private TransferType transferType;

    public TransferHistory(String fromAccountName, String fromBankLabel, String toAccountName, String toBankLabel,
            Double amount, TransferType transferType) {
        this.fromAccountName = fromAccountName;
        this.fromBankLabel = fromBankLabel;
        this.toAccountName = toAccountName;
        this.toBankLabel = toBankLabel;
        this.amount = amount;
        this.transferType = transferType;
    }

    public String getFromAccountName() {
        return fromAccountName;
    }

    public void setFromAccountName(String fromAccountName) {
        this.fromAccountName = fromAccountName;
    }

    public String getFromBankLabel() {
        return fromBankLabel;
    }

    public void setFromBankLabel(String fromBankLabel) {
        this.fromBankLabel = fromBankLabel;
    }

    public String getToAccountName() {
        return toAccountName;
    }

    public void setToAccountName(String toAccountName) {
        this.toAccountName = toAccountName;
    }

    public String getToBankLabel() {
        return toBankLabel;
    }

    public void setToBankLabel(String toBankLabel) {
        this.toBankLabel = toBankLabel;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(TransferType transferType) {
        this.transferType = transferType;
    }

}
