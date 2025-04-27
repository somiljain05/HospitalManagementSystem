package com.hmapp.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hmapp.dao.AppointmentDAO;
import com.hmapp.model.Appointment;

@WebServlet("/view-appointments")
public class ViewAppointmentsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ViewAppointmentsServlet.class.getName());
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            logger.info("Fetching all appointments from database");
            List<Appointment> appointments = appointmentDAO.getAllAppointments();
            logger.info("Retrieved " + appointments.size() + " appointments from database");
            
            if (appointments.isEmpty()) {
                logger.info("No appointments found in database");
                request.setAttribute("message", "No appointments found in the database.");
            } else {
                logger.info("Setting appointments in request attribute");
                request.setAttribute("appointments", appointments);
            }
            
            // Set content type and character encoding
            response.setContentType("text/html;charset=UTF-8");
            
            // Forward to JSP
            request.getRequestDispatcher("view-appointments.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.severe("Error while fetching appointments: " + e.getMessage());
            request.setAttribute("error", "Error retrieving appointments: " + e.getMessage());
            request.getRequestDispatcher("view-appointments.jsp").forward(request, response);
        }
    }
} 