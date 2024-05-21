package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import com.example.demo.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private StudentController studentController;

    @Mock
    private StudentService studentService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void testSignup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

        Student student = new Student();
        student.setUsername("testuser");
        student.setPassword("testpass");

        when(studentService.saveStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(post("/api/students/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testuser\", \"password\": \"testpass\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\": \"testuser\", \"password\": \"testpass\"}"));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

        String username = "testuser";
        String password = "testpass";
        String token = "dummyjwt";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(studentService.loadUserByUsername(username)).thenReturn(new org.springframework.security.core.userdetails.User(username, password, List.of()));
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn(token);

        mockMvc.perform(post("/api/students/student-login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"testuser\", \"password\": \"testpass\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\": \"dummyjwt\"}"));
    }

    @Test
    public void testGetStudent() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

        Student student = new Student();
        student.setUsername("testuser");

        when(studentService.findStudentByUsername("testuser")).thenReturn(Optional.of(student));

        mockMvc.perform(get("/api/students/testuser"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"username\": \"testuser\"}"));
    }

    @Test
    public void testGetAllStudents() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();

        Student student1 = new Student();
        student1.setUsername("testuser1");

        Student student2 = new Student();
        student2.setUsername("testuser2");

        when(studentService.getAllStudents()).thenReturn(List.of(student1, student2));

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"username\": \"testuser1\"}, {\"username\": \"testuser2\"}]"));
    }
}
