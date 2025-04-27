package com.hmapp.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hmapp.dao.DoctorDAO;
import com.hmapp.model.Doctor;

@WebServlet("/view-doctors")
public class ViewDoctorsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ViewDoctorsServlet.class.getName());
    private DoctorDAO doctorDAO = new DoctorDAO();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            logger.info("Fetching all doctors from database");
            List<Doctor> doctors = doctorDAO.getAllDoctors();
            logger.info("Retrieved " + doctors.size() + " doctors from database");
            
            if (doctors.isEmpty()) {
                logger.info("No doctors found in database");
                request.setAttribute("message", "No doctors found in the database.");
            } else {
                logger.info("Setting doctors in request attribute");
                for (Doctor doctor : doctors) {
                    logger.info("Doctor: " + doctor.getFirstName() + " " + doctor.getLastName() + 
                              " (ID: " + doctor.getId() + ")");
                }
                request.setAttribute("doctors", doctors);
                logger.info("Doctors attribute set in request: " + request.getAttribute("doctors"));
            }
            
            // Set content type and character encoding
            response.setContentType("text/html;charset=UTF-8");
            
            // Forward to JSP
            request.getRequestDispatcher("view-doctors.jsp").forward(request, response);
        } catch (Exception e) {
            logger.severe("Error while fetching doctors: " + e.getMessage());
            request.setAttribute("error", "Error retrieving doctors: " + e.getMessage());
            request.getRequestDispatcher("view-doctors.jsp").forward(request, response);
        }
    }
} 