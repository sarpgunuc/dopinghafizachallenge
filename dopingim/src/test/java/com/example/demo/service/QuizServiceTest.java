package com.example.demo.service;

import com.example.demo.model.Quiz;
import com.example.demo.repository.QuizRepository;
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
public class QuizServiceTest {

    @InjectMocks
    private QuizService quizService;

    @Mock
    private QuizRepository quizRepository;

    @Test
    public void testCreateQuiz() {
        Quiz quiz = new Quiz();
        quiz.setName("Sample Quiz");

        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        Quiz createdQuiz = quizService.createQuiz(quiz);

        assertNotNull(createdQuiz);
        assertEquals("Sample Quiz", createdQuiz.getName());
    }

    @Test
    public void testGetAllQuizzes() {
        Quiz quiz1 = new Quiz();
        quiz1.setName("Quiz 1");

        Quiz quiz2 = new Quiz();
        quiz2.setName("Quiz 2");

        List<Quiz> quizzes = Arrays.asList(quiz1, quiz2);
        when(quizRepository.findAll()).thenReturn(quizzes);

        List<Quiz> result = quizService.getAllQuizzes();

        assertEquals(2, result.size());
        assertEquals("Quiz 1", result.get(0).getName());
        assertEquals("Quiz 2", result.get(1).getName());
    }

    @Test
    public void testGetQuizById() {
        Quiz quiz = new Quiz();
        quiz.setName("Sample Quiz");

        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));

        Quiz result = quizService.getQuizById(1L);

        assertNotNull(result);
        assertEquals("Sample Quiz", result.getName());
    }
}
