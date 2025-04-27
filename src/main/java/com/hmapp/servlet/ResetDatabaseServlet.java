package com.hmapp.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hmapp.util.DatabaseUtil;

@WebServlet(name = "ResetDatabaseServlet", urlPatterns = {"/reset-database"})
public class ResetDatabaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(ResetDatabaseServlet.class.getName());
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String table = request.getParameter("table");
        String redirectUrl = "";
        
        logger.info("Attempting to reset table: " + table);
        
        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            
            switch (table) {
                case "patients":
                    stmt.executeUpdate("DELETE FROM appointments WHERE patient_id IS NOT NULL");
                    stmt.executeUpdate("DELETE FROM patients");
                    stmt.executeUpdate("ALTER TABLE patients AUTO_INCREMENT = 1");
                    redirectUrl = "view-patients";
                    break;
                    
                case "doctors":
                    stmt.executeUpdate("DELETE FROM appointments WHERE doctor_id IS NOT NULL");
                    stmt.executeUpdate("DELETE FROM doctors");
                    stmt.executeUpdate("ALTER TABLE doctors AUTO_INCREMENT = 1");
                    redirectUrl = "view-doctors";
                    break;
                    
                case "appointments":
                    stmt.executeUpdate("DELETE FROM appointments");
                    stmt.executeUpdate("ALTER TABLE appointments AUTO_INCREMENT = 1");
                    redirectUrl = "view-appointments";
                    break;
                    
                default:
                    throw new IllegalArgumentException("Invalid table name: " + table);
            }
            
            logger.info("Successfully reset table: " + table);
            request.getSession().setAttribute("success", "Successfully reset " + table + " table.");
            
        } catch (Exception e) {
            logger.severe("Error resetting table " + table + ": " + e.getMessage());
            request.getSession().setAttribute("error", "Failed to reset " + table + ": " + e.getMessage());
        }
        
        response.sendRedirect(redirectUrl);
    }
} 