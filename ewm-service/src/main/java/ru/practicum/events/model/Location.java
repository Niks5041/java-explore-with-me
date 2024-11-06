package ru.practicum.events.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@Table(name = "LOCATIONS")
@RequiredArgsConstructor
public class Location {

    @Id
    @Column(name = "LOCATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "LAT")
    private Float lat;

    @Column(name = "LON")
    private Float lon;

    @Transient
    private Long likes;
}
