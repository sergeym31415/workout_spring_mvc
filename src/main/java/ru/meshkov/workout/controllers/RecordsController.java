package ru.meshkov.workout.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.meshkov.workout.models.Record;
import ru.meshkov.workout.services.RecordsService;

import java.util.List;

@Controller
@RequestMapping("/records")
public class RecordsController {
    private final RecordsService recordsService;

    @Autowired
    public RecordsController(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    @GetMapping
    public String index(Model model) {
        List<Record> records = recordsService.findAll();
        model.addAttribute("records", records);
        return "records/index";
    }
}
