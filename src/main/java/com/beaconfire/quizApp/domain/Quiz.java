package com.beaconfire.quizApp.domain;

import lombok.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Quiz {
    private int quizId;
    private int userId;
    private int categoryId;
    private String name;
    private LocalDateTime timeStart;
    private LocalDateTime timeEnd;
    private boolean isCompleted;
    private String userFullName;
    private String categoryName;

}
