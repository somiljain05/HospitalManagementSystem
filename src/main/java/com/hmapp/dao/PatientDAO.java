package com.hmapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.hmapp.model.Patient;
import com.hmapp.util.DatabaseUtil;

public class PatientDAO {
    private static final Logger logger = Logger.getLogger(PatientDAO.class.getName());
    
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            logger.info("Executing query: " + sql);
            int count = 0;
            
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setId(rs.getInt("id"));
                patient.setFirstName(rs.getString("first_name"));
                patient.setLastName(rs.getString("last_name"));
                patient.setDateOfBirth(rs.getDate("date_of_birth"));
                patient.setGender(rs.getString("gender"));
                patient.setPhoneNumber(rs.getString("phone_number"));
                patient.setEmail(rs.getString("email"));
                patient.setAddress(rs.getString("address"));
                patient.setBloodGroup(rs.getString("blood_group"));
                patients.add(patient);
                count++;
                
                // Log each patient's details
                logger.info("Patient #" + count + ":");
                logger.info("  ID: " + patient.getId());
                logger.info("  Name: " + patient.getFirstName() + " " + patient.getLastName());
                logger.info("  DOB: " + patient.getDateOfBirth());
                logger.info("  Gender: " + patient.getGender());
                logger.info("  Phone: " + patient.getPhoneNumber());
                logger.info("  Email: " + patient.getEmail());
                logger.info("  Address: " + patient.getAddress());
                logger.info("  Blood Group: " + patient.getBloodGroup());
            }
            
            if (count == 0) {
                logger.info("No patients found in the database");
            } else {
                logger.info("Successfully retrieved " + count + " patients from database");
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving patients: " + e.getMessage());
            logger.severe("SQL State: " + e.getSQLState());
            logger.severe("Error Code: " + e.getErrorCode());
            throw new RuntimeException("Failed to retrieve patients", e);
        }
        
        return patients;
    }
    
    public Patient getPatientById(int id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        Patient patient = null;
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    patient = new Patient();
                    patient.setId(rs.getInt("id"));
                    patient.setFirstName(rs.getString("first_name"));
                    patient.setLastName(rs.getString("last_name"));
                    patient.setDateOfBirth(rs.getDate("date_of_birth"));
                    patient.setGender(rs.getString("gender"));
                    patient.setPhoneNumber(rs.getString("phone_number"));
                    patient.setEmail(rs.getString("email"));
                    patient.setAddress(rs.getString("address"));
                    patient.setBloodGroup(rs.getString("blood_group"));
                    logger.info("Successfully retrieved patient with ID: " + id);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving patient with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to retrieve patient", e);
        }
        
        return patient;
    }
    
    public boolean addPatient(Patient patient) {
        String sql = "INSERT INTO patients (first_name, last_name, date_of_birth, gender, phone_number, email, address, blood_group) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setDate(3, new java.sql.Date(patient.getDateOfBirth().getTime()));
            stmt.setString(4, patient.getGender());
            stmt.setString(5, patient.getPhoneNumber());
            stmt.setString(6, patient.getEmail());
            stmt.setString(7, patient.getAddress());
            stmt.setString(8, patient.getBloodGroup());
            
            int rowsAffected = stmt.executeUpdate();
            logger.info("Successfully added new patient: " + patient.getFirstName() + " " + patient.getLastName());
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.severe("Error adding patient: " + e.getMessage());
            throw new RuntimeException("Failed to add patient", e);
        }
    }
    
    public boolean updatePatient(Patient patient) {
        String sql = "UPDATE patients SET first_name = ?, last_name = ?, date_of_birth = ?, gender = ?, phone_number = ?, email = ?, address = ?, blood_group = ? WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, patient.getFirstName());
            stmt.setString(2, patient.getLastName());
            stmt.setDate(3, new java.sql.Date(patient.getDateOfBirth().getTime()));
            stmt.setString(4, patient.getGender());
            stmt.setString(5, patient.getPhoneNumber());
            stmt.setString(6, patient.getEmail());
            stmt.setString(7, patient.getAddress());
            stmt.setString(8, patient.getBloodGroup());
            stmt.setInt(9, patient.getId());
            
            int rowsAffected = stmt.executeUpdate();
            logger.info("Successfully updated patient with ID: " + patient.getId());
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.severe("Error updating patient: " + e.getMessage());
            throw new RuntimeException("Failed to update patient", e);
        }
    }
    
    public boolean deletePatient(int id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Successfully deleted patient with ID: " + id);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.severe("Error deleting patient with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to delete patient", e);
        }
    }
} 