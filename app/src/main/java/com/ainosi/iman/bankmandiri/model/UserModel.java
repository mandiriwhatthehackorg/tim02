package com.ainosi.iman.bankmandiri.model;

public class UserModel {
    private String cifnumber, accountnumber, username, password, name, balance;
    private int set_a, set_b, set_c;

    public int getSet_a() {
        return set_a;
    }

    public void setSet_a(int set_a) {
        this.set_a = set_a;
    }

    public int getSet_b() {
        return set_b;
    }

    public void setSet_b(int set_b) {
        this.set_b = set_b;
    }

    public int getSet_c() {
        return set_c;
    }

    public void setSet_c(int set_c) {
        this.set_c = set_c;
    }

    public String getCifnumber() {
        return cifnumber;
    }

    public void setCifnumber(String cifnumber) {
        this.cifnumber = cifnumber;
    }

    public String getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(String accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
