package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testSaveStudent() {
        Student student = new Student();
        student.setUsername("testuser");
        student.setPassword("testpass");

        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedpass");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student savedStudent = studentService.saveStudent(student);

        assertEquals("encodedpass", savedStudent.getPassword());
    }

    @Test
    public void testFindStudentByUsername() {
        Student student = new Student();
        student.setUsername("testuser");

        when(studentRepository.findByUsername("testuser")).thenReturn(Optional.of(student));

        Optional<Student> foundStudent = studentService.findStudentByUsername("testuser");

        assertTrue(foundStudent.isPresent());
        assertEquals("testuser", foundStudent.get().getUsername());
    }

    @Test
    public void testLoadUserByUsername() {
        Student student = new Student();
        student.setUsername("testuser");
        student.setPassword("testpass");

        when(studentRepository.findByUsername("testuser")).thenReturn(Optional.of(student));

        UserDetails userDetails = studentService.loadUserByUsername("testuser");

        assertEquals("testuser", userDetails.getUsername());
        assertEquals("testpass", userDetails.getPassword());
    }
}
