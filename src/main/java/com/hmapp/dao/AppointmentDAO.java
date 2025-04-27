package com.hmapp.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.hmapp.model.Appointment;
import com.hmapp.model.Doctor;
import com.hmapp.model.Patient;
import com.hmapp.util.DatabaseUtil;

public class AppointmentDAO {
    
    private PatientDAO patientDAO = new PatientDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();
    
    public int addAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (patient_id, doctor_id, appointment_date, " +
                    "time_slot, status, symptoms, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            pstmt.setString(4, appointment.getTimeSlot());
            pstmt.setString(5, appointment.getStatus());
            pstmt.setString(6, appointment.getSymptoms());
            pstmt.setTimestamp(7, new java.sql.Timestamp(appointment.getCreatedAt().getTime()));
            
            pstmt.executeUpdate();
            
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        }
        return -1;
    }
    
    public Appointment getAppointment(int id) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extractAppointmentFromResultSet(rs);
                }
            }
        }
        return null;
    }
    
    public List<Appointment> getAllAppointments() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments ORDER BY appointment_date, time_slot";
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                appointments.add(extractAppointmentFromResultSet(rs));
            }
        }
        return appointments;
    }
    
    public List<Appointment> getAppointmentsByPatient(int patientId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE patient_id = ? ORDER BY appointment_date, time_slot";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, patientId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(extractAppointmentFromResultSet(rs));
                }
            }
        }
        return appointments;
    }
    
    public List<Appointment> getAppointmentsByDoctor(int doctorId) throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctor_id = ? ORDER BY appointment_date, time_slot";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, doctorId);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(extractAppointmentFromResultSet(rs));
                }
            }
        }
        return appointments;
    }
    
    public boolean updateAppointment(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointments SET patient_id = ?, doctor_id = ?, " +
                    "appointment_date = ?, time_slot = ?, status = ?, symptoms = ? " +
                    "WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, appointment.getPatientId());
            pstmt.setInt(2, appointment.getDoctorId());
            pstmt.setDate(3, new java.sql.Date(appointment.getAppointmentDate().getTime()));
            pstmt.setString(4, appointment.getTimeSlot());
            pstmt.setString(5, appointment.getStatus());
            pstmt.setString(6, appointment.getSymptoms());
            pstmt.setInt(7, appointment.getId());
            
            return pstmt.executeUpdate() > 0;
        }
    }
    
    public boolean deleteAppointment(int id) throws SQLException {
        String sql = "DELETE FROM appointments WHERE id = ?";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    private Appointment extractAppointmentFromResultSet(ResultSet rs) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setId(rs.getInt("id"));
        appointment.setPatientId(rs.getInt("patient_id"));
        appointment.setDoctorId(rs.getInt("doctor_id"));
        appointment.setAppointmentDate(rs.getDate("appointment_date"));
        appointment.setTimeSlot(rs.getString("time_slot"));
        appointment.setStatus(rs.getString("status"));
        appointment.setSymptoms(rs.getString("symptoms"));
        appointment.setCreatedAt(rs.getTimestamp("created_at"));
        
        // Load associated patient and doctor
        Patient patient = patientDAO.getPatientById(appointment.getPatientId());
        Doctor doctor = doctorDAO.getDoctorById(appointment.getDoctorId());
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        
        return appointment;
    }
} 