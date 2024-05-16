package com.example.demo.dto;

public class AnswerRequest {
    private Long studentQuizId;
    private Long questionId;
    private String answer;

    public Long getStudentQuizId() {
        return studentQuizId;
    }

    public void setStudentQuizId(Long studentQuizId) {
        this.studentQuizId = studentQuizId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
