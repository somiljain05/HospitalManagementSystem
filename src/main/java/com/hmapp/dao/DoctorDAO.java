package com.hmapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.hmapp.model.Doctor;
import com.hmapp.util.DatabaseUtil;

public class DoctorDAO {
	private static final Logger logger = Logger.getLogger(DoctorDAO.class.getName());
    
	public List<Doctor> getAllDoctors() {
		List<Doctor> doctors = new ArrayList<>();
		String sql = "SELECT * FROM doctors";
		
		try (Connection conn = DatabaseUtil.getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql);
	             ResultSet rs = stmt.executeQuery()) {
	            
	            logger.info("Executing query: " + sql);
	            int count = 0;
	            
	            while (rs.next()) {
	                Doctor doctor = new Doctor();
	                doctor.setId(rs.getInt("id"));
	                doctor.setFirstName(rs.getString("first_name"));
	                doctor.setLastName(rs.getString("last_name"));
	                doctor.setGender(rs.getString("gender"));
	                doctor.setSpecialization(rs.getString("specialization"));
	                doctor.setPhoneNumber(rs.getString("phone_number"));
	                doctor.setEmail(rs.getString("email"));
	                doctor.setQualification(rs.getString("qualification"));
	                doctor.setExperience(rs.getString("experience"));
	                doctor.setConsultationFee(rs.getString("consultation_fee"));
	                doctors.add(doctor);
	                count++;
	                
	                // Log each doctor's details
	                logger.info("Doctor #" + count + ":");
	                logger.info("  ID: " + doctor.getId());
	                logger.info("  Name: " + doctor.getFirstName() + " " + doctor.getLastName());
	                logger.info("  Gender: " + doctor.getGender());
	                logger.info("  Specialization: " + doctor.getSpecialization());
	                logger.info("  Phone: " + doctor.getPhoneNumber());
	                logger.info("  Email: " + doctor.getEmail());
	                logger.info("  Qualification: " + doctor.getQualification());
	                logger.info("  Experience: " + doctor.getExperience());
	                logger.info("  Consultation Fee: " + doctor.getConsultationFee());
	            }
	            
	            if (count == 0) {
	                logger.info("No Doctors found in the database");
	            } else {
	                logger.info("Successfully retrieved " + count + " doctors from database");
	            }
	        } catch (SQLException e) {
	            logger.severe("Error retrieving doctors: " + e.getMessage());
	            logger.severe("SQL State: " + e.getSQLState());
	            logger.severe("Error Code: " + e.getErrorCode());
	            throw new RuntimeException("Failed to retrieve doctors", e);
	        }
        return doctors;
	}
	
	public Doctor getDoctorById(int id) {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        Doctor doctor = null;
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    doctor = new Doctor();
                    doctor.setId(rs.getInt("id"));
                    doctor.setFirstName(rs.getString("first_name"));
                    doctor.setLastName(rs.getString("last_name"));
                    doctor.setGender(rs.getString("gender"));
                    doctor.setSpecialization(rs.getString("specialization"));
                    doctor.setPhoneNumber(rs.getString("phone_number"));
                    doctor.setEmail(rs.getString("email"));
                    doctor.setQualification(rs.getString("qualification"));
                    doctor.setExperience(rs.getString("experience"));
                    doctor.setConsultationFee(rs.getString("consultation_fee"));
                    logger.info("Successfully retrieved doctor with ID: " + id);
                }
            }
        } catch (SQLException e) {
            logger.severe("Error retrieving doctor with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to retrieve doctor", e);
        }
        
        return doctor;
    }
	
	public boolean addDoctor(Doctor doctor) {
        String sql = "INSERT INTO doctors (first_name, last_name, gender, specialization, phone_number, email, qualification, experience, consultation_fee) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, doctor.getFirstName());
            stmt.setString(2, doctor.getLastName());
            stmt.setString(3, doctor.getGender());
            stmt.setString(4, doctor.getSpecialization());
            stmt.setString(5, doctor.getPhoneNumber());
            stmt.setString(6, doctor.getEmail());
            stmt.setString(7, doctor.getQualification());
            stmt.setString(8, doctor.getExperience());
            stmt.setString(9, doctor.getConsultationFee());
            
            int rowsAffected = stmt.executeUpdate();
            logger.info("Successfully added new doctor: " + doctor.getFirstName() + " " + doctor.getLastName());
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.severe("Error adding doctor: " + e.getMessage());
            throw new RuntimeException("Failed to add doctor", e);
        }
    }

    public boolean deleteDoctor(int id) {
        String sql = "DELETE FROM doctors WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
        
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Successfully deleted doctor with ID: " + id);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.severe("Error deleting doctor with ID " + id + ": " + e.getMessage());
            throw new RuntimeException("Failed to delete doctor", e);
        }
    }
} 