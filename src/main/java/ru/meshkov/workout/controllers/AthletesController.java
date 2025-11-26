package ru.meshkov.workout.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


import org.springframework.web.bind.annotation.*;
import ru.meshkov.workout.dto.AthleteDTO;
import ru.meshkov.workout.exceptions.DoneExerciseNotFoundException;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.models.DoneExercise;
import ru.meshkov.workout.models.Exercise;
import ru.meshkov.workout.models.Record;
import ru.meshkov.workout.models.TrainingProgram;
import ru.meshkov.workout.security.AthleteDetails;
import ru.meshkov.workout.services.*;
import ru.meshkov.workout.exceptions.AthleteNotFoundException;
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
    private final RecordsService recordsService;
    private final AthleteValidator athleteValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public AthletesController(AthletesService athletesService,
                              TrainingProgramService trainingProgramService,
                              DoneExerciseService doneExerciseService,
                              ExercisesService exercisesService,
                              AthleteValidator athleteValidator,
                              RecordsService recordsService,
                              ModelMapper modelMapper) {
        this.athletesService = athletesService;
        this.trainingProgramService = trainingProgramService;
        this.athleteValidator = athleteValidator;
        this.modelMapper = modelMapper;
        this.doneExerciseService = doneExerciseService;
        this.exercisesService = exercisesService;
        this.recordsService = recordsService;
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
        if (bindingResult.hasErrors()) {
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

    @GetMapping("/{athleteId}/done_exercises")
    public String showDoneExercises(@PathVariable("athleteId") int athleteId, Model model) throws AthleteNotFoundException {
        Optional<Athlete> athlete = athletesService.findOne(athleteId);
        if(athlete.isEmpty()) {
            throw new AthleteNotFoundException("");
        }
        List<DoneExercise> doneExerciseList = doneExerciseService.findAllByAthlete(athlete.get());
        model.addAttribute("doneExerciseList", doneExerciseList);
        model.addAttribute("athlete", athlete.get());
        return "done_exercises/index_by_athlete";
    }

    @GetMapping("/{athleteId}/done_exercises/{id}")
    public String showDoneExercise(Model model, @PathVariable("id") int id, @PathVariable("athleteId") int athleteId) throws DoneExerciseNotFoundException {
        Optional<DoneExercise> optionalDoneExercise = doneExerciseService.findById(id, athleteId);
        if(optionalDoneExercise.isPresent()) {
            DoneExercise doneExercise = optionalDoneExercise.get();
            model.addAttribute("doneExercise", doneExercise);
            return "done_exercises/show";
        } else throw new DoneExerciseNotFoundException("");
    }

    @GetMapping("/{athleteId}/records")
    public String showPersonalRecords(@PathVariable("athleteId") int athleteId, Model model) throws AthleteNotFoundException {
        Optional<Athlete> athlete = athletesService.findOne(athleteId);
        if(athlete.isEmpty()) {
            throw new AthleteNotFoundException("");
        }
        List<Record> records = recordsService.findAllByAthlete(athlete.get());
        model.addAttribute("records", records);
        model.addAttribute("athlete", athlete.get());
        return "records/index_by_athlete";
    }

    @GetMapping("/{athleteId}/records/{id}")
    public String showPersonalRecord(Model model, @PathVariable("id") int id, @PathVariable("athleteId") int athleteId) throws DoneExerciseNotFoundException {
        Optional<Record> optionalRecord = recordsService.findById(id);
        if(optionalRecord.isPresent()) {
            Record record = optionalRecord.get();
            model.addAttribute("record", record);
            return "records/show";
        } else throw new DoneExerciseNotFoundException("");
    }
}
