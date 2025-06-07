package com.beaconfire.quizApp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
        private int userId;
        private String email;
        private String password;
        private String firstname;
        private String lastname;

        private boolean active;
        private boolean admin;
}

