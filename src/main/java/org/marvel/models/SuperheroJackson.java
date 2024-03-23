package org.marvel.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "fullName",
        "birthDate",
        "city",
        "mainSkill",
        "gender",
        "phone"
})

public class SuperheroJackson {
    @JsonProperty("id")
    private int id;
    @JsonProperty("fullName")
    private String name;
    @JsonProperty("birthDate")
    private String birthDate;
    @JsonProperty("city")
    private String city;
    @JsonProperty("mainSkill")
    private String mainSkill;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("phone")
    private Object phone;
}
