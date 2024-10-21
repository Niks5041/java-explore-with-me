package ru.practicum.events.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.categories.model.CategoryDto;
import ru.practicum.users.model.User;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
public class Events {
    @Column
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryDto category;

    @Column
    private Integer confirmedRequests;

    @Column
    private LocalDateTime eventDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User initiator;

    @Column
    private Boolean paid;

    @Column
    private String title;

    @Column
    private Integer views;
}
