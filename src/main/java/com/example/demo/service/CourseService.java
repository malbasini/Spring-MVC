package com.example.demo.service;
import java.util.List;
import com.example.demo.entity.*;
import org.springframework.data.domain.Page;

public interface CourseService {
    Course findById(Integer id);

    Page<Course> findCourses(int page, int size, String title, String sortBy, String sortDirection);

    List<Course> getTopRatedCourses();

    List<Course> getNewestCourses();

    Course getCourseByIdWithLessons(Integer id);

    void updateCourse(Course course);

    void deleteCourse(int id);

    void updateImagePath(String image, int id);

    User findByUsername(String username);
}