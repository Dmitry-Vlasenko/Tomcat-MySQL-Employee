package com.dvlasenko.app.repository.impl;

import com.dvlasenko.app.database.DBConn;
import com.dvlasenko.app.entity.Employee;
import com.dvlasenko.app.repository.AppRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeRepository implements AppRepository<Employee> {

    private static final Logger LOGGER =
            Logger.getLogger(EmployeeRepository.class.getName());

    public void create(Employee employee) {
        String sql = "INSERT INTO employee (firstName, lastName, phoneNumber, positionName) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConn.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getPhoneNumber());
            ps.setString(4, employee.getPositionName());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public List<Employee> read() {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT * FROM employee";
        try (Connection conn = DBConn.connect();
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new Employee(
                                rs.getLong("id"),
                                rs.getString("firstName"),
                                rs.getString("lastName"),
                                rs.getString("phoneNumber"),
                                rs.getString("positionName")
                        )
                );
            }
            return list;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Employee readById(Long id) {
        Employee employee = null;
        String sql = "SELECT * FROM employee WHERE id = ?";
        try (Connection conn = DBConn.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                employee = new Employee(id, rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("phoneNumber"),
                        rs.getString("positionName")
                );
            }
            ps.executeQuery();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        return employee;
    }

    public void update(Employee employee) {
        String sql = "UPDATE employee SET firstName = ?, lastName = ?, phoneNumber = ?, positionName = ? WHERE id = ?";
        try (Connection conn = DBConn.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, employee.getFirstName());
            ps.setString(2, employee.getLastName());
            ps.setString(3, employee.getPhoneNumber());
            ps.setString(4, employee.getPositionName());
            ps.setLong(5, employee.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM employee where id = ?";
        try (Connection conn = DBConn.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }
}
