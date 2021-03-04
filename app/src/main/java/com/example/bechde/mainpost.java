package com.example.bechde;

public class mainpost {
    String price;
    String desc;
    String location;
    String imgurl;

    @Override
    public String toString() {
        return "mainpost{" +
                "price='" + price + '\'' +
                ", desc='" + desc + '\'' +
                ", location='" + location + '\'' +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public mainpost() {
    }

    public mainpost(String price, String desc, String location, String imgurl) {
        this.price = price;
        this.desc = desc;
        this.location = location;
        this.imgurl = imgurl;
    }
}
