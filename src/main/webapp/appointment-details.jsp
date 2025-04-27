<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hmapp.dao.AppointmentDAO"%>
<%@ page import="com.hmapp.model.Appointment"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Appointment Details - Hospital Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="index.jsp">Hospital Management System</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="view-appointments">Back To Appointment</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <%
            int appointmentId = Integer.parseInt(request.getParameter("id"));
            AppointmentDAO appointmentDAO = new AppointmentDAO();
            Appointment appointment = appointmentDAO.getAppointment(appointmentId);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            
            if (appointment != null) {
        %>
            <h2>Appointment Details</h2>
            
            <div class="card mt-4">
                <div class="card-body">
                    <h5 class="card-title">Appointment #<%= appointment.getId() %></h5>
                    
                    <div class="row mt-4">
                        <div class="col-md-6">
                            <h6>Patient Information</h6>
                            <p>
                                <strong>Name:</strong> 
                                <%= appointment.getPatient().getFirstName() + " " + 
                                    appointment.getPatient().getLastName() %>
                            </p>
                            <p>
                                <strong>Phone:</strong> 
                                <%= appointment.getPatient().getPhoneNumber() %>
                            </p>
                            <p>
                                <strong>Email:</strong> 
                                <%= appointment.getPatient().getEmail() %>
                            </p>
                        </div>
                        
                        <div class="col-md-6">
                            <h6>Doctor Information</h6>
                            <p>
                                <strong>Name:</strong> 
                                Dr. <%= appointment.getDoctor().getFirstName() + " " + 
                                       appointment.getDoctor().getLastName() %>
                            </p>
                            <p>
                                <strong>Specialization:</strong> 
                                <%= appointment.getDoctor().getSpecialization() %>
                            </p>
                            <p>
                                <strong>Phone:</strong> 
                                <%= appointment.getDoctor().getPhoneNumber() %>
                            </p>
                        </div>
                    </div>
                    
                    <div class="row mt-4">
                        <div class="col-md-12">
                            <h6>Appointment Information</h6>
                            <p>
                                <strong>Date:</strong> 
                                <%= dateFormat.format(appointment.getAppointmentDate()) %>
                            </p>
                            <p>
                                <strong>Time:</strong> 
                                <%= appointment.getTimeSlot() %>
                            </p>
                            <p>
                                <strong>Status:</strong> 
                                <span class="badge bg-<%= appointment.getStatus().equals("Scheduled") ? 
                                                        "primary" : "success" %>">
                                    <%= appointment.getStatus() %>
                                </span>
                            </p>
                            <p>
                                <strong>Symptoms:</strong><br>
                                <%= appointment.getSymptoms() != null ? 
                                    appointment.getSymptoms() : "No symptoms recorded" %>
                            </p>
                        </div>
                    </div>
                    
                    <div class="mt-4">
                        <a href="view-appointments" class="btn btn-primary">Back to Appointments</a>
                    </div>
                </div>
            </div>
        <% } else { %>
            <div class="alert alert-danger">
                Appointment not found.
            </div>
            <a href="view-appointments" class="btn btn-primary">Back to Appointments</a>
        <% } %>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 