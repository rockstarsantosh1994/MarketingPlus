package com.soulsoft.marketingplus.model;

public class LoginResponse {
    private int Responsecode;
    private String Message;
    private LoginData Data;

    public int getResponsecode() {
        return Responsecode;
    }

    public void setResponsecode(int responsecode) {
        Responsecode = responsecode;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public LoginData getData() {
        return Data;
    }

    public void setData(LoginData data) {
        Data = data;
    }
}
