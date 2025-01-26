package com.example.demo.repository;

import com.example.demo.entity.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class CourseJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int updateCourse(Course course) {
        course.setRating(BigDecimal.valueOf(1.0));
        String sql = "UPDATE Courses " +
                "SET Title = ?, " +
                "Description = ?, " +
                "Author = ?, " +
                "Email = ?, " +
                "Rating = ?, " +
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
                course.getRating(),
                course.getFullPriceAmount(),
                "EUR",
                course.getCurrentPriceAmount(),
                "EUR",
                course.getRowVersion(),
                course.getStatus(),
                course.getId());
    }
    public int saveCourse(Course course) {
        String sql = "INSERT INTO Courses (title) VALUES (?)";
        return jdbcTemplate.update(sql, course.getTitle());
    }
    public int deleteCourse(int id) {
        String sql = "DELETE FROM Courses WHERE Id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int updateImagePath(String image, int id) {
        String sql = "UPDATE Courses " +
                "SET ImagePath = ? " +
                "WHERE Id = ?";
        return jdbcTemplate.update(sql,
                image,
                id);
    }

}