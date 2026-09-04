package org.example.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicHomeController {

    @RequestMapping("/")
    public String getDefaultPage() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String getHomePage() {
        return "public/home-page";
    }
}
