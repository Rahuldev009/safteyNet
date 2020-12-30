package com.safetynet.safetynet.utils;

import java.time.LocalDate;
import java.time.Period;

public class Utilities {
    public static int calculateAge(LocalDate birthDate, LocalDate currentDate) {
        return Period.between(birthDate, currentDate).getYears();
    }
}