//package com.accesshr.emsbackend.EmployeeController;
//
//import com.accesshr.emsbackend.Dto.EmployeeDTO;
//import com.accesshr.emsbackend.Dto.LoginDTO;
//import com.accesshr.emsbackend.Service.EmployeeService;
//import com.accesshr.emsbackend.response.LoginResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@CrossOrigin  // Allow cross-origin requests (CORS)
//@RequestMapping("api/v1/employee")  // Base URL for all employee-related endpoints
//public class EmployeeController {
//
//    // Injecting the EmployeeService to handle business logic
//    @Autowired
//    private EmployeeService employeeService;
//
//    /**
//     * Endpoint to save a new employee.
//     *
//     * @param employeeDTO contains employee details to be saved.
//     * @return ResponseEntity with either the new employee ID or an error if the email already exists.
//     */
//    @PostMapping(path = "/save")
//    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
//        // Check if the email already exists
//        if (employeeService.isEmailExists(employeeDTO.getEmail())) {
//            return ResponseEntity.badRequest().body("Email already exists");
//        }
//        // Add the employee and return the new employee ID
//        String id = employeeService.addEmployee(employeeDTO);
//        return ResponseEntity.ok(id);
//    }
//
//    /**
//     * Endpoint to check if an email already exists in the system.
//     *
//     * @param requestBody contains the email to be checked.
//     * @return ResponseEntity with a boolean value indicating whether the email exists.
//     */
//    @PostMapping(path = "/emailExists")
//    public ResponseEntity<?> checkEmailExists(@RequestBody Map<String, String> requestBody) {
//        // Extract the email from the request body
//        String email = requestBody.get("email");
//        // Check if the email exists
//        boolean exists = employeeService.isEmailExists(email);
//        // Return a map with the result
//        return ResponseEntity.ok(Map.of("exists", exists));
//    }
//
//    /**
//     * Endpoint for employee login.
//     *
//     * @param loginDTO contains login credentials (email and password).
//     * @return ResponseEntity with a LoginResponse object, containing login result and any token if applicable.
//     */
//    @PostMapping(path = "/login")
//    public ResponseEntity<?> loginEmployee(@RequestBody LoginDTO loginDTO) {
//        // Authenticate the employee and return the login response
//        LoginResponse loginResponse = employeeService.loginEmployee(loginDTO);
//        return ResponseEntity.ok(loginResponse);
//    }
//}
//
