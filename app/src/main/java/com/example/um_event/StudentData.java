package com.example.um_event;

public class StudentData {

    private String FullName;
    private String MatricNumber;
    private String Siswamail;

    public StudentData( String fullName,String matricNumber, String siswamail) {
        this. FullName = fullName;
        this.MatricNumber = matricNumber;
        this. Siswamail = siswamail;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getMatricNumber() {
        return MatricNumber;
    }

    public void setMatricNumber(String matricNumber) {
        MatricNumber = matricNumber;
    }

    public String getSiswamail() {
        return Siswamail;
    }

    public void setSiswamail(String siswamail) {
        Siswamail = siswamail;
    }
}
