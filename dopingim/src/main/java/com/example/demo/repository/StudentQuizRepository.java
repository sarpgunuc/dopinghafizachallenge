package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.StudentQuiz;
import com.example.demo.model.Student;

import java.util.List;

@Repository
public interface StudentQuizRepository extends JpaRepository<StudentQuiz, Long> {
    StudentQuiz findByStudentIdAndQuizId(Long studentId, Long quizId);
    List<StudentQuiz> findByStudent(Student student);
}
