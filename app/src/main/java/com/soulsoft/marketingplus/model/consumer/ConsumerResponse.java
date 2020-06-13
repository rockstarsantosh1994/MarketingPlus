package com.soulsoft.marketingplus.model.consumer;

import java.util.ArrayList;

public class ConsumerResponse {

    private int Responsecode;
    private String Message;
    private ArrayList<ConsumerBO> Data;

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

    public ArrayList<ConsumerBO> getData() {
        return Data;
    }

    public void setData(ArrayList<ConsumerBO> data) {
        Data = data;
    }
}
