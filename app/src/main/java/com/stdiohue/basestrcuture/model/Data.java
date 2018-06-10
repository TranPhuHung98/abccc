package com.stdiohue.basestrcuture.model;

public class Data {
    private String Ten;
    private String SoDT;

    public Data(String ten, String soDT) {
        Ten = ten;
        SoDT = soDT;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public String getSoDT() {
        return SoDT;
    }

    public void setSoDT(String soDT) {
        SoDT = soDT;
    }
}
