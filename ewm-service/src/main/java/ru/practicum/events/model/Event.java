package ru.practicum.events.model;

import jakarta.persistence.*;
import lombok.*;
import ru.practicum.categories.model.Category;
import ru.practicum.events.model.enums.EventState;
import ru.practicum.users.model.User;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Table(name = "EVENTS")
public class Event {

    @Id
    @Column(name = "EVENT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "ANNOTATION")
    private String annotation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @Transient
    private Long confirmedRequests;

    @Column(name = "CREATED_ON")
    private LocalDateTime createOn;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "EVENT_DATE")
    private LocalDateTime eventDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @Column(name = "PAID")
    private boolean paid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INITIATOR_ID")
    private User initiator;

    @Column(name = "PARTICIPANT_LIMIT")
    private int participantLimit;

    @Column(name = "PUBLISHED_ON")
    private LocalDateTime publishedOn;

    @Column(name = "REQUEST_MODERATION")
    private boolean requestModeration;

    @Column(name = "STATE")
    @Enumerated(EnumType.STRING)
    private EventState state;

    @Column(name = "TITLE")
    private String title;

    @Transient
    private Long views;

    @Transient
    private Long likes;
}
