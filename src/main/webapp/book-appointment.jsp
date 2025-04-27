<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hmapp.dao.DoctorDAO"%>
<%@ page import="com.hmapp.dao.PatientDAO"%>
<%@ page import="com.hmapp.model.Doctor"%>
<%@ page import="com.hmapp.model.Patient"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Book Appointment - Hospital Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h2>Book New Appointment</h2>
        
        <%-- Display error message if any --%>
        <% if (request.getAttribute("error") != null) { %>
            <div class="alert alert-danger">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        
        <form action="book-appointment" method="post" class="mt-4">
            <div class="mb-3">
                <label for="patientId" class="form-label">Select Patient</label>
                <select class="form-select" name="patientId" required>
                    <option value="">Choose patient...</option>
                    <%
                        PatientDAO patientDAO = new PatientDAO();
                        List<Patient> patients = patientDAO.getAllPatients();
                        for (Patient patient : patients) {
                    %>
                        <option value="<%= patient.getId() %>">
                            <%= patient.getFirstName() + " " + patient.getLastName() %>
                        </option>
                    <% } %>
                </select>
            </div>
            
            <div class="mb-3">
                <label for="doctorId" class="form-label">Select Doctor</label>
                <select class="form-select" name="doctorId" required>
                    <option value="">Choose doctor...</option>
                    <%
                        DoctorDAO doctorDAO = new DoctorDAO();
                        List<Doctor> doctors = doctorDAO.getAllDoctors();
                        for (Doctor doctor : doctors) {
                    %>
                        <option value="<%= doctor.getId() %>">
                            Dr. <%= doctor.getFirstName() + " " + doctor.getLastName() %> 
                            (<%= doctor.getSpecialization() %>)
                        </option>
                    <% } %>
                </select>
            </div>
            
            <div class="mb-3">
                <label for="appointmentDate" class="form-label">Appointment Date</label>
                <input type="date" class="form-control" name="appointmentDate" required
                       min="<%= java.time.LocalDate.now() %>">
            </div>
            
            <div class="mb-3">
                <label for="timeSlot" class="form-label">Time Slot</label>
                <select class="form-select" name="timeSlot" required>
                    <option value="">Choose time slot...</option>
                    <option value="09:00 AM">09:00 AM</option>
                    <option value="09:30 AM">09:30 AM</option>
                    <option value="10:00 AM">10:00 AM</option>
                    <option value="10:30 AM">10:30 AM</option>
                    <option value="11:00 AM">11:00 AM</option>
                    <option value="11:30 AM">11:30 AM</option>
                    <option value="12:00 PM">12:00 PM</option>
                    <option value="02:00 PM">02:00 PM</option>
                    <option value="02:30 PM">02:30 PM</option>
                    <option value="03:00 PM">03:00 PM</option>
                    <option value="03:30 PM">03:30 PM</option>
                    <option value="04:00 PM">04:00 PM</option>
                    <option value="04:30 PM">04:30 PM</option>
                    <option value="05:00 PM">05:00 PM</option>
                </select>
            </div>
            
            <div class="mb-3">
                <label for="symptoms" class="form-label">Symptoms</label>
                <textarea class="form-control" name="symptoms" rows="3" 
                          placeholder="Describe the symptoms..."></textarea>
            </div>
            
            <div class="mb-3">
                <button type="submit" class="btn btn-primary">Book Appointment</button>
                <a href="view-appointments" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 