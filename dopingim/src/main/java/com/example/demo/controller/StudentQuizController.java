package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.StudentQuiz;
import com.example.demo.service.StudentQuizService;
import com.example.demo.service.StudentService;

import com.example.demo.util.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/api/student-quizzes")
public class StudentQuizController {

    @Autowired
    private StudentQuizService studentQuizService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/start")
    public ResponseEntity<StudentQuiz> startQuiz(@RequestBody StudentQuiz studentQuiz, @RequestHeader("Authorization") String token) {
        String username = studentQuiz.getStudent().getUsername();
    	System.out.println("BAKALIM YANLIŞ MI ");
     //   if (!jwtUtil.validateToken(token.substring(7), studentService.loadUserByUsername(username))) {
        //	System.out.println("İFİN ALTI ------------ ");
          //  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
       // }
        StudentQuiz createdStudentQuiz = studentQuizService.startQuiz(studentQuiz.getStudent().getId(), studentQuiz.getQuiz().getId());
    	System.out.println("RETURN ÜST-------------------- ");
        return ResponseEntity.ok(createdStudentQuiz);
    }

    @GetMapping
    public ResponseEntity<List<StudentQuiz>> getAllStudentQuizzes() {
        List<StudentQuiz> studentQuizzes = studentQuizService.getAllStudentQuizzes();
        return ResponseEntity.ok(studentQuizzes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentQuiz> getStudentQuizById(@PathVariable("id") Long id) {
        StudentQuiz studentQuiz = studentQuizService.getStudentQuizById(id);
        if (studentQuiz != null) {
            return ResponseEntity.ok(studentQuiz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student/{studentId}/quiz/{quizId}/score")
    public ResponseEntity<Integer> getStudentQuizScore(@PathVariable("studentId") Long studentId, @PathVariable("quizId") Long quizId) {
        StudentQuiz studentQuiz = studentQuizService.findByStudentIdAndQuizId(studentId, quizId);
        if (studentQuiz != null) {
            return ResponseEntity.ok(studentQuiz.getScore());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{studentQuizId}/result")
    public ResponseEntity<StudentQuiz> getStudentQuizResult(@PathVariable("studentQuizId") Long studentQuizId) {
        StudentQuiz studentQuiz = studentQuizService.getStudentQuizById(studentQuizId);
        if (studentQuiz != null) {
            return ResponseEntity.ok(studentQuiz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<StudentQuiz>> getStudentQuizzes(@PathVariable("studentId") Long studentId) {
        List<StudentQuiz> studentQuizzes = studentQuizService.getStudentQuizzesByStudentId(studentId);
        if (studentQuizzes != null) {
            return ResponseEntity.ok(studentQuizzes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
