<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hmapp.model.Appointment"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Appointments - Hospital Management System</title>
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
                        <a class="nav-link" href="index.jsp">Home</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h2>Appointments</h2>
            <div>
                <a href="book-appointment.jsp" class="btn btn-primary">Book New Appointment</a>
                <form action="${pageContext.request.contextPath}/reset-database" method="post" class="d-inline ms-2">
                    <input type="hidden" name="table" value="appointments">
                    <button type="submit" class="btn btn-danger" 
                            onclick="return confirm('Are you sure you want to reset the appointments table? This will delete ALL appointments.')">
                        Reset Appointments Database
                    </button>
                </form>
            </div>
        </div>

        <%-- Display error message if any --%>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <%-- Display info message if any --%>
        <% if (request.getAttribute("message") != null) { %>
            <div class="alert alert-info">
                <%= request.getAttribute("message") %>
            </div>
        <% } %>
        
        <% 
            List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
            if (appointments != null && !appointments.isEmpty()) { 
        %>
            <table class="table table-striped">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Patient Name</th>
                        <th>Doctor Name</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        for (Appointment appointment : appointments) {
                    %>
                        <tr>
                            <td><%= appointment.getId() %></td>
                            <td>
                                <%= appointment.getPatient().getFirstName() + " " + 
                                    appointment.getPatient().getLastName() %>
                            </td>
                            <td>
                                Dr. <%= appointment.getDoctor().getFirstName() + " " + 
                                       appointment.getDoctor().getLastName() %>
                            </td>
                            <td><%= dateFormat.format(appointment.getAppointmentDate()) %></td>
                            <td><%= appointment.getTimeSlot() %></td>
                            <td>
                                <span class="badge bg-<%= appointment.getStatus().equals("Scheduled") ? 
                                                        "primary" : "success" %>">
                                    <%= appointment.getStatus() %>
                                </span>
                            </td>
                            <td>
                                <a href="appointment-details.jsp?id=<%= appointment.getId() %>" 
                                   class="btn btn-sm btn-info">View</a>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } %>

        <%-- Add this for displaying success/error messages --%>
        <% if (session.getAttribute("success") != null) { %>
            <div class="alert alert-success">
                <%= session.getAttribute("success") %>
                <% session.removeAttribute("success"); %>
            </div>
        <% } %>
        <% if (session.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <%= session.getAttribute("error") %>
                <% session.removeAttribute("error"); %>
            </div>
        <% } %>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 