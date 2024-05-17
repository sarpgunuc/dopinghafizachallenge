package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Quiz;
import com.example.demo.model.Student;
import com.example.demo.model.StudentQuiz;
import com.example.demo.repository.StudentQuizRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.QuizRepository;

import java.util.List;

@Service
public class StudentQuizService {

    @Autowired
    private StudentQuizRepository studentQuizRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuizRepository quizRepository;

    public StudentQuiz startQuiz(Long studentId, Long quizId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));

        StudentQuiz studentQuiz = new StudentQuiz();
        studentQuiz.setStudent(student);
        studentQuiz.setQuiz(quiz);
        studentQuiz.setScore(0);

        return studentQuizRepository.save(studentQuiz);
    }

    public List<StudentQuiz> getAllStudentQuizzes() {
        return studentQuizRepository.findAll();
    }

    public StudentQuiz getStudentQuizById(Long id) {
        return studentQuizRepository.findById(id).orElse(null);
    }

    public StudentQuiz findByStudentIdAndQuizId(Long studentId, Long quizId) {
        return studentQuizRepository.findByStudentIdAndQuizId(studentId, quizId);
    }

    public void updateScore(Long studentQuizId, int score) {
        StudentQuiz studentQuiz = studentQuizRepository.findById(studentQuizId).orElseThrow(() -> new RuntimeException("StudentQuiz not found"));
        studentQuiz.setScore(score);
        studentQuizRepository.save(studentQuiz);
    }
}
