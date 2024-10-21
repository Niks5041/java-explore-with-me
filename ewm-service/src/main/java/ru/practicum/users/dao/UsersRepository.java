package ru.practicum.users.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.users.model.User;

public interface UsersRepository extends JpaRepository<User, Integer> {


}


