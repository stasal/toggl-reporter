package ru.stasal.toggl.report.fill;

import ru.stasal.toggl.report.WeeklyReport;

/**
 * @author Stanislav Alekminskiy (al.stanislav@gmail.com), 07.02.2018
 */
public interface ReportFiller {
    void fillReport(WeeklyReport report);
}
