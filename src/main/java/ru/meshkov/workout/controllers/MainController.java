package ru.meshkov.workout.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meshkov.workout.security.AthleteDetails;

import java.util.List;

@Controller
@RequestMapping
public class MainController {

    @GetMapping
    public String mainPage(@AuthenticationPrincipal AthleteDetails athleteDetails, Model model) {
        String username = athleteDetails.getUsername();
        System.out.println(username);
        System.out.println(athleteDetails.getAuthorities());
        boolean isAdmin = athleteDetails.isAdmin();
        System.out.println("isAdmin:" + isAdmin);

        model.addAttribute("username", username);
        model.addAttribute("autorities", athleteDetails.getAuthorities());
        model.addAttribute("idAthlete", athleteDetails.getId());
        model.addAttribute("isAdmin", isAdmin);
        return "main_page";
    }
}
