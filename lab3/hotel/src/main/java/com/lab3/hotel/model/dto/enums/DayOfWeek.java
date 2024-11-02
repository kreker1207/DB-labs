package com.lab3.hotel.model.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum DayOfWeek {
    MONDAY(1, "Понеділок"),
    TUESDAY(2, "Вівторок"),
    WEDNESDAY(3, "Середа"),
    THURSDAY(4, "Четвер"),
    FRIDAY(5, "П'ятниця"),
    SATURDAY(6, "Субота"),
    SUNDAY(7, "Неділя");

    private final long dayId;
    private final String name;

    public static String getDayOfWeekById(long dayId) {
        return Arrays.stream(DayOfWeek.values()).filter(day -> day.getDayId() == dayId).map(DayOfWeek::getName).findFirst().orElse("");
    }
}
