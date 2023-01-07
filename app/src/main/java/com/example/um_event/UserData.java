package com.example.um_event;

public class UserData {
    private String MatricNumber;
    private String Username;
    private String Password;
    private String DesiredEvent;

    public UserData(String matricNumber, String username, String password, String desiredEvent) {
        this.MatricNumber = matricNumber;
        this.Username = username;
        this.Password = password;
        this.DesiredEvent = desiredEvent;
    }

    public String getMatricNumber() {
        return MatricNumber;
    }

    public void setMatricNumber(String matricNumber) {
        MatricNumber = matricNumber;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getDesiredEvent() {
        return DesiredEvent;
    }

    public void setDesiredEvent(String desiredEvent) {
        DesiredEvent = desiredEvent;
    }
}
