package ru.stasal.toggl.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Stanislav Alekminskiy (al.stanislav@gmail.com), 07.02.2018
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskDetails {

    @JsonProperty("id")
    private long id;

    @JsonProperty("pid")
    private long pid;

    @JsonProperty("tid")
    private long tid;

    @JsonProperty("uid")
    private long uid;

    @JsonProperty("description")
    private String description;

    @JsonProperty("start")
    private LocalDateTime start;

    @JsonProperty("end")
    private LocalDateTime end;

    @JsonProperty("updated")
    private LocalDateTime updated;

    @JsonProperty("dur")
    private long dur;

    @JsonProperty("user")
    private String user;

    @JsonProperty("use_stop")
    private boolean useStop;

    @JsonProperty("client")
    private String client;

    @JsonProperty("project")
    private String project;

    @JsonProperty("task")
    private String task;

    @JsonProperty("billable")
    private long billable;

    @JsonProperty("is_billable")
    private boolean isBillable;

    @JsonProperty("cur")
    private String cur;

    @JsonProperty("tags")
    private List<String> tags;
}
