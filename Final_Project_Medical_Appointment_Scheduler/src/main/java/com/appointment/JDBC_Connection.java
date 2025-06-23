package com.appointment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JDBC_Connection extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        PrintWriter pw = response.getWriter();
        response.setContentType("text/html");

        String FullName = request.getParameter("fullName");
        String PhoneNumber = request.getParameter("phoneNumber");
        String Email = request.getParameter("email");
        String DoctorId = request.getParameter("doctorId");
        String DoctorName = request.getParameter("doctorName");
        String AppointmentDate = request.getParameter("appointmentDate");
        String AppointmentTime = request.getParameter("appointmentTime");
        String Symptoms = request.getParameter("symptoms");
        System.out.println(FullName + " " + PhoneNumber + " " + Email + " " + DoctorId + " " + DoctorName + " " + Symptoms);

       
        if (FullName == null || Email == null || DoctorName == null || Symptoms == null || DoctorId == null || AppointmentDate == null || PhoneNumber == null) {
            pw.println("Booking failed. Please enter all the details correctly.");

            
            RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.html");
            dispatcher.include(request, response);
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

                Connection con = DriverManager.getConnection("jdbc:mysql://@localhost:3306/appointment", "root", "root");
                PreparedStatement pst = con.prepareStatement("insert into appointment_details values(?,?,?,?,?,?,?,?)");
                pst.setString(1, FullName);
                pst.setString(2, Email);
                pst.setString(3, DoctorName);
                pst.setString(4, Symptoms);
                pst.setString(5, DoctorId);
                pst.setString(6, AppointmentDate);
                pst.setString(7, AppointmentTime);
                pst.setString(8, PhoneNumber);
                pst.executeUpdate();
                pst.close();
                pw.println("Appointment booking is successful");
                
                	 RequestDispatcher dispatcher = request.getRequestDispatcher("confirmation.jsp");
                     dispatcher.forward(request, response);
                

                // Forward the request to a confirmation page
               

            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
}
