package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Repository
public class CourseJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Transactional
    public int updateCourse(Course course) {

        String sql = "UPDATE Courses " +
                "SET Title = ?, " +
                "Description = ?, " +
                "Author = ?, " +
                "Email = ?, " +
                "FullPrice_Amount = ?, " +
                "FullPrice_Currency = ?, " +
                "CurrentPrice_Amount = ?, " +
                "CurrentPrice_Currency = ?, " +
                "RowVersion = ?, " +
                "Status = ? " +
                "WHERE Id = ?";

        return jdbcTemplate.update(sql,
                course.getTitle(),
                course.getDescription(),
                course.getAuthor(),
                course.getEmail(),
                course.getFullPriceAmount(),
                "EUR",
                course.getCurrentPriceAmount(),
                "EUR",
                course.getRowVersion(),
                course.getStatus(),
                course.getId());
    }
    @Transactional
    public int saveCourse(Course course) {
        String sql = "INSERT INTO Courses (idUser, title) VALUES (?,?)";
        jdbcTemplate.update(sql, course.getUserOwner().getId(), course.getTitle());
        Integer courseId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        return courseId;
    }

    @Transactional
    public int deleteCourse(int id) {
        String sql = "DELETE FROM Lessons WHERE CourseId = ?";
        jdbcTemplate.update(sql, id);
        sql = "DELETE FROM Courses WHERE Id = ?";
        return jdbcTemplate.update(sql, id);
    }
    @Transactional
    public int updateImagePath(String image, int id) {
        String sql = "UPDATE Courses " +
                "SET ImagePath = ? " +
                "WHERE Id = ?";
        return jdbcTemplate.update(sql,
                image,
                id);
    }

}