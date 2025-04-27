package com.hmapp.model;

public class Doctor {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;
    private String specialization;
    private String phoneNumber;
    private String email;
    private String qualification;
    private String experience;
    private String consultationFee;
    
    // Constructors
    public Doctor() {}
    
    public Doctor(String firstName, String lastName, String gender, String specialization, 
                 String phoneNumber, String email, String qualification, 
                 String experience, String consultationFee) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.specialization = specialization;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.qualification = qualification;
        this.experience = experience;
        this.consultationFee = consultationFee;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public String getSpecialization() {
        return specialization;
    }
    
    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getQualification() {
        return qualification;
    }
    
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
    
    public String getExperience() {
        return experience;
    }
    
    public void setExperience(String experience) {
        this.experience = experience;
    }
    
    public String getConsultationFee() {
        return consultationFee;
    }
    
    public void setConsultationFee(String consultationFee) {
        this.consultationFee = consultationFee;
    }
} 