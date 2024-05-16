package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AnswerRequest;
import com.example.demo.dto.QuestionResponse;
import com.example.demo.dto.ResultResponse;
import com.example.demo.dto.StartQuizRequest;
import com.example.demo.model.*;
import com.example.demo.service.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student-quizzes")
public class StudentQuizController {
    @Autowired
    private StudentQuizService studentQuizService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private StudentAnswerService studentAnswerService;

    @PostMapping("/start")
    public ResponseEntity<StudentQuiz> startQuiz(@RequestBody StartQuizRequest request) {
        StudentQuiz studentQuiz = studentQuizService.startQuiz(request.getStudentId(), request.getQuizId());
        return ResponseEntity.ok(studentQuiz);
    }

    @GetMapping("/{studentQuizId}/questions")
    public ResponseEntity<List<QuestionResponse>> getQuizQuestions(@PathVariable("studentQuizId") Long studentQuizId) {
        List<Question> questions = studentQuizService.getQuizQuestions(studentQuizId);
        List<QuestionResponse> questionResponses = questions.stream()
            .map(q -> new QuestionResponse(q.getId(), q.getQuestionText()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(questionResponses);
    }

    @PostMapping("/answers")
    public ResponseEntity<StudentAnswer> answerQuestion(@RequestBody AnswerRequest request) {
        StudentAnswer studentAnswer = studentAnswerService.saveAnswer(request.getStudentQuizId(), request.getQuestionId(), request.getAnswer());
        return ResponseEntity.ok(studentAnswer);
    }

    @GetMapping("/{studentQuizId}/result")
    public ResponseEntity<ResultResponse> getResult(@PathVariable("studentQuizId") Long studentQuizId) {
        int score = studentQuizService.calculateScore(studentQuizId);
        StudentQuiz studentQuiz = studentQuizService.getStudentQuizById(studentQuizId);
        ResultResponse resultResponse = new ResultResponse(studentQuizId, studentQuiz.getStudent(), studentQuiz.getQuiz(), score);
        return ResponseEntity.ok(resultResponse);
    }
}
