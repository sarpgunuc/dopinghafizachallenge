package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Question;
import com.example.demo.model.Quiz;
import com.example.demo.model.Student;
import com.example.demo.model.StudentQuiz;
import com.example.demo.model.StudentAnswer;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.QuizRepository;
import com.example.demo.repository.StudentQuizRepository;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.StudentAnswerRepository;
import java.util.List;

@Service
public class StudentQuizService {
    @Autowired
    private StudentQuizRepository studentQuizRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    public StudentQuiz startQuiz(Long studentId, Long quizId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("Student not found"));
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));

        StudentQuiz studentQuiz = new StudentQuiz();
        studentQuiz.setStudent(student);
        studentQuiz.setQuiz(quiz);

        return studentQuizRepository.save(studentQuiz);
    }

    public List<Question> getQuizQuestions(Long studentQuizId) {
        StudentQuiz studentQuiz = studentQuizRepository.findById(studentQuizId).orElseThrow(() -> new RuntimeException("StudentQuiz not found"));
        return studentQuiz.getQuiz().getQuestions();
    }

    public int calculateScore(Long studentQuizId) {
        StudentQuiz studentQuiz = studentQuizRepository.findById(studentQuizId).orElseThrow(() -> new RuntimeException("StudentQuiz not found"));
        int correctAnswers = (int) studentQuiz.getAnswers().stream().filter(StudentAnswer::isCorrect).count();
        int totalQuestions = studentQuiz.getQuiz().getQuestions().size();
        return (correctAnswers * 100) / totalQuestions;
    }

    public StudentQuiz getStudentQuizById(Long studentQuizId) {
        return studentQuizRepository.findById(studentQuizId).orElse(null);
    }
}