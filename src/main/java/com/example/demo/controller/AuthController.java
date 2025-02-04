package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage()
    {
        return "redirect:/login?logout=true"; // restituisce la JSP login.jsp
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model)
    {
        User userForm = new User();
        model.addAttribute("userForm",userForm);
        return "security/login"; // restituisce la JSP login.jsp
    }
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(@ModelAttribute("userForm") User userForm,
                          Model model) {
        boolean isAuthenticated;
        User user = null;
        try {
            user = userService.login(userForm.getUsername());
        } catch (Exception e) {
            model.addAttribute("errore","I dati inseriti non sono corretti");
            return "security/login"; // restituisce la JSP login.jsp
        }
        if(user.getUsername().equals(userForm.getUsername()) && passwordEncoder.matches(userForm.getPassword(), user.getPassword()))
        {
            isAuthenticated = true;
            model.addAttribute("username",userForm.getUsername());
            model.addAttribute("isAuthenticated",isAuthenticated);
            return "redirect:/";
        }
        model.addAttribute("errore","I dati inseriti non sono corretti");
        return "security/login"; // restituisce la JSP login.jsp
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterPage(Model model) {
        return "security/register"; // restituisce la JSP register.jsp
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public String doRegister(
            @RequestParam("username") String username,
            @RequestParam("fullname") String fullname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("roleId") String role,
            Model model) {
            //Controlli

            if (fullname.isEmpty()) {
                model.addAttribute("errore", "Valorizzare il fullname");
                return "security/register";
            }

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                model.addAttribute("errore", "Valorizzare username, password ed email");
                return "security/register";
            }
            if (!role.equals("ROLE_STUDENT") && (!role.equals("ROLE_TEACHER"))) {
                model.addAttribute("errore", "Valorizzare il ruolo");
                return "security/register";
            }
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                model.addAttribute("errore", "Email non valida");
                return "security/register";
            }
            if (password.length() < 5) {
                model.addAttribute("errore", "Password non valida. La lunghezza deve essere di almeno cinque caratteri");
                return "security/register";
            }
            // Qui invochiamo il servizio che crea l'utente e assegna il ruolo
            try {
                userService.registerNewUser(username,fullname, password, email, role);
                model.addAttribute("message", "Registrazione effettuata con successo. Ora fai il login!");
                return "security/register";
            } catch (Exception e) {
                model.addAttribute("errore", "Errore nella registrazione: " + e.getMessage());
                return "security/register";
            }
        }
}
