package com.example.demo.controller;

import com.example.demo.model.Quiz;
import com.example.demo.service.QuizService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class QuizControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private QuizController quizController;

    @Mock
    private QuizService quizService;

    @Test
    public void testCreateQuiz() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();

        Quiz quiz = new Quiz();
        quiz.setName("Sample Quiz");

        when(quizService.createQuiz(any(Quiz.class))).thenReturn(quiz);

        mockMvc.perform(post("/api/quizzes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Sample Quiz\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\": \"Sample Quiz\"}"));
    }

    @Test
    public void testGetAllQuizzes() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();

        Quiz quiz1 = new Quiz();
        quiz1.setName("Quiz 1");

        Quiz quiz2 = new Quiz();
        quiz2.setName("Quiz 2");

        List<Quiz> quizzes = Arrays.asList(quiz1, quiz2);
        when(quizService.getAllQuizzes()).thenReturn(quizzes);

        mockMvc.perform(get("/api/quizzes"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\": \"Quiz 1\"}, {\"name\": \"Quiz 2\"}]"));
    }

    @Test
    public void testGetQuizById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(quizController).build();

        Quiz quiz = new Quiz();
        quiz.setName("Sample Quiz");

        when(quizService.getQuizById(1L)).thenReturn(quiz);

        mockMvc.perform(get("/api/quizzes/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"name\": \"Sample Quiz\"}"));
    }
}
