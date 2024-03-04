package com.example.sqllitedemo.model;

import java.io.Serializable;

public class User implements Serializable {
    private String maU;
    private String tenU;
    private String phoneU;

    public User() {
    }

    public User(String maU, String tenU, String phoneU) {
        this.maU = maU;
        this.tenU = tenU;
        this.phoneU = phoneU;
    }

    public String getMaU() {
        return maU;
    }

    public void setMaU(String maU) {
        this.maU = maU;
    }

    public String getTenU() {
        return tenU;
    }

    public void setTenU(String tenU) {
        this.tenU = tenU;
    }

    public String getPhoneU() {
        return phoneU;
    }

    public void setPhoneU(String phoneU) {
        this.phoneU = phoneU;
    }

    @Override
    public String toString() {
        return maU + "\t" + tenU + "\t" + phoneU;
    }
}
