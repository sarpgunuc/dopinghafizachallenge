package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }

    @Cacheable("questions")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Cacheable("questions")
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }
}
