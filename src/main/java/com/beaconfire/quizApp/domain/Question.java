package com.beaconfire.quizApp.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Question {
    private int questionId;
    private int categoryId;
    private Integer userChoiceId;
    private String description;
    private Boolean isActive;
    private List<Choice> choices;
    private String categoryName;
    public Boolean getActive() {
        return isActive;
    }
}
