package com.example.demo.controller;
import com.example.demo.entity.Course;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeController {

    private final CourseService courseService;

    public HomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String showHomePage(Model model, org.springframework.security.core.Authentication authentication) {
        // Recupera i corsi
        List<Course> topRatedCourses = courseService.getTopRatedCourses();
        List<Course> newestCourses = courseService.getNewestCourses();
        // Aggiunge i dati al modello
        model.addAttribute("topRatedCourses", topRatedCourses);
        model.addAttribute("newestCourses", newestCourses);
        // Ritorna la vista JSP
        return "home";
    }
}
