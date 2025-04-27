package com.hmapp.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hmapp.dao.PatientDAO;

@WebServlet("/delete-patient")
public class DeletePatientServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(DeletePatientServlet.class.getName());
    private PatientDAO patientDAO = new PatientDAO();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            logger.info("Received request to delete patient with ID: " + idParam);
            
            if (idParam == null || idParam.isEmpty()) {
                logger.warning("No patient ID provided in delete request");
                request.setAttribute("error", "No patient ID provided");
                request.getRequestDispatcher("view-patients").forward(request, response);
                return;
            }
            
            int patientId = Integer.parseInt(idParam);
            logger.info("Attempting to delete patient with ID: " + patientId);
            
            boolean success = patientDAO.deletePatient(patientId);
            
            if (success) {
                logger.info("Successfully deleted patient with ID: " + patientId);
                request.setAttribute("message", "Patient deleted successfully");
            } else {
                logger.warning("Failed to delete patient with ID: " + patientId);
                request.setAttribute("error", "Failed to delete patient");
            }
            
            // Set content type and character encoding
            response.setContentType("text/html;charset=UTF-8");
            
            // Redirect back to view patients page
            response.sendRedirect("view-patients");
            
        } catch (NumberFormatException e) {
            logger.severe("Invalid patient ID format: " + e.getMessage());
            request.setAttribute("error", "Invalid patient ID");
            request.getRequestDispatcher("view-patients").forward(request, response);
        } catch (Exception e) {
            logger.severe("Error deleting patient: " + e.getMessage());
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("view-patients").forward(request, response);
        }
    }
} 