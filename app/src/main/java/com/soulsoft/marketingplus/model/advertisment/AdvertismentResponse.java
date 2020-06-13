package com.soulsoft.marketingplus.model.advertisment;

import java.util.ArrayList;

public class AdvertismentResponse {
    private String Message;
    private int Responsecode;
    private ArrayList<AdvertisementBO> Data;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getResponsecode() {
        return Responsecode;
    }

    public void setResponsecode(int responsecode) {
        Responsecode = responsecode;
    }

    public ArrayList<AdvertisementBO> getData() {
        if(this.Data==null){
            this.Data=new ArrayList<>();
        }
        return Data;
    }

    public void setData(ArrayList<AdvertisementBO> data) {
        Data = data;
    }
}
