package com.safetynet.safetynet.dto.responses;

public class MedicalRecordResponseDTO extends ResponseDTO{
    private String fName;
    private String lName;

    public MedicalRecordResponseDTO(String fName, String lName, String error, int statusCode) {
        super();
    }

    public String getFname() {
        return fName;
    }

    public void setFname(String fname) {
        this.fName = fname;
    }

    public String getLname() {
        return lName;
    }

    public void setLname(String lname) {
        this.lName = lname;
    }
}