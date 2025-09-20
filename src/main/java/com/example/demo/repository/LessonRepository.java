package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import com.example.demo.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    List<Lesson> findByCourseId(Integer courseId);
    Lesson findByTitleAndCourseId(String title,int courseId);
    Lesson findLessonsById(Integer id);
}