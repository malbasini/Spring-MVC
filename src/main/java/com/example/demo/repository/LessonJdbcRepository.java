package com.example.demo.repository;

import com.example.demo.entity.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class LessonJdbcRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Transactional
    public int updateLesson(Lesson lesson) {
        String sql = "UPDATE Lessons " +
                "SET Title = ?, " +
                "Description = ?, " +
                "Duration = ?, " +
                "RowVersion = ? " +
                "WHERE Id = ?";
        return jdbcTemplate.update(sql,
                lesson.getTitle(),
                lesson.getDescription(),
                lesson.getDuration(),
                lesson.getRowVersion(),
                lesson.getId());
    }
    @Transactional
    public int saveLesson(String title, int courseId) {
        String sql = "INSERT INTO Lessons (Title, CourseId) VALUES (?,?)";
        return jdbcTemplate.update(sql,
                title,courseId);

    }
    @Transactional
    public int deleteLesson(int id) {
        String sql = "DELETE FROM Lessons WHERE Id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
