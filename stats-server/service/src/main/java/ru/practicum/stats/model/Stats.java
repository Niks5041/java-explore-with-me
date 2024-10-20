package ru.practicum.stats.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Entity
@Table(name = "stats")
public class Stats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String app;
    @Column
    private String uri;
    @Column
    private Collection<Integer> hits = new ArrayList<>();
}
