package com.example.SpringDataJPA.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling requests to the application's index or home page.
 */
@Controller
public class IndexController {

    /**
     * Handles requests to the application's home page.
     *
     * @param model The Spring MVC model for rendering data to the view.
     * @return The name of the HTML template to render, in this case, "index".
     */
    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
}
