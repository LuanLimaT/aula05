package com.example.aula03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @GetMapping("/access-denied")
    public ModelAndView login(ModelMap model) {
        return new ModelAndView("autenticacao/access-denied");
    }
}
