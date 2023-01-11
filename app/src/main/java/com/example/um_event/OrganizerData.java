package com.example.um_event;

public class OrganizerData {

    private String OrganizerID;
    private String Password;
    private String Email;
    private String PhoneNo;

    public OrganizerData(String organizerID, String password, String email, String phoneNo) {
        this.OrganizerID = organizerID;
        this.Password = password;
        this.Email = email;
        this.PhoneNo = phoneNo;
    }

    public String getOrganizerID() {
        return OrganizerID;
    }

    public void setOrganizerID(String organizerID) {
        OrganizerID = organizerID;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }
}
