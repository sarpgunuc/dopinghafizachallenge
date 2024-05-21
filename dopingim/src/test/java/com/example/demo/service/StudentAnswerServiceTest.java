package com.example.demo.service;

import com.example.demo.model.Question;
import com.example.demo.model.StudentAnswer;
import com.example.demo.model.StudentQuiz;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.StudentAnswerRepository;
import com.example.demo.repository.StudentQuizRepository;
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
public class StudentAnswerServiceTest {

    @InjectMocks
    private StudentAnswerService studentAnswerService;

    @Mock
    private StudentAnswerRepository studentAnswerRepository;

    @Mock
    private StudentQuizRepository studentQuizRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Test
    public void testSaveAnswer() {
        StudentQuiz studentQuiz = new StudentQuiz();
        studentQuiz.setId(1L);

        Question question = new Question();
        question.setId(1L);
        question.setAnswer("Correct Answer");

        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setStudentQuiz(studentQuiz);
        studentAnswer.setQuestion(question);
        studentAnswer.setAnswer("Correct Answer");
        studentAnswer.setCorrect(true);

        when(studentQuizRepository.findById(1L)).thenReturn(Optional.of(studentQuiz));
        when(questionRepository.findById(1L)).thenReturn(Optional.of(question));
        when(studentAnswerRepository.save(any(StudentAnswer.class))).thenReturn(studentAnswer);

        StudentAnswer savedStudentAnswer = studentAnswerService.saveAnswer(1L, 1L, "Correct Answer");

        assertNotNull(savedStudentAnswer);
        assertEquals("Correct Answer", savedStudentAnswer.getAnswer());
        assertEquals(true, savedStudentAnswer.isCorrect());
    }

    @Test
    public void testGetAllStudentAnswers() {
        StudentAnswer studentAnswer1 = new StudentAnswer();
        StudentAnswer studentAnswer2 = new StudentAnswer();

        List<StudentAnswer> studentAnswers = Arrays.asList(studentAnswer1, studentAnswer2);
        when(studentAnswerRepository.findAll()).thenReturn(studentAnswers);

        List<StudentAnswer> result = studentAnswerService.getAllStudentAnswers();

        assertEquals(2, result.size());
    }

    @Test
    public void testGetStudentAnswerById() {
        StudentAnswer studentAnswer = new StudentAnswer();
        studentAnswer.setId(1L);

        when(studentAnswerRepository.findById(1L)).thenReturn(Optional.of(studentAnswer));

        StudentAnswer result = studentAnswerService.getStudentAnswerById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testCalculateScore() {
        StudentAnswer studentAnswer1 = new StudentAnswer();
        studentAnswer1.setCorrect(true);

        StudentAnswer studentAnswer2 = new StudentAnswer();
        studentAnswer2.setCorrect(false);

        List<StudentAnswer> studentAnswers = Arrays.asList(studentAnswer1, studentAnswer2);
        when(studentAnswerRepository.findByStudentQuizId(1L)).thenReturn(studentAnswers);

        int score = studentAnswerService.calculateScore(1L);

        assertEquals(50, score);
    }
}
