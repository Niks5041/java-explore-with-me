package ru.practicum.users.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "email"})
public class User {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "EMAIL",nullable = false)
    private String email;

}
