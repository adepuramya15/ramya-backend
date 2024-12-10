//package com.accesshr.emsbackend.Service.impl;
//
//import com.accesshr.emsbackend.Dto.EmployeeDTO;
//import com.accesshr.emsbackend.Dto.LoginDTO;
//import com.accesshr.emsbackend.Entity.Employee;
//import com.accesshr.emsbackend.Repo.EmployeeRepo;
//import com.accesshr.emsbackend.Service.EmployeeService;
//import com.accesshr.emsbackend.response.LoginResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmployeeIMPL implements EmployeeService {
//
//    @Autowired
//    private EmployeeRepo employeeRepo;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    /**
//     * Add a new employee to the system.
//     *
//     * @param employeeDTO contains employee details including plain text password.
//     * @return The employee's name after saving.
//     */
//    @Override
//    public String addEmployee(EmployeeDTO employeeDTO) {
//        // Encode the password before saving
//        String encodedPassword = passwordEncoder.encode(employeeDTO.getPassword());
//
//        // Create an Employee entity from the DTO
//        Employee employee = ne
//        w Employee(
//                employeeDTO.getEmployeeid(),
//                employeeDTO.getEmployeename(),
//                employeeDTO.getEmail(),
//                employeeDTO.getCompanyname(),
//                encodedPassword
//        );
//
//        // Save the employee to the repository
//        employeeRepo.save(employee);
//
//        // Return the employee's name
//        return employee.getEmployeename();
//    }
//
//    /**
//     * Check if an email already exists in the system.
//     *
//     * @param email The email to check.
//     * @return True if the email exists, otherwise false.
//     */
//    @Override
//    public boolean isEmailExists(String email) {
//        // Use repository method to find employee by email
//        return employeeRepo.findByEmail(email) != null;
//    }
//
//    /**
//     * Authenticate an employee's login attempt.
//     *
//     * @param loginDTO contains the email and password provided by the user.
//     * @return A LoginResponse with the result of the login attempt.
//     */
//    @Override
//    public LoginResponse loginEmployee(LoginDTO loginDTO) {
//        // Find employee by email
//        Employee employee = employeeRepo.findByEmail(loginDTO.getEmail());
//
//        if (employee != null) {
//            // Check if the provided password matches the encoded password
//            boolean isPasswordValid = passwordEncoder.matches(loginDTO.getPassword(), employee.getPassword());
//
//            if (isPasswordValid) {
//                // Return success response with the employee's role
//                return new LoginResponse("Login Success", true, employee.getRole());
//            } else {
//                // Return failure response if password does not match
//                return new LoginResponse("Password does not match", false, null); // No role on failure
//            }
//        } else {
//            // Return failure response if email does not exist
//            return new LoginResponse("Email does not exist", false, null); // No role on failure
//        }
//    }
//}