package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

  
   

    // Creating students
    @PostMapping("/signup")
    public ResponseEntity<Student> signup(@RequestBody Student student) {
        System.out.println("Signup request received: " + student);
        Student savedStudent = studentService.saveStudent(student);
        System.out.println("Student saved: " + savedStudent);
        return ResponseEntity.ok(savedStudent);
    }

    // Student login 
    @PostMapping("/student-login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        final UserDetails userDetails = studentService.loadUserByUsername(username);
        final String jwt = jwtUtil.generateToken(userDetails);  // Burada doğru argümanı kullanın
        System.out.println("Generated JWT Token: " + jwt); // Log the generated token
        return ResponseEntity.ok(Map.of("token", jwt));
    }

    // Get student by username

    @GetMapping("/{username}")
    public ResponseEntity<Student> getStudent(@PathVariable("username") String username) {
        Optional<Student> student = studentService.findStudentByUsername(username);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // get all student
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }
}
