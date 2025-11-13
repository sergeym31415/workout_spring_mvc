package ru.meshkov.workout.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import org.springframework.web.bind.annotation.*;
import ru.meshkov.workout.dto.AthleteDTO;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.models.DoneExercise;
import ru.meshkov.workout.models.Exercise;
import ru.meshkov.workout.models.TrainingProgram;
import ru.meshkov.workout.security.AthleteDetails;
import ru.meshkov.workout.services.AthletesService;
import ru.meshkov.workout.services.DoneExerciseService;
import ru.meshkov.workout.services.ExercisesService;
import ru.meshkov.workout.services.TrainingProgramService;
import ru.meshkov.workout.utils.AthleteNotFoundException;
import ru.meshkov.workout.validators.AthleteValidator;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/athletes")
public class AthletesController {
    private final AthletesService athletesService;
    private final TrainingProgramService trainingProgramService;

    private final ExercisesService exercisesService;
    private final DoneExerciseService doneExerciseService;
    private final AthleteValidator athleteValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public AthletesController(AthletesService athletesService,
                              TrainingProgramService trainingProgramService,
                              DoneExerciseService doneExerciseService,
                              ExercisesService exercisesService,
                              AthleteValidator athleteValidator,
                              ModelMapper modelMapper) {
        this.athletesService = athletesService;
        this.trainingProgramService = trainingProgramService;
        this.athleteValidator = athleteValidator;
        this.modelMapper = modelMapper;
        this.doneExerciseService = doneExerciseService;
        this.exercisesService = exercisesService;
    }

    @ExceptionHandler(AthleteNotFoundException.class)
    public ResponseEntity<String> handle() {
        return ResponseEntity.internalServerError().body("Athlete not found");
    }

    @GetMapping()
    public String index(Model model) {
        List<Athlete> athletes = athletesService.findAll();
        List<AthleteDTO> athleteDTOs = athletes.stream().map(a -> modelMapper.map(a, AthleteDTO.class)).toList();
        model.addAttribute("athletes", athleteDTOs);
        return "athletes/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
                       @AuthenticationPrincipal AthleteDetails athleteDetails) throws AthleteNotFoundException {
//        if(!athleteDetails.isAdmin() && !athleteDetails.isCurrentUserById(id)) {
//            throw new No
//        }
        Optional<Athlete> athlete = athletesService.findOne(id);
        if (athlete.isEmpty()) {
            throw new AthleteNotFoundException("");
        }
        AthleteDTO athleteDTO = modelMapper.map(athlete, AthleteDTO.class);
        model.addAttribute("athlete", athleteDTO);
        return "athletes/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        Athlete athlete = athletesService.findOne(id).get();
        List<TrainingProgram> trainingPrograms = trainingProgramService.findAll();
        model.addAttribute("training_programs", trainingPrograms);
        AthleteDTO athleteDTO = modelMapper.map(athlete, AthleteDTO.class);
        model.addAttribute("athlete", athleteDTO);
        return "athletes/edit";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, Model model,
                         @ModelAttribute("athlete") @Valid AthleteDTO athleteDTO,
                         BindingResult bindingResult) {
        Athlete athlete = modelMapper.map(athleteDTO, Athlete.class);
        athleteValidator.validate(athlete, bindingResult);
        if (bindingResult.hasErrors()) {
            List<TrainingProgram> trainingPrograms = trainingProgramService.findAll();
            model.addAttribute("training_programs", trainingPrograms);
            return "athletes/edit";
        }
        athletesService.save(athlete);
        return "redirect:/athletes";
    }

    @GetMapping("/new")
    public String create(@ModelAttribute("athlete") Athlete athlete) {
        return "athletes/new";
    }

    @PostMapping()
    public String newAthlete(@ModelAttribute("athlete") @Valid AthleteDTO athleteDTO, BindingResult bindingResult) {
        Athlete athlete = modelMapper.map(athleteDTO, Athlete.class);
        athleteValidator.validate(athlete, bindingResult);
        if ((bindingResult.hasErrors())) {
            return "athletes/new";
        }
        athletesService.save(athlete);
        return "redirect:/athletes";
    }

    @GetMapping("/{athleteId}/done_exercise")
    public String addDoneExercise(@PathVariable("athleteId") int athleteId, Model model,
                                  @ModelAttribute("done_exercise") DoneExercise doneExercise) {
        Optional<Athlete> athlete = athletesService.findOne(athleteId);

        if (athlete.isPresent()) {
            doneExercise.setAthlete(athlete.get());
            model.addAttribute("athlete", athlete.get());
            List<Exercise> exercises = exercisesService.findAll();
            model.addAttribute("exercises", exercises);
            return "done_exercises/new";
        }
        return "";
    }

    @PostMapping("/{athleteId}/done_exercise")
    public String saveDoneExercise(@PathVariable("athleteId") int athleteId,
                                   @ModelAttribute("done_exercise") @Valid DoneExercise doneExercise,
                                   BindingResult bindingResult,
                                   Model model
                                  ) {
        if(bindingResult.hasErrors()) {
            Optional<Athlete> athlete = athletesService.findOne(athleteId);
            doneExercise.setAthlete(athlete.get());
            model.addAttribute("athlete", athlete.get());
            List<Exercise> exercises = exercisesService.findAll();
            model.addAttribute("exercises", exercises);
            return "done_exercises/new";
        }
        doneExercise.setDoneDate(new Date());
        doneExerciseService.save(doneExercise);
        return "redirect:/athletes/{athleteId}";
    }
}
