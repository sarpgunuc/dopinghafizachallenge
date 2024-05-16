package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Question;
import com.example.demo.model.StudentAnswer;
import com.example.demo.model.StudentQuiz;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.StudentAnswerRepository;
import com.example.demo.repository.StudentQuizRepository;
import java.util.List;

@Service
public class StudentAnswerService {
    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentQuizRepository studentQuizRepository;

    public StudentAnswer saveAnswer(Long studentQuizId, Long questionId, String answer) {
        StudentQuiz studentQuiz = studentQuizRepository.findById(studentQuizId)
            .orElseThrow(() -> new RuntimeException("StudentQuiz not found"));
        Question question = questionRepository.findById(questionId)
            .orElseThrow(() -> new RuntimeException("Question not found"));

        boolean isCorrect = question.getAnswer().equalsIgnoreCase(answer);

        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setStudentQuiz(studentQuiz);
        studentAnswer.setQuestion(question);
        studentAnswer.setAnswer(answer);
        studentAnswer.setCorrect(isCorrect);

        return studentAnswerRepository.save(studentAnswer);
    }

    public List<StudentAnswer> getAllStudentAnswers() {
        return studentAnswerRepository.findAll();
    }

    public StudentAnswer getStudentAnswerById(Long id) {
        return studentAnswerRepository.findById(id).orElse(null);
    }
}
