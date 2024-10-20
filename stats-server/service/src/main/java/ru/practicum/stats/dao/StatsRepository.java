package ru.practicum.stats.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.practicum.stats.model.Stats;



public interface StatsRepository extends JpaRepository<Stats, Integer> {

}
