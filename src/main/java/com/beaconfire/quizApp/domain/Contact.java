package com.beaconfire.quizApp.domain;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contact {
    private int contactId;
    private String subject;
    private String message;
    private String email;
    private Timestamp time;
}
