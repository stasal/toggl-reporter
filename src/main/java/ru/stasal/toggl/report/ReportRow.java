package ru.stasal.toggl.report;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Stanislav Alekminskiy (al.stanislav@gmail.com), 07.02.2018
 */
@Data
@AllArgsConstructor
public class ReportRow {
    private final String project;
    private final String issue;
    private final String activity;
    private final double estimate;
    private final String state;
    private double completedThisWeek;
    private double completedTotal;

    void addThisWeek(double thisWeek) {
        completedThisWeek += thisWeek;
    }

    void addTotal(double anotherWeek) {
        completedTotal += anotherWeek;
    }
}
