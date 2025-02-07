package com.example.demo.controller;

import com.example.demo.service.CaptchaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FormController {

    @Autowired
    private CaptchaValidator captchaValidator;

    @PostMapping("{redirectSuccess}/{redirectError}/submitForm")
    public String submitForm(@RequestParam("g-recaptcha-response") String captchaResponse,
                             Model model,
                             @PathVariable("redirectSuccess") String redirectSuccess,
                             @PathVariable("redirectError") String redirectError)
    {

        boolean isCaptchaValid = captchaValidator.verifyCaptcha(captchaResponse);
        if (!isCaptchaValid) {
            model.addAttribute("error", "Captcha non valido. Riprova.");
            return redirectError; // Torna alla pagina del form
        }
        // Continua con l'elaborazione del form
        model.addAttribute("success", "Form inviato con successo!");
        return redirectSuccess; // Vai alla pagina di successo
    }

}
