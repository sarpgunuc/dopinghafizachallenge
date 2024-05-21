package com.example.demo.service;

import com.example.demo.model.Quiz;
import com.example.demo.model.Student;
import com.example.demo.model.StudentQuiz;
import com.example.demo.repository.QuizRepository;
import com.example.demo.repository.StudentQuizRepository;
import com.example.demo.repository.StudentRepository;
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
public class StudentQuizServiceTest {

    @InjectMocks
    private StudentQuizService studentQuizService;

    @Mock
    private StudentQuizRepository studentQuizRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private QuizRepository quizRepository;

    @Test
    public void testStartQuiz() {
        Student student = new Student();
        student.setId(1L);
        student.setUsername("testuser");

        Quiz quiz = new Quiz();
        quiz.setId(1L);
        quiz.setName("Sample Quiz");

        StudentQuiz studentQuiz = new StudentQuiz();
        studentQuiz.setStudent(student);
        studentQuiz.setQuiz(quiz);
        studentQuiz.setScore(0);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(quizRepository.findById(1L)).thenReturn(Optional.of(quiz));
        when(studentQuizRepository.save(any(StudentQuiz.class))).thenReturn(studentQuiz);

        StudentQuiz result = studentQuizService.startQuiz(1L, 1L);

        assertNotNull(result);
        assertEquals("testuser", result.getStudent().getUsername());
        assertEquals("Sample Quiz", result.getQuiz().getName());
        assertEquals(0, result.getScore());
    }

    @Test
    public void testGetAllStudentQuizzes() {
        StudentQuiz studentQuiz1 = new StudentQuiz();
        StudentQuiz studentQuiz2 = new StudentQuiz();

        List<StudentQuiz> studentQuizzes = Arrays.asList(studentQuiz1, studentQuiz2);
        when(studentQuizRepository.findAll()).thenReturn(studentQuizzes);

        List<StudentQuiz> result = studentQuizService.getAllStudentQuizzes();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetStudentQuizById() {
        StudentQuiz studentQuiz = new StudentQuiz();
        studentQuiz.setId(1L);

        when(studentQuizRepository.findById(1L)).thenReturn(Optional.of(studentQuiz));

        StudentQuiz result = studentQuizService.getStudentQuizById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testFindByStudentIdAndQuizId() {
        StudentQuiz studentQuiz = new StudentQuiz();
        studentQuiz.setId(1L);

        when(studentQuizRepository.findByStudentIdAndQuizId(1L, 1L)).thenReturn(studentQuiz);

        StudentQuiz result = studentQuizService.findByStudentIdAndQuizId(1L, 1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}
