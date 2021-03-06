package com.example.bechde;

public class adhelperclass {
    String adtitle,price,desc,location,howold,category,imgurl;

    public adhelperclass() {
    }

    @Override
    public String toString() {
        return "adhelperclass{" +
                "adtitle='" + adtitle + '\'' +
                ", price='" + price + '\'' +
                ", desc='" + desc + '\'' +
                ", location='" + location + '\'' +
                ", howold='" + howold + '\'' +
                ", category='" + category + '\'' +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }

    public String getAdtitle() {
        return adtitle;
    }

    public void setAdtitle(String adtitle) {
        this.adtitle = adtitle;
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

    public String getHowold() {
        return howold;
    }

    public void setHowold(String howold) {
        this.howold = howold;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public adhelperclass(String adtitle, String price, String desc, String location, String howold, String category, String imgurl) {
        this.adtitle = adtitle;
        this.price = price;
        this.desc = desc;
        this.location = location;
        this.howold = howold;
        this.category = category;
        this.imgurl = imgurl;
    }
}
