package org.com.izec.controllers;

import org.com.izec.domeins.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;


@Controller
public class GreetingController {




    @PostMapping("/greeting")
    public String greeting2(
            @AuthenticationPrincipal User user,
            @RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);

        if (user.isAdmin()) {
            model.addAttribute("isAdmin", user.isAdmin());
        }


        return "greeting1";
    }

}
