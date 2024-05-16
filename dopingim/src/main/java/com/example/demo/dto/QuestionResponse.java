package com.example.demo.dto;

public class QuestionResponse {
    private Long id;
    private String questionText;

    public QuestionResponse(Long id, String questionText) {
        this.id = id;
        this.questionText = questionText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }
}
