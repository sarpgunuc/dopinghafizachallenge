package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.model.StudentAnswer;
import com.example.demo.model.StudentQuiz;
import com.example.demo.service.StudentAnswerService;
import com.example.demo.service.StudentQuizService;
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
public class StudentAnswerControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private StudentAnswerController studentAnswerController;

    @Mock
    private StudentAnswerService studentAnswerService;

    @Mock
    private StudentQuizService studentQuizService;

    @Test
    public void testCreateStudentAnswer() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentAnswerController).build();

        StudentQuiz studentQuiz = new StudentQuiz();
        studentQuiz.setId(1L);

        Question question = new Question();
        question.setId(1L);

        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setStudentQuiz(studentQuiz);
        studentAnswer.setQuestion(question);
        studentAnswer.setAnswer("Correct Answer");
        studentAnswer.setCorrect(true);

        when(studentAnswerService.saveAnswer(any(Long.class), any(Long.class), any(String.class))).thenReturn(studentAnswer);
        when(studentAnswerService.calculateScore(1L)).thenReturn(100);

        mockMvc.perform(post("/api/student-answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"studentQuiz\": {\"id\": 1}, \"question\": {\"id\": 1}, \"answer\": \"Correct Answer\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"answer\": \"Correct Answer\", \"correct\": true}"));
    }

    @Test
    public void testGetAllStudentAnswers() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentAnswerController).build();

        StudentAnswer studentAnswer1 = new StudentAnswer();
        StudentAnswer studentAnswer2 = new StudentAnswer();

        List<StudentAnswer> studentAnswers = Arrays.asList(studentAnswer1, studentAnswer2);
        when(studentAnswerService.getAllStudentAnswers()).thenReturn(studentAnswers);

        mockMvc.perform(get("/api/student-answers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));
    }

    @Test
    public void testGetStudentAnswerById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentAnswerController).build();

        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setId(1L);

        when(studentAnswerService.getStudentAnswerById(1L)).thenReturn(studentAnswer);

        mockMvc.perform(get("/api/student-answers/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": 1}"));
    }
}
