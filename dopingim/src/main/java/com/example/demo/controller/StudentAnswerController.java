package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.StudentAnswer;
import com.example.demo.service.StudentAnswerService;
import com.example.demo.service.StudentQuizService;

import java.util.List;

@RestController
@RequestMapping("/api/student-answers")
public class StudentAnswerController {

    @Autowired
    private StudentAnswerService studentAnswerService;

    @Autowired
    private StudentQuizService studentQuizService;

    @PostMapping
    public ResponseEntity<StudentAnswer> createStudentAnswer(@RequestBody StudentAnswer studentAnswer) {
        if (studentAnswer.getStudentQuiz() == null || studentAnswer.getStudentQuiz().getId() == null ||
            studentAnswer.getQuestion() == null || studentAnswer.getQuestion().getId() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        StudentAnswer savedStudentAnswer = studentAnswerService.saveAnswer(studentAnswer.getStudentQuiz().getId(), studentAnswer.getQuestion().getId(), studentAnswer.getAnswer());
        int score = studentAnswerService.calculateScore(studentAnswer.getStudentQuiz().getId());
        studentQuizService.updateScore(studentAnswer.getStudentQuiz().getId(), score);

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
