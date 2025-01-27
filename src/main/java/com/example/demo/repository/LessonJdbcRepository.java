package com.example.demo.repository;

import com.example.demo.entity.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class LessonJdbcRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;
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
    public int saveLesson(String title, int courseId) {
        String sql = "INSERT INTO Lessons (Title, CourseId) VALUES (?,?)";
        return jdbcTemplate.update(sql,
                title,courseId);

    }
    public int deleteLesson(int id) {
        String sql = "DELETE FROM Lessons WHERE Id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
