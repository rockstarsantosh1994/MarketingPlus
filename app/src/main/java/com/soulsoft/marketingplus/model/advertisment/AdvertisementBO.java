package com.soulsoft.marketingplus.model.advertisment;

public class AdvertisementBO {
    private int adid;
    private String adtitle;
    private String addesc;
    private String city;
    private String adstate;
    private String zipcode;
    private String isactive;
    private String created;

    public int getAdid() {
        return adid;
    }

    public void setAdid(int adid) {
        this.adid = adid;
    }

    public String getAdtitle() {
        return adtitle;
    }

    public void setAdtitle(String adtitle) {
        this.adtitle = adtitle;
    }

    public String getAddesc() {
        return addesc;
    }

    public void setAddesc(String addesc) {
        this.addesc = addesc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdstate() {
        return adstate;
    }

    public void setAdstate(String adstate) {
        this.adstate = adstate;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getIsactive() {
        return isactive;
    }

    public void setIsactive(String isactive) {
        this.isactive = isactive;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
