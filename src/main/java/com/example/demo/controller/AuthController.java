package com.example.demo.controller;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.AdminService;
import com.example.demo.service.CaptchaValidator;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
    private CaptchaValidator captchaValidator;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AdminService adminService;


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
                          Model model, Authentication authentication) {
        boolean isAuthenticated;
        User user = null;
        try {
            user = userService.login(userForm.getUsername().trim(),userForm.getPassword().trim());
        } catch (Exception e) {
            model.addAttribute("errore","I dati inseriti non sono corretti");
            return "security/login"; // restituisce la JSP login.jsp
        }
        boolean isAdmin = true;
        model.addAttribute("isAdmin", isAdmin);
        isAuthenticated = true;
        model.addAttribute("username",userForm.getUsername());
        model.addAttribute("isAuthenticated",isAuthenticated);
        return "redirect:/";
    }


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterPage(Model model) {
        model.addAttribute("sitetkey", captchaValidator.getSiteKey());
        return "security/register"; // restituisce la JSP register.jsp
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public String doRegister(
            @RequestParam("username") String username,
            @RequestParam("fullname") String fullname,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("roleId") String role,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            Model model) {


            model.addAttribute("username", username);
            model.addAttribute("fullname", fullname);
            model.addAttribute("email", email);
            model.addAttribute("password", password);
            model.addAttribute("role", role);
            model.addAttribute("sitetkey", captchaValidator.getSiteKey());
            this.valorizzaCampi(model, username, fullname, email, password, role);
            //Controlli
            if (fullname.isEmpty()) {
                model.addAttribute("errore", "Valorizzare il fullname");
                return "security/register";
            }
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                model.addAttribute("errore", "Valorizzare username, password ed email");
                return "security/register";
            }
            if (!role.equals("ROLE_STUDENT") && (!role.equals("ROLE_EDITOR") && !role.equals("ROLE_ADMIN"))) {
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
            boolean isCaptchaValid = captchaValidator.verifyCaptcha(captchaResponse);
            if (!isCaptchaValid) {
                model.addAttribute("error", "Captcha non valido. Riprova.");
                this.valorizzaCampi(model, username, fullname, email, password, role);
                return "security/register";// Torna alla pagina del form
            }
            // Qui invochiamo il servizio che crea l'utente e assegna il ruolo
            try {
                User user = new User();
                user.setUsername(username);
                user.setFullname(fullname);
                user.setPassword(passwordEncoder.encode(password.trim()));
                System.out.println(user.getPassword());
                user.setEmail(email);
                user.setEnabled(true);
                Role r = roleRepository.findByName(role);
                user.setRoles(java.util.Collections.singleton(r));
                int id = userService.registerNewUser(user.getUsername(),user.getFullname(),user.getPassword(),user.getEmail(),role);
                //VERIFICO CHE CI SIA UN SOLO AMMINISTRATORE
                if(role.equals("ROLE_ADMIN")) {
                    Admin a = adminRepository.findByRole("ROLE_ADMIN");
                    if (a != null) {
                        model.addAttribute("errore", "Ci puo essere un solo amministratore");
                        this.valorizzaCampi(model, username, fullname, email, password, role);
                        return "security/register";
                    }
                    else {
                        //AGGIUNGO LA RIGA ALLA TABELLA ROLE
                        Admin admin = new Admin();
                        admin.setFullname(user.getFullname());
                        admin.setEmail(user.getEmail());
                        admin.setUserId(id);
                        admin.setRole(role);
                        admin.setRevoke(0);
                        adminService.saveRole(admin);
                    }
                }
                if(role.equals("ROLE_EDITOR")){
                    //AGGIUNGO LA RIGA ALLA TABELLA ROLE
                    Admin a = new Admin();
                    a.setFullname(user.getFullname());
                    a.setEmail(user.getEmail());
                    a.setUserId(id);
                    a.setRole(role);
                    a.setRevoke(0);
                    adminService.saveRole(a);
                }
                model.addAttribute("message", "Registrazione effettuata con successo. Ora fai il login!");
                return "security/register";
            } catch (Exception e) {
                model.addAttribute("errore", "Errore nella registrazione: " + e.getMessage());
                return "security/register";
            }
        }
    private void valorizzaCampi(Model model, String username, String fullname, String email, String password, String role) {
        model.addAttribute("username", username);
        model.addAttribute("fullname", fullname);
        model.addAttribute("email", email);
        model.addAttribute("password", password);
        model.addAttribute("role", role);
    }
}
