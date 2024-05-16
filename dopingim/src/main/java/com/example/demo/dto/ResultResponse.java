package com.example.demo.dto;

import com.example.demo.model.Quiz;
import com.example.demo.model.Student;

public class ResultResponse {
    private Long studentQuizId;
    private Student student;
    private Quiz quiz;
    private int score;

    public ResultResponse(Long studentQuizId, Student student, Quiz quiz, int score) {
        this.studentQuizId = studentQuizId;
        this.student = student;
        this.quiz = quiz;
        this.score = score;
    }

    public Long getStudentQuizId() {
        return studentQuizId;
    }

    public void setStudentQuizId(Long studentQuizId) {
        this.studentQuizId = studentQuizId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
