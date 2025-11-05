package ru.meshkov.workout.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.meshkov.workout.models.Athlete;
import ru.meshkov.workout.services.AthletesService;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AthleteValidator implements Validator {
    private final AthletesService athletesService;

    @Autowired
    public AthleteValidator(AthletesService athletesService) {
        this.athletesService = athletesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Athlete.class.equals(clazz);
    }

    private boolean isPasswordOk(Athlete athlete) {
        String password = athlete.getPassword();
        if (password.length() < 8)
            return false;
        if (!password.matches(".*[0-9]+.*") || !password.matches(".*[a-zA-Z]+.*"))
            return false;
        return true;
    }

    private boolean isBirthDateOk(Athlete athlete) {
        Date birthDate = athlete.getBirthDate();
        return true;
    }

    private boolean isLoginOk(Athlete athlete) {
        String login = athlete.getLogin();
        if (!login.matches("\\w{3,}")) {
            return false;
        }
        return true;
    }

    private boolean isBodyWeightOk(Athlete athlete) {
        Double weight = athlete.getBodyWeight();
        if (weight == null) {
            return false;
        }
        if (weight <= 0 || weight > 500) {
            return false;
        }
        return true;
    }

    private boolean isBodyHeightOk(Athlete athlete) {
        Integer height = athlete.getBodyHeight();
        if (height == null) {
            return false;
        }
        if (height < 50 || height > 300) {
            return false;
        }
        return true;
    }

    private boolean isNameOk(Athlete athlete) {
        athlete.setName(athlete.getName().trim());
        String name = athlete.getName();
        String regex = "[a-zA-Zа-яА-ЯёЁ ]{2,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        if(!matcher.matches()) {
            return false;
        }
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        Athlete athlete = (Athlete) target;
        if (!isLoginOk(athlete)) {
            errors.rejectValue("login", "login_is_not_ok");
        } else {
            Optional<Athlete> athleteInBd = athletesService.findByLogin(athlete.getLogin());
            if (athleteInBd.isPresent() && athlete.getId() != athleteInBd.get().getId()) {
                errors.rejectValue("login", "is_busy");
            }
        }
        if (!isNameOk(athlete)) {
            errors.rejectValue("name", "name_is_not_ok");
        }
        if (!isPasswordOk(athlete)) {
            errors.rejectValue("password", "password_is_not_ok");
        }
        if (!isBirthDateOk(athlete)) {
            errors.rejectValue("birthDate", "birthDate_is_not_ok");
        }
        if (!isBodyWeightOk(athlete)) {
            errors.rejectValue("bodyWeight", "bodyWeight_is_not_ok");
        }
        if (!isBodyHeightOk(athlete)) {
            errors.rejectValue("bodyHeight", "bodyHeight_is_not_ok");
        }


    }


}
