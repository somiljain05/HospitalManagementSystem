package com.hmapp.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hmapp.dao.DoctorDAO;
import com.hmapp.model.Doctor;

@WebServlet("/register-doctor")
public class DoctorRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DoctorDAO doctorDAO = new DoctorDAO();
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get form parameters
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String gender = request.getParameter("gender");
            String specialization = request.getParameter("specialization");
            String phoneNumber = request.getParameter("phoneNumber");
            String email = request.getParameter("email");
            String qualification = request.getParameter("qualification");
            String experience = request.getParameter("experience");
            String consultationFee = request.getParameter("consultationFee");
                      
            // Create doctor object
            Doctor doctor = new Doctor(firstName, lastName, gender, specialization, 
                                    phoneNumber, email, qualification, experience, 
                                    consultationFee);
            
            // Save doctor
            boolean success = doctorDAO.addDoctor(doctor);
            
            if (success) {
                // Get the last inserted ID
                List<Doctor> doctors = doctorDAO.getAllDoctors();
                int doctorId = doctors.get(doctors.size() - 1).getId();
                response.sendRedirect("doctor-details?id=" + doctorId);
            } else {
                request.setAttribute("error", "Failed to register doctor");
                request.getRequestDispatcher("register-doctor.jsp").forward(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
            request.getRequestDispatcher("register-doctor.jsp").forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("register-doctor.jsp").forward(request, response);
    }
} 