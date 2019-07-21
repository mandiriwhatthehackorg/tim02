package com.ainosi.iman.bankmandiri.model;

public class BalanceModel {
    private String ledgerBalance;
    private String yesterdayLedgerBalance;
    private String previousStatementBalance;
    private String prevYearEndLedgerBalance;
    private String minimumBalance;
    private String maximumBalance;
    private String collectedBalance;


    // Getter Methods

    public String getLedgerBalance() {
        return ledgerBalance;
    }

    public String getYesterdayLedgerBalance() {
        return yesterdayLedgerBalance;
    }

    public String getPreviousStatementBalance() {
        return previousStatementBalance;
    }

    public String getPrevYearEndLedgerBalance() {
        return prevYearEndLedgerBalance;
    }

    public String getMinimumBalance() {
        return minimumBalance;
    }

    public String getMaximumBalance() {
        return maximumBalance;
    }

    public String getCollectedBalance() {
        return collectedBalance;
    }

    // Setter Methods

    public void setLedgerBalance(String ledgerBalance) {
        this.ledgerBalance = ledgerBalance;
    }

    public void setYesterdayLedgerBalance(String yesterdayLedgerBalance) {
        this.yesterdayLedgerBalance = yesterdayLedgerBalance;
    }

    public void setPreviousStatementBalance(String previousStatementBalance) {
        this.previousStatementBalance = previousStatementBalance;
    }

    public void setPrevYearEndLedgerBalance(String prevYearEndLedgerBalance) {
        this.prevYearEndLedgerBalance = prevYearEndLedgerBalance;
    }

    public void setMinimumBalance(String minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public void setMaximumBalance(String maximumBalance) {
        this.maximumBalance = maximumBalance;
    }

    public void setCollectedBalance(String collectedBalance) {
        this.collectedBalance = collectedBalance;
    }
}
