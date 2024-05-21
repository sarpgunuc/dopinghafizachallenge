package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Student saveStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));
        return studentRepository.save(student);
    }

    @Cacheable("students")
    public Optional<Student> findStudentByUsername(String username) {
        long start = System.currentTimeMillis();
        Optional<Student> student = studentRepository.findByUsername(username);
        long end = System.currentTimeMillis();
        System.out.println("findStudentByUsername execution time: " + (end - start) + "ms");
        return student;
    }
    
    @Cacheable("students")
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public boolean checkPassword(String username, String rawPassword) {
        Optional<Student> studentOpt = studentRepository.findByUsername(username);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            return passwordEncoder.matches(rawPassword, student.getPassword());
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Student not found"));
        return new org.springframework.security.core.userdetails.User(student.getUsername(), student.getPassword(), new ArrayList<>());
    }
}
