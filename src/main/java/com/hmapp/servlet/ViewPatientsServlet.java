package com.hmapp.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hmapp.dao.PatientDAO;
import com.hmapp.model.Patient;

@WebServlet("/view-patients")
public class ViewPatientsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ViewPatientsServlet.class.getName());
    private PatientDAO patientDAO = new PatientDAO();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            logger.info("Fetching all patients from database");
            List<Patient> patients = patientDAO.getAllPatients();
            logger.info("Retrieved " + patients.size() + " patients from database");
            
            if (patients.isEmpty()) {
                logger.info("No patients found in database");
                request.setAttribute("message", "No patients found in the database.");
            } else {
                logger.info("Setting patients in request attribute");
                for (Patient patient : patients) {
                    logger.info("Patient: " + patient.getFirstName() + " " + patient.getLastName() + 
                              " (ID: " + patient.getId() + ")");
                }
                request.setAttribute("patients", patients);
                logger.info("Patients attribute set in request: " + request.getAttribute("patients"));
            }
            
            // Set content type and character encoding
            response.setContentType("text/html;charset=UTF-8");
            
            // Forward to JSP
            request.getRequestDispatcher("view-patients.jsp").forward(request, response);
        } catch (Exception e) {
            logger.severe("Error while fetching patients: " + e.getMessage());
            request.setAttribute("error", "Error retrieving patients: " + e.getMessage());
            request.getRequestDispatcher("view-patients.jsp").forward(request, response);
        }
    }
} 