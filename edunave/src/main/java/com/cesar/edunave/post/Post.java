package com.cesar.edunave.post;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Post(
    String title,
    String summary,
    String url,
    @JsonProperty("date_published")
    LocalDate datePublished
) {
    

}
