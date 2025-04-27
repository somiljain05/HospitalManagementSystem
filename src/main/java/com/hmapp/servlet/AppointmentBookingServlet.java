package com.hmapp.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hmapp.dao.AppointmentDAO;
import com.hmapp.model.Appointment;

@WebServlet("/book-appointment")
public class AppointmentBookingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AppointmentDAO appointmentDAO = new AppointmentDAO();
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get form parameters
            int patientId = Integer.parseInt(request.getParameter("patientId"));
            int doctorId = Integer.parseInt(request.getParameter("doctorId"));
            String appointmentDateStr = request.getParameter("appointmentDate");
            String timeSlot = request.getParameter("timeSlot");
            String symptoms = request.getParameter("symptoms");
            
            // Parse date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date appointmentDate = sdf.parse(appointmentDateStr);
            
            // Create appointment object
            Appointment appointment = new Appointment(patientId, doctorId, appointmentDate, 
                                                   timeSlot, symptoms);
            
            // Save appointment
            int appointmentId = appointmentDAO.addAppointment(appointment);
            
            if (appointmentId > 0) {
                response.sendRedirect("appointment-details.jsp?id=" + appointmentId);
            } else {
                request.setAttribute("error", "Failed to book appointment");
                request.getRequestDispatcher("book-appointment.jsp").forward(request, response);
            }
            
        } catch (ParseException e) {
            request.setAttribute("error", "Invalid date format");
            request.getRequestDispatcher("book-appointment.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid patient or doctor ID");
            request.getRequestDispatcher("book-appointment.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("book-appointment.jsp").forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("book-appointment.jsp").forward(request, response);
    }
} 