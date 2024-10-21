package ru.practicum.events.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.events.model.Events;


public interface EventsRepository extends JpaRepository<Events, Integer> {

}


