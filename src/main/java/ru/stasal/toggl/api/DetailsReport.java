package ru.stasal.toggl.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Stanislav Alekminskiy (al.stanislav@gmail.com), 07.02.2018
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailsReport {

    @JsonProperty("total_grand")
    private long totalGrand;

    @JsonProperty("total_count")
    private int totalCount;

    @JsonProperty("per_page")
    private int perPage;

    @JsonProperty("data")
    private List<TaskDetails> tasks;
}
