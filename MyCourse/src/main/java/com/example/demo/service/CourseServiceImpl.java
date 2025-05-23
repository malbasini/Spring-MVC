package com.example.demo.service;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.example.demo.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.repository.CourseJdbcRepository;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private CourseJdbcRepository courseJdbcRepository;
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
        int courseId;
        try {
            courseId = courseJdbcRepository.saveCourse(course);
            if (courseId == 0) {
                throw new RuntimeException("Inserimento fallito");
            }
        }
        catch (Exception ex){
            throw ex;
        }
        return courseId;
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
        int rowsAffected = courseJdbcRepository.updateCourse(course);
        if (rowsAffected == 0) {
            throw new RuntimeException("Aggiornamento fallito. Corso con ID " + course.getId() + " non trovato.");
        }
    }
    public void deleteCourse(int id) {
        int rowsAffected = courseJdbcRepository.deleteCourse(id);
        if (rowsAffected == 0) {
            throw new RuntimeException("Eliminazione fallita. Corso con ID " + id + " non trovato.");
        }
    }
    public void updateImagePath(String image, int id) {
        int rowsAffected = courseJdbcRepository.updateImagePath(image, id);
        if (rowsAffected == 0) {
            throw new RuntimeException("Aggiornamento dell'immagine fallito. Corso con ID " + id + " non trovato.");
        }
    }
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public String getEmailByCourseIdAndAuthor(int courseId, String author) {
        return courseRepository.findCourseByAuthorAndId(author, courseId).getEmail();
    }
}