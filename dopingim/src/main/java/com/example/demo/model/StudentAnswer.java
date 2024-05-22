package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Bir öğrenci birden fazla teste katılabilir veya bir teste birden fazla öğrenci katılabilir.
    @ManyToOne
    @JoinColumn(name = "student_quiz_id")
    @JsonBackReference
    private StudentQuiz studentQuiz;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String answer;
    private boolean isCorrect;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StudentQuiz getStudentQuiz() {
		return studentQuiz;
	}
	public void setStudentQuiz(StudentQuiz studentQuiz) {
		this.studentQuiz = studentQuiz;
	}
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}

 
}
