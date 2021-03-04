package com.example.bechde;

public class UserHelperClass {
    String fullname;
    String phone;
    String email;
    String username;


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }




    public UserHelperClass(String fullname, String phone, String email ,String username){
        this.fullname = fullname;
        this.phone = phone;
        this.email = email;
        this.username = username;
    }

}
