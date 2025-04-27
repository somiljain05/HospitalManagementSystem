<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Doctors - Hospital Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
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
            <h2>Doctors</h2>
            <div>
                <a href="register-doctor.jsp" class="btn btn-primary">Add New Doctor</a>
                <form action="${pageContext.request.contextPath}/reset-database" method="post" class="d-inline ms-2">
                    <input type="hidden" name="table" value="doctors">
                    <button type="submit" class="btn btn-danger" 
                            onclick="return confirm('Are you sure you want to reset the doctors table? This will delete ALL doctors and their appointments.')">
                        Reset Doctors Database
                    </button>
                </form>
            </div>
        </div>
        
        <!-- Debug Information -->
        <div class="alert alert-info">
            <h4>Debug Information:</h4>
            <p>Doctors attribute exists: ${not empty doctors}</p>
            <p>Doctors list size: ${doctors.size()}</p>
            <p>Error message: ${error}</p>
            <p>Info message: ${message}</p>
        </div>
        
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
        
        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Gender</th>
                                <th>Specialization</th>
                                <th>Phone</th>
                                <th>Email</th>
                                <th>Qualification</th>
                                <th>Experience</th>
                                <th>Consultation Fee</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty doctors}">
                                    <c:forEach var="doctor" items="${doctors}">
                                        <tr>
                                            <td>${doctor.id}</td>
                                            <td>${doctor.firstName} ${doctor.lastName}</td>
                                            <td>${doctor.gender}</td>
                                            <td>${doctor.specialization}</td>
                                            <td>${doctor.phoneNumber}</td>
                                            <td>${doctor.email}</td>
                                            <td>${doctor.qualification}</td>
                                            <td>${doctor.experience}</td>
                                            <td>${doctor.consultationFee}</td>
                                            <td>
                                                <a href="doctor-details?id=${doctor.id}" class="btn btn-sm btn-info">
                                                    <i class="fas fa-eye"></i> View
                                                </a>
                                                <a href="edit-doctor.jsp?id=${doctor.id}" class="btn btn-sm btn-warning">
                                                    <i class="fas fa-edit"></i> Edit
                                                </a>
                                                <a href="delete-doctor?id=${doctor.id}" class="btn btn-sm btn-danger" 
                                                   onclick="return confirm('Are you sure you want to delete this doctor?')">
                                                    <i class="fas fa-trash"></i> Delete
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="10" class="text-center">No doctors found</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 