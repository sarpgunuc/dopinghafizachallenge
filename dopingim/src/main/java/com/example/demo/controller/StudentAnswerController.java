package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.StudentAnswer;
import com.example.demo.service.StudentAnswerService;
import com.example.demo.dto.AnswerRequest;
import java.util.List;

@RestController
@RequestMapping("/api/student-answers")
public class StudentAnswerController {
    @Autowired
    private StudentAnswerService studentAnswerService;

    @PostMapping
    public ResponseEntity<StudentAnswer> createStudentAnswer(@RequestBody AnswerRequest request) {
        StudentAnswer savedStudentAnswer = studentAnswerService.saveAnswer(request.getStudentQuizId(), request.getQuestionId(), request.getAnswer());
        return ResponseEntity.ok(savedStudentAnswer);
    }

    @GetMapping
    public ResponseEntity<List<StudentAnswer>> getAllStudentAnswers() {
        List<StudentAnswer> studentAnswers = studentAnswerService.getAllStudentAnswers();
        return ResponseEntity.ok(studentAnswers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentAnswer> getStudentAnswerById(@PathVariable("id") Long id) {
        StudentAnswer studentAnswer = studentAnswerService.getStudentAnswerById(id);
        if (studentAnswer != null) {
            return ResponseEntity.ok(studentAnswer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
