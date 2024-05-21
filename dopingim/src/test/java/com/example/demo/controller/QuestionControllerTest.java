package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.service.QuestionService;
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
public class QuestionControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private QuestionController questionController;

    @Mock
    private QuestionService questionService;

    @Test
    public void testCreateQuestion() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();

        Question question = new Question();
        question.setQuestionText("Sample Question");
        question.setAnswer("Sample Answer");

        when(questionService.createQuestion(any(Question.class))).thenReturn(question);

        mockMvc.perform(post("/api/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"questionText\": \"Sample Question\", \"answer\": \"Sample Answer\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"questionText\": \"Sample Question\", \"answer\": \"Sample Answer\"}"));
    }

    @Test
    public void testGetAllQuestions() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();

        Question question1 = new Question();
        question1.setQuestionText("Question 1");

        Question question2 = new Question();
        question2.setQuestionText("Question 2");

        List<Question> questions = Arrays.asList(question1, question2);
        when(questionService.getAllQuestions()).thenReturn(questions);

        mockMvc.perform(get("/api/questions"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"questionText\": \"Question 1\"}, {\"questionText\": \"Question 2\"}]"));
    }

    @Test
    public void testGetQuestionById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();

        Question question = new Question();
        question.setQuestionText("Sample Question");

        when(questionService.getQuestionById(1L)).thenReturn(question);

        mockMvc.perform(get("/api/questions/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"questionText\": \"Sample Question\"}"));
    }
}
