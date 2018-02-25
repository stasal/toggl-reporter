package ru.stasal.toggl.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Stanislav Alekminskiy (al.stanislav@gmail.com), 07.02.2018
 */
public class WeeklyReport {
    private List<ReportRow> rows = new ArrayList<>();

    public void addRow(ReportRow row) {
        Optional<ReportRow> existingRow = findExistingRow(row);

        if (existingRow.isPresent()) {
            existingRow.get().addThisWeek(row.getCompletedThisWeek());
            existingRow.get().addTotal(row.getCompletedThisWeek());
        } else {
            rows.add(row);
        }
    }

    public void addPreviousPeriodRow(ReportRow row) {
        findExistingRow(row).ifPresent(reportRow -> reportRow.addTotal(row.getCompletedTotal()));
    }

    private Optional<ReportRow> findExistingRow(ReportRow row) {
        return rows.stream().filter(reportRow -> reportRow.getProject().equals(row.getProject())
                    && reportRow.getIssue().equals(row.getIssue())
                    && reportRow.getActivity().equals(row.getActivity()))
                    .findFirst();
    }
}
