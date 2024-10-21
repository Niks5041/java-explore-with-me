package ru.practicum.compilations.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilations.model.Compilation;


public interface CompilationsRepository extends JpaRepository<Compilation, Integer> {

}


