package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.model.StudentQuiz;
import com.example.demo.service.StudentQuizService;
import com.example.demo.service.StudentService;
import com.example.demo.util.JwtUtil;
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
public class StudentQuizControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private StudentQuizController studentQuizController;

    @Mock
    private StudentQuizService studentQuizService;

    @Mock
    private StudentService studentService;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    public void testStartQuiz() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentQuizController).build();

        Student student = new Student();
        student.setId(1L);
        student.setUsername("testuser");

        StudentQuiz studentQuiz = new StudentQuiz();
        studentQuiz.setStudent(student);
        studentQuiz.setQuiz(null);  // Simplification for the test
        studentQuiz.setScore(0);

        when(studentQuizService.startQuiz(any(Long.class), any(Long.class))).thenReturn(studentQuiz);

        mockMvc.perform(post("/api/student-quizzes/start")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"student\": {\"id\": 1, \"username\": \"testuser\"}, \"quiz\": {\"id\": 1}, \"score\": 0}")
                .header("Authorization", "Bearer valid-token"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"student\": {\"id\": 1, \"username\": \"testuser\"}, \"score\": 0}"));
    }

    @Test
    public void testGetAllStudentQuizzes() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentQuizController).build();

        StudentQuiz studentQuiz1 = new StudentQuiz();
        StudentQuiz studentQuiz2 = new StudentQuiz();

        List<StudentQuiz> studentQuizzes = Arrays.asList(studentQuiz1, studentQuiz2);
        when(studentQuizService.getAllStudentQuizzes()).thenReturn(studentQuizzes);

        mockMvc.perform(get("/api/student-quizzes"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));
    }

    @Test
    public void testGetStudentQuizById() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentQuizController).build();

        StudentQuiz studentQuiz = new StudentQuiz();
        studentQuiz.setId(1L);

        when(studentQuizService.getStudentQuizById(1L)).thenReturn(studentQuiz);

        mockMvc.perform(get("/api/student-quizzes/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\": 1}"));
    }

    @Test
    public void testGetStudentQuizScore() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentQuizController).build();

        StudentQuiz studentQuiz = new StudentQuiz();
        studentQuiz.setScore(85);

        when(studentQuizService.findByStudentIdAndQuizId(1L, 1L)).thenReturn(studentQuiz);

        mockMvc.perform(get("/api/student-quizzes/student/1/quiz/1/score"))
                .andExpect(status().isOk())
                .andExpect(content().json("85"));
    }

    @Test
    public void testGetStudentQuizzes() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(studentQuizController).build();

        StudentQuiz studentQuiz1 = new StudentQuiz();
        StudentQuiz studentQuiz2 = new StudentQuiz();

        List<StudentQuiz> studentQuizzes = Arrays.asList(studentQuiz1, studentQuiz2);
        when(studentQuizService.getStudentQuizzesByStudentId(1L)).thenReturn(studentQuizzes);

        mockMvc.perform(get("/api/student-quizzes/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}, {}]"));
    }
}
