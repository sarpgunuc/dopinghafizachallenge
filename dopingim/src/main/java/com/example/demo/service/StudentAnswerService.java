package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Question;
import com.example.demo.model.StudentAnswer;
import com.example.demo.model.StudentQuiz;
import com.example.demo.repository.StudentAnswerRepository;
import com.example.demo.repository.StudentQuizRepository;
import com.example.demo.repository.QuestionRepository;

import java.util.List;

@Service
public class StudentAnswerService {

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    @Autowired
    private StudentQuizRepository studentQuizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    public StudentAnswer saveAnswer(Long studentQuizId, Long questionId, String answer) {
        if (studentQuizId == null || questionId == null) {
            throw new IllegalArgumentException("StudentQuizId and QuestionId must not be null");
        }

        StudentQuiz studentQuiz = studentQuizRepository.findById(studentQuizId).orElseThrow(() -> new RuntimeException("StudentQuiz not found"));
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));

        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setStudentQuiz(studentQuiz);
        studentAnswer.setQuestion(question);
        studentAnswer.setAnswer(answer);
        studentAnswer.setCorrect(isCorrectAnswer(questionId, answer));

        return studentAnswerRepository.save(studentAnswer);
    }

    public List<StudentAnswer> getAllStudentAnswers() {
        return studentAnswerRepository.findAll();
    }

    public StudentAnswer getStudentAnswerById(Long id) {
        return studentAnswerRepository.findById(id).orElse(null);
    }

    private boolean isCorrectAnswer(Long questionId, String answer) {
        Question question = questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question not found"));
        return question.getAnswer().equalsIgnoreCase(answer);
    }

    public int calculateScore(Long studentQuizId) {
        List<StudentAnswer> studentAnswers = studentAnswerRepository.findByStudentQuizId(studentQuizId);
        int correctAnswers = 0;
        for (StudentAnswer answer : studentAnswers) {
            if (answer.isCorrect()) {
                correctAnswers++;
            }
        }
        int totalQuestions = studentAnswers.size();
        return (int) ((double) correctAnswers / totalQuestions * 100);
    }
}
