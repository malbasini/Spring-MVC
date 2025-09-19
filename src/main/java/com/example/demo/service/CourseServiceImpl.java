package com.example.demo.service;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.demo.entity.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Iterable<Course> findAll() {
        return courseRepository.findAll();
    }
    @Override
    public Course findById(Integer id) {
        return courseRepository.findCourseById(id);
    }
    @Transactional
    @Override
    public int saveCourse(Course course) {
        Course c = null;
        try {
            c = courseRepository.save(course);
        }
        catch (Exception ex){
            throw ex;
        }
        return c.getId();
    }
    @Override
    public Course getCourseByIdWithLessons(Integer id) {
        return courseRepository.findCourseWithLessons(id);
    }

    @Override
    public void deleteById(Integer id) {
        courseRepository.deleteById(id);
    }


    public Page<Course> findCourses(int page, int size, String title, String sortBy, String sortDirection) {
        Sort sort = Sort.by(sortBy);
        sort = sortDirection.equalsIgnoreCase("desc") ? sort.descending() : sort.ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        // Filtro per titolo
        if (title != null && !title.isEmpty()) {
            return courseRepository.findByTitleContainingIgnoreCase(title, pageable);
        }

        return courseRepository.findAll(pageable);
    }
    public List<Course> getTopRatedCourses() {
        Pageable pageable = PageRequest.of(0, 3); // Prima pagina, 3 risultati
        return courseRepository.findTopByRating(pageable);
    }

    public List<Course> getNewestCourses() {
        Pageable pageable = PageRequest.of(0, 3); // Prima pagina, 3 risultati
        return courseRepository.findTopByNewest(pageable);
    }
    public void updateCourse(Course course) {
        courseRepository.save(course);
    }
    public void deleteCourse(int id) {
        Course c = courseRepository.findCourseById(id);
        courseRepository.delete(c);

    }
    public void updateImagePath(String image, int id) {
        Course c = courseRepository.findCourseById(id);
        if (c != null) {
            c.setImagePath(image);
        }
        courseRepository.save(c);
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String getEmailByCourseIdAndAuthor(int courseId, String author) {
        return courseRepository.findCourseByAuthorAndId(author, courseId).getEmail();
    }
}