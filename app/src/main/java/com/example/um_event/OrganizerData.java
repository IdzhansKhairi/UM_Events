package com.example.um_event;

public class OrganizerData {

    private String OrganizerID;
    private String Password;
    private String Email;
    private int PhoneNo;

    public OrganizerData(String organizerID, String password, String email, int phoneNo) {
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

    public int getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        PhoneNo = phoneNo;
    }
}
