package ru.stasal.toggl.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author Stanislav Alekminskiy (al.stanislav@gmail.com), 07.02.2018
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class DataItemTitle {

    @JsonProperty("project")
    private String project;

    @JsonProperty("time_entry")
    private String timeEntry;
}
