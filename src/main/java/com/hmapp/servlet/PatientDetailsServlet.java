package com.hmapp.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hmapp.dao.PatientDAO;
import com.hmapp.model.Patient;

@WebServlet("/patient-details")
public class PatientDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(PatientDetailsServlet.class.getName());
    private PatientDAO patientDAO = new PatientDAO();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            logger.info("Received request for patient details with ID: " + idParam);
            
            if (idParam == null || idParam.isEmpty()) {
                logger.warning("No patient ID provided in request");
                response.sendRedirect("view-patients");
                return;
            }
            
            int patientId = Integer.parseInt(idParam);
            logger.info("Fetching patient with ID: " + patientId);
            Patient patient = patientDAO.getPatientById(patientId);
            
            if (patient == null) {
                logger.warning("Patient not found with ID: " + patientId);
                request.setAttribute("error", "Patient not found");
                request.getRequestDispatcher("view-patients").forward(request, response);
                return;
            }
            
            logger.info("Patient found: " + patient.getFirstName() + " " + patient.getLastName());
            request.setAttribute("patient", patient);
            
            // Set content type and character encoding
            response.setContentType("text/html;charset=UTF-8");
            
            // Forward to JSP
            request.getRequestDispatcher("patient-details.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            logger.severe("Invalid patient ID format: " + e.getMessage());
            request.setAttribute("error", "Invalid patient ID");
            request.getRequestDispatcher("view-patients").forward(request, response);
        } catch (Exception e) {
            logger.severe("Error retrieving patient details: " + e.getMessage());
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("view-patients").forward(request, response);
        }
    }
} 