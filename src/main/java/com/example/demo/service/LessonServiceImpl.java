package com.example.demo.service;

import com.example.demo.entity.Lesson;
import com.example.demo.repository.LessonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    public LessonServiceImpl(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }
    @Override
    public List<Lesson> findByCourseId(Integer courseId) {
        return lessonRepository.findByCourseId(courseId);
    }
    @Override
    public Lesson findById(Integer id) {
        return lessonRepository.findLessonsById(id);
    }
    @Override
    public Lesson save(Lesson lesson) {
        return lessonRepository.save(lesson);
    }
    public Lesson findByTitleAndCourseId(String title, int courseId) {
        return lessonRepository.findByTitleAndCourseId(title, courseId);
    }
    public Lesson updateLesson(Lesson lesson) {
        Lesson l = lessonRepository.save(lesson);
        return l;
    }
}