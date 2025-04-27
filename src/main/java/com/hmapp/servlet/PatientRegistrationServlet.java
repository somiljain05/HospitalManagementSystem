package com.hmapp.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hmapp.dao.PatientDAO;
import com.hmapp.model.Patient;

@WebServlet("/register-patient")
public class PatientRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private PatientDAO patientDAO = new PatientDAO();
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get form parameters
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String dateOfBirthStr = request.getParameter("dateOfBirth");
            String gender = request.getParameter("gender");
            String phoneNumber = request.getParameter("phoneNumber");
            String email = request.getParameter("email");
            String address = request.getParameter("address");
            String bloodGroup = request.getParameter("bloodGroup");
            
            // Parse date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = sdf.parse(dateOfBirthStr);
            
            // Create patient object
            Patient patient = new Patient(firstName, lastName, dateOfBirth, gender, 
                                       phoneNumber, email, address, bloodGroup);
            
            // Save patient
            boolean success = patientDAO.addPatient(patient);
            
            if (success) {
                // Get the last inserted ID
                List<Patient> patients = patientDAO.getAllPatients();
                int patientId = patients.get(patients.size() - 1).getId();
                response.sendRedirect("patient-details?id=" + patientId);
            } else {
                request.setAttribute("error", "Failed to register patient");
                request.getRequestDispatcher("register-patient.jsp").forward(request, response);
            }
            
        } catch (ParseException e) {
            request.setAttribute("error", "Invalid date format");
            request.getRequestDispatcher("register-patient.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("register-patient.jsp").forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("register-patient.jsp").forward(request, response);
    }
} 