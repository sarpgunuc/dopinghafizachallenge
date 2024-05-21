package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.example.demo.model.Quiz;
import com.example.demo.repository.QuizRepository;

import java.util.List;

@Service
public class QuizService {
    
    @Autowired
    private QuizRepository quizRepository;

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Cacheable("quizzes")
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Cacheable("quizzes")
    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }
}
