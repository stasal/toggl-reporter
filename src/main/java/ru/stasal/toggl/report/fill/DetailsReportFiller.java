package ru.stasal.toggl.report.fill;

import ru.stasal.toggl.api.TaskDetails;
import ru.stasal.toggl.report.ReportRow;
import ru.stasal.toggl.report.WeeklyReport;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;

/**
 * @author Stanislav Alekminskiy (al.stanislav@gmail.com), 07.02.2018
 */
public class DetailsReportFiller implements ReportFiller {

    private static final String ACTIVITY_TAG_START = "Activity:";
    private static final String DEFAULT_PROJECT = "DMD";
    private static final double DEFAULT_ESTIMATE = 0D;
    private static final String DEFAULT_STATE = "Done";
    private final List<TaskDetails> tasks;

    public DetailsReportFiller(List<TaskDetails> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void fillReport(final WeeklyReport report) {
        List<TaskDetails> tasksToProcess = tasks.stream()
                .filter(task -> !task.getTags().isEmpty())
                .filter(task -> {
                    List<String> tags = task.getTags();
                    return tags.stream().anyMatch(tag -> tag.startsWith(ACTIVITY_TAG_START));
                })
                .sorted(comparingLong(TaskDetails::getPid))
                .collect(Collectors.toList());

        setDefaultProject(tasksToProcess);

        tasksToProcess.forEach(task -> {
            ReportRow row = new ReportRow(task.getProject(), task.getDescription(),
                    getActivity(task), DEFAULT_ESTIMATE, DEFAULT_STATE, task.getDur(), task.getDur());
            report.addRow(row);
        });
    }

    private String getActivity(TaskDetails task) {
        String tag = task.getTags().get(0);
        return tag.substring(ACTIVITY_TAG_START.length());
    }

    private static void setDefaultProject(final List<TaskDetails> tasksToProcess) {
        tasksToProcess.forEach(task -> {
            if (task.getProject() == null) {
                task.setProject(DEFAULT_PROJECT);
            } else if (task.getProject().equals(DEFAULT_PROJECT)) {
                task.setPid(0);
            }
        });
    }
}
