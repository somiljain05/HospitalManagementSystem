package com.hmapp.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hmapp.dao.DoctorDAO;
import com.hmapp.model.Doctor;

@WebServlet("/doctor-details")
public class DoctorDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(DoctorDetailsServlet.class.getName());
    private DoctorDAO doctorDAO = new DoctorDAO();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String idParam = request.getParameter("id");
            logger.info("Received request for doctor details with ID: " + idParam);
            
            if (idParam == null || idParam.isEmpty()) {
                logger.warning("No doctor ID provided in request");
                response.sendRedirect("view-doctors");
                return;
            }
            
            int doctorId = Integer.parseInt(idParam);
            logger.info("Fetching doctor with ID: " + doctorId);
            Doctor doctor = doctorDAO.getDoctorById(doctorId);
            
            if (doctor == null) {
                logger.warning("Doctor not found with ID: " + doctorId);
                request.setAttribute("error", "Doctor not found");
                request.getRequestDispatcher("view-doctors").forward(request, response);
                return;
            }
            
            logger.info("Doctor found: " + doctor.getFirstName() + " " + doctor.getLastName());
            request.setAttribute("doctor", doctor);
            
            // Set content type and character encoding
            response.setContentType("text/html;charset=UTF-8");
            
            // Forward to JSP
            request.getRequestDispatcher("doctor-details.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            logger.severe("Invalid doctor ID format: " + e.getMessage());
            request.setAttribute("error", "Invalid doctor ID");
            request.getRequestDispatcher("view-doctors").forward(request, response);
        } catch (Exception e) {
            logger.severe("Error retrieving doctor details: " + e.getMessage());
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("view-doctors").forward(request, response);
        }
    }
} 