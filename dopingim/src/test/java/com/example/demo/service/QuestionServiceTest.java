package com.example.demo.service;

import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    public void testCreateQuestion() {
        Question question = new Question();
        question.setQuestionText("Sample Question");
        question.setAnswer("Sample Answer");

        when(questionRepository.save(any(Question.class))).thenReturn(question);

        Question createdQuestion = questionService.createQuestion(question);

        assertNotNull(createdQuestion);
        assertEquals("Sample Question", createdQuestion.getQuestionText());
        assertEquals("Sample Answer", createdQuestion.getAnswer());
    }

    @Test
    public void testGetAllQuestions() {
        Question question1 = new Question();
        question1.setQuestionText("Question 1");

        Question question2 = new Question();
        question2.setQuestionText("Question 2");

        List<Question> questions = Arrays.asList(question1, question2);
        when(questionRepository.findAll()).thenReturn(questions);

        List<Question> result = questionService.getAllQuestions();

        assertEquals(2, result.size());
        assertEquals("Question 1", result.get(0).getQuestionText());
        assertEquals("Question 2", result.get(1).getQuestionText());
    }

    @Test
    public void testGetQuestionById() {
        Question question = new Question();
        question.setQuestionText("Sample Question");

        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));

        Question result = questionService.getQuestionById(1L);

        assertNotNull(result);
        assertEquals("Sample Question", result.getQuestionText());
    }
}
