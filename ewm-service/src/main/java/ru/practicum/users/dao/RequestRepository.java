package ru.practicum.users.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.users.model.Request;

public interface RequestRepository extends JpaRepository<Request, Integer> {
}
