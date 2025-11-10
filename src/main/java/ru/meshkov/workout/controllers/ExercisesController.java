package ru.meshkov.workout.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.meshkov.workout.dto.ExerciseDTO;
import ru.meshkov.workout.models.Exercise;
import ru.meshkov.workout.services.ExercisesService;

import java.util.List;

@Controller
@RequestMapping("/exercises")
public class ExercisesController {
    private final ExercisesService exercisesService;
    private final ModelMapper modelMapper;

    @Autowired
    public ExercisesController(ExercisesService exercisesService, ModelMapper modelMapper) {
        this.exercisesService = exercisesService;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public String index(Model model) {
        List<Exercise> exercises = exercisesService.findAll();
        List<ExerciseDTO> exerciseDTOs = exercises.stream().map(e -> modelMapper.map(e, ExerciseDTO.class)).toList();
        model.addAttribute("exercises", exerciseDTOs);
        return "exercises/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Exercise exercise = exercisesService.findOne(id);
        ExerciseDTO exerciseDTO = modelMapper.map(exercise, ExerciseDTO.class);
        model.addAttribute("exercise", exerciseDTO);
        return "exercises/show";
    }

    @PostMapping()
    public String newExercise(@ModelAttribute("exercise") @Valid ExerciseDTO exerciseDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "exercises/new";
        }
        Exercise exercise = modelMapper.map(exerciseDTO, Exercise.class);
        exercisesService.save(exercise);
        return "redirect:/exercises";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("exercise") Exercise exercise) {
        return "exercises/new";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Exercise exercise = exercisesService.findOne(id);
        ExerciseDTO exerciseDTO = modelMapper.map(exercise, ExerciseDTO.class);
        model.addAttribute("exercise", exerciseDTO);
        return "exercises/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute @Valid ExerciseDTO exerciseDTO,
                         BindingResult bindingResult) {
        Exercise exercise = modelMapper.map(exerciseDTO, Exercise.class);
        if (bindingResult.hasErrors()) {
            return "exercises/edit";
        }
        exercisesService.save(exercise);
        return "redirect:/exercises";
    }

}
