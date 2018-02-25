package ru.stasal.toggl.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author Stanislav Alekminskiy (al.stanislav@gmail.com), 07.02.2018
 */
@Data
public class SummaryReport {

    @JsonProperty("total_grand")
    private long totalGrand;

    @JsonProperty("data")
    private List<ResponseDataItem> data;
}
