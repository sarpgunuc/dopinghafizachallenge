package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.StudentQuiz;
import com.example.demo.service.StudentQuizService;
import java.util.List;

@RestController
@RequestMapping("/api/student-quizzes")
public class StudentQuizController {

    @Autowired
    private StudentQuizService studentQuizService;

    @PostMapping("/start")
    public ResponseEntity<StudentQuiz> startQuiz(@RequestBody StudentQuiz studentQuiz) {
        StudentQuiz createdStudentQuiz = studentQuizService.startQuiz(studentQuiz.getStudent().getId(), studentQuiz.getQuiz().getId());
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
}
