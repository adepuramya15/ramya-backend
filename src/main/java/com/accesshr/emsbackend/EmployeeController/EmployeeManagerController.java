

package com.accesshr.emsbackend.EmployeeController;

import com.accesshr.emsbackend.Dto.EmployeeManagerDTO;
import com.accesshr.emsbackend.Dto.LoginDTO;
import com.accesshr.emsbackend.Service.EmployeeManagerService;
import com.accesshr.emsbackend.response.LoginResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/employeeManager")
@CrossOrigin(origins = "http://localhost:3000") // Adjust as needed for your frontend
public class EmployeeManagerController {

    @Value("${file.upload-dir}")
    private String uploadDir; // Path to your Documents folder

    private final EmployeeManagerService employeeManagerService;

    public EmployeeManagerController(EmployeeManagerService employeeManagerService) {
        this.employeeManagerService = employeeManagerService;
    }

    // Add Employee method (used by admins to add employees)
    @PostMapping("/add")
    public ResponseEntity<?> addEmployee(
            @Valid @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("country") String country,
            @RequestParam("streetAddress") String streetAddress,
            @RequestParam("city") String city,
            @RequestParam("region") String region,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("companyName") String companyName,
            @RequestParam("employeeId") int employeeId,
            @RequestParam("corporateEmail") String corporateEmail,
            @RequestParam("jobRole") String jobRole,
            @RequestParam("employmentStatus") String employmentStatus,
            @RequestParam("reportingTo") String reportingTo,
            @RequestParam("role") String role,
            @RequestParam("nationalCard") MultipartFile nationalCard,
            @RequestParam(value = "tenthCertificate", required = false) MultipartFile tenthCertificate,
            @RequestParam(value = "twelfthCertificate", required = false) MultipartFile twelfthCertificate,
            @RequestParam(value = "graduationCertificate", required = false) MultipartFile graduationCertificate) {

        try {
            EmployeeManagerDTO employeeManagerDTO = new EmployeeManagerDTO();
            employeeManagerDTO.setFirstName(firstName);
            employeeManagerDTO.setLastName(lastName);
            employeeManagerDTO.setEmail(email);
            employeeManagerDTO.setCountry(country);
            employeeManagerDTO.setStreetAddress(streetAddress);
            employeeManagerDTO.setCity(city);
            employeeManagerDTO.setRegion(region);
            employeeManagerDTO.setPostalCode(postalCode);
            employeeManagerDTO.setCompanyName(companyName);
            employeeManagerDTO.setEmployeeId(employeeId);
            employeeManagerDTO.setCorporateEmail(corporateEmail);
            employeeManagerDTO.setJobRole(jobRole);
            employeeManagerDTO.setEmploymentStatus(employmentStatus);
            employeeManagerDTO.setReportingTo(reportingTo);
            employeeManagerDTO.setRole(role);

            // Save files and update DTO fields for certificates
            employeeManagerDTO.setNationalCard(saveFile(nationalCard, "nationalCard"));
            employeeManagerDTO.setTenthCertificate(saveOptionalFile(tenthCertificate, "tenthCertificate"));
            employeeManagerDTO.setTwelfthCertificate(saveOptionalFile(twelfthCertificate, "twelfthCertificate"));
            employeeManagerDTO.setGraduationCertificate(saveOptionalFile(graduationCertificate, "graduationCertificate"));

            EmployeeManagerDTO employeeManager = employeeManagerService.addEmployee(employeeManagerDTO);

            return ResponseEntity.ok(employeeManager);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }

    // Registration endpoint (for Admins)
    @PostMapping("/register")
    public ResponseEntity<?> registerAdmin(
            @Valid @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName,
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        EmployeeManagerDTO employeeManagerDTO = new EmployeeManagerDTO();
        employeeManagerDTO.setFirstName(firstName);
        employeeManagerDTO.setLastName(lastName);
        employeeManagerDTO.setEmail(email);
        employeeManagerDTO.setCorporateEmail(email); // Set corporate email to the same email for registration
        employeeManagerDTO.setRole("Admin"); // Default role for admin
        employeeManagerDTO.setPassword(password); // Set plain text password

        try {
            EmployeeManagerDTO registeredAdmin = employeeManagerService.addAdmin(employeeManagerDTO);
            return ResponseEntity.ok(registeredAdmin);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }

    // Save files (reuse from previous add employee)
    private String saveFile(MultipartFile file, String fileType) throws IOException {
        if (file != null && !file.isEmpty()) {
            Path filePath = Paths.get(uploadDir, fileType + "-" + file.getOriginalFilename());
            Files.write(filePath, file.getBytes());
            return filePath.toString();
        }
        return null;
    }

    private String saveOptionalFile(MultipartFile file, String fileType) throws IOException {
        if (file != null && !file.isEmpty()) {
            return saveFile(file, fileType);
        }
        return null;
    }

    // New login endpoint
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) {
        LoginResponse response = employeeManagerService.loginEmployee(loginDTO);
        return new ResponseEntity<>(response, response.getStatus() ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }



    // Fetch all employees
    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees() {
        try {
            List<EmployeeManagerDTO> employees = employeeManagerService.getAllEmployees();
            return ResponseEntity.ok(employees);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch employees: " + e.getMessage());
        }
    }

    // Fetch a specific employee by ID
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("employeeId") int employeeId) {
        try {
            EmployeeManagerDTO employee = employeeManagerService.getEmployeeById(employeeId);
            if (employee != null) {
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to fetch employee: " + e.getMessage());
        }
    }

    // Delete an employee by ID
//    @DeleteMapping("/employees/{id}")
//    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") int employeeId) {
//        try {
//            boolean isDeleted = employeeManagerService.deleteEmployeeById(employeeId);
//            if (isDeleted) {
//                return ResponseEntity.ok("Employee deleted successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete employee: " + e.getMessage());
//        }
//    }


    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") int employeeId) {
        try {
            boolean isDeleted = employeeManagerService.deleteEmployeeById(employeeId);
            if (isDeleted) {
                return ResponseEntity.ok("Employee deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee not found");
            }
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete employee: " + e.getMessage());
        }
    }


    // New endpoint to get file size
    @GetMapping("/fileSize")
    public ResponseEntity<Map<String, Long>> getFileSize(@RequestParam String fileName) {
        try {
            Path filePath = Paths.get(uploadDir, fileName); // Construct the file path
            File file = filePath.toFile(); // Convert to File object

            if (file.exists()) {
                Map<String, Long> response = new HashMap<>();
                response.put("size", file.length()); // Size in bytes
                return ResponseEntity.ok(response);
            } else {
                // If the file does not exist, return a size of 0 with an appropriate message
                Map<String, Long> response = new HashMap<>();
                response.put("size", 0L); // File not found, size is 0
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            // Return size as 0 in case of error
            Map<String, Long> response = new HashMap<>();
            response.put("size", 0L); // Error occurred, size is 0
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}


