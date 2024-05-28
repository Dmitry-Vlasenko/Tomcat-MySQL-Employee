package com.dvlasenko.app.controller;

import com.dvlasenko.app.entity.Employee;
import com.dvlasenko.app.repository.impl.EmployeeRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeController extends HttpServlet {
    private static final Logger LOGGER =
            Logger.getLogger(EmployeeController.class.getName());

    private final EmployeeRepository repository = new EmployeeRepository();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        try {
            switch (action) {
                case "/new" -> showNewForm(request, response);
                case "/insert" -> create(request, response);
                case "/delete" -> delete(request, response);
                case "/edit" -> showEditForm(request, response);
                case "/update" -> update(request, response);
                default -> read(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void create(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String positionName = request.getParameter("positionName");
        LOGGER.log(Level.INFO, "lastName" + lastName);
        Employee newEmployee = new Employee(firstName, lastName, phoneNumber, positionName);
        repository.create(newEmployee);
        response.sendRedirect("list");
    }

    private void read(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Employee> listEmployee = repository.read();
        LOGGER.log(Level.INFO, listEmployee.toString());
        request.setAttribute("listEmployee", listEmployee);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("pages/employee_list.jsp");
        dispatcher.forward(request, response);
    }

    private void update(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String phoneNumber = request.getParameter("phoneNumber");
        String positionName = request.getParameter("positionName");
        Employee employee = new Employee(id, firstName, lastName, phoneNumber, positionName);
        repository.update(employee);
        response.sendRedirect("list");
    }

    private void delete(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        repository.delete(id);
        response.sendRedirect("list");
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("pages/employee_form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Employee existingEmployee = repository.readById(id);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("pages/employee_form.jsp");
        request.setAttribute("employee", existingEmployee);
        dispatcher.forward(request, response);
    }
}
