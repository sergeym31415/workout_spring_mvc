package ru.meshkov.workout.utils;

import java.math.BigDecimal;

public class UsefulFunctions {
    public static void main(String[] args) {
        System.out.println(round(34.5465463634, 2));

    }
    public static double round(double d, int scale) {
        BigDecimal bigDecimal = BigDecimal.valueOf(d);
        bigDecimal = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }
}
