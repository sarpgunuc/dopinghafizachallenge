package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.StudentQuiz;
import com.example.demo.model.Student;
import com.example.demo.model.Quiz;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentQuizRepository extends JpaRepository<StudentQuiz, Long> {
    Optional<StudentQuiz> findByStudentAndQuiz(Student student, Quiz quiz);
    List<StudentQuiz> findByStudent(Student student); 
}
