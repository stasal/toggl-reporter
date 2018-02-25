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
class ResponseDataItem {

    @JsonProperty("id")
    private long id;

    @JsonProperty("title")
    private DataItemTitle title;

    @JsonProperty("items")
    private List<ResponseDataItem> items;

    @JsonProperty("time")
    private long time;
}
