package ru.meshkov.workout;

import ru.meshkov.workout.utils.DayOfWeekRus;

import java.time.DayOfWeek;

public class MyClass {
    public static void main(String[] args) {
        int number = 6;

        System.out.println(DayOfWeek.of(number).name());
        DayOfWeekRus dayOfWeekRus = DayOfWeekRus.valueOf(DayOfWeek.of(number).name());
        System.out.println(dayOfWeekRus.getTitle());

        System.out.println("СУББОТА");

    }
}
