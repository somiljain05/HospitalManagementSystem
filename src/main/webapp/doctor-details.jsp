<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Doctor Details - Hospital Management System</title>
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
                        <a class="nav-link" href="view-doctors">Back to Doctors</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <!-- Debug Information -->
        <div class="alert alert-info">
            <h4>Debug Information:</h4>
            <p>Doctor attribute exists: ${not empty doctor}</p>
            <p>Doctor ID: ${param.id}</p>
            <p>Error message: ${error}</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                <i class="fas fa-exclamation-circle"></i> ${error}
            </div>
        </c:if>
        
        <c:if test="${empty error and not empty doctor}">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Doctor Details</h4>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <h5 class="mb-3">Personal Information</h5>
                            <table class="table">
                                <tr>
                                    <th>Doctor ID:</th>
                                    <td>${doctor.id}</td>
                                </tr>
                                <tr>
                                    <th>Name:</th>
                                    <td>${doctor.firstName} ${doctor.lastName}</td>
                                </tr>
                                <tr>
                                    <th>Gender:</th>
                                    <td>${doctor.gender}</td>
                                </tr>
                                <tr>
                                    <th>Specialization:</th>
                                    <td>${doctor.specialization}</td>
                                </tr>
                                <tr>
                                    <th>Qualification:</th>
                                    <td>${doctor.qualification}</td>
                                </tr>
                                <tr>
                                    <th>Experience:</th>
                                    <td>${doctor.experience} years</td>
                                </tr>
                                <tr>
                                    <th>Consultation Fees:</th>
                                    <td>$${doctor.consultationFee}</td>
                                </tr>
                            </table>
                        </div>
                        <div class="col-md-6">
                            <h5 class="mb-3">Contact Information</h5>
                            <table class="table">
                                <tr>
                                    <th>Phone Number:</th>
                                    <td>${doctor.phoneNumber}</td>
                                </tr>
                                <tr>
                                    <th>Email:</th>
                                    <td>${doctor.email}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    
                    <div class="mt-4">
                        <a href="edit-doctor.jsp?id=${doctor.id}" class="btn btn-warning">
                            <i class="fas fa-edit"></i> Edit Doctor
                        </a>
                        <a href="view-doctors" class="btn btn-secondary">
                            <i class="fas fa-arrow-left"></i> Back to List
                        </a>
                    </div>
                </div>
            </div>
        </c:if>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 