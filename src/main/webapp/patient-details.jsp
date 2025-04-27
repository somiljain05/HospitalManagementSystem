<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patient Details - Hospital Management System</title>
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
                        <a class="nav-link" href="view-patients">Back to Patients</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <!-- Debug Information -->
        <div class="alert alert-info">
            <h4>Debug Information:</h4>
            <p>Patient attribute exists: ${not empty patient}</p>
            <p>Patient ID: ${param.id}</p>
            <p>Error message: ${error}</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </c:if>
        
        <c:if test="${empty error and not empty patient}">
            <div class="card">
                <div class="card-header bg-primary text-white">
                    <h4 class="mb-0">Patient Details</h4>
                </div>
                <div class="card-body">
                    <div class="row">
                        <div class="col-md-6">
                            <h5 class="mb-3">Personal Information</h5>
                            <table class="table">
                                <tr>
                                    <th>Patient ID:</th>
                                    <td>${patient.id}</td>
                                </tr>
                                <tr>
                                    <th>Name:</th>
                                    <td>${patient.firstName} ${patient.lastName}</td>
                                </tr>
                                <tr>
                                    <th>Date of Birth:</th>
                                    <td>${patient.dateOfBirth}</td>
                                </tr>
                                <tr>
                                    <th>Gender:</th>
                                    <td>${patient.gender}</td>
                                </tr>
                                <tr>
                                    <th>Blood Group:</th>
                                    <td>${patient.bloodGroup}</td>
                                </tr>
                            </table>
                        </div>
                        <div class="col-md-6">
                            <h5 class="mb-3">Contact Information</h5>
                            <table class="table">
                                <tr>
                                    <th>Phone Number:</th>
                                    <td>${patient.phoneNumber}</td>
                                </tr>
                                <tr>
                                    <th>Email:</th>
                                    <td>${patient.email}</td>
                                </tr>
                                <tr>
                                    <th>Address:</th>
                                    <td>${patient.address}</td>
                                </tr>
                            </table>
                        </div>
                    </div>
                    
                    <div class="mt-4">
                        <a href="edit-patient.jsp?id=${patient.id}" class="btn btn-warning">
                            <i class="fas fa-edit"></i> Edit Patient
                        </a>
                        <a href="view-patients" class="btn btn-secondary">
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