package ru.firesin.feature.jpa;

import jakarta.persistence.*;
import lombok.*;
import ru.firesin.feature.jpa.enums.UserState;

/**
 * Author:    firesin
 * Date:      23.10.2023
 */

@Setter
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "bot_user")
public class BotUser {
    @Id
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    @Enumerated(EnumType.STRING)
    private UserState userState;
}


