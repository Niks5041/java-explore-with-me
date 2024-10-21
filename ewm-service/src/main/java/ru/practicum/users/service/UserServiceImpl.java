package ru.practicum.users.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.users.dao.RequestRepository;
import ru.practicum.users.dao.UsersRepository;
import ru.practicum.users.dto.NewUserRequestDto;
import ru.practicum.users.dto.UserDto;
import ru.practicum.users.dto.mapper.UserMapper;
import ru.practicum.users.dto.requests.RequestDto;
import ru.practicum.users.model.Request;

import java.util.Collection;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UsersRepository usersRepository;
    RequestRepository requestRepository;

    @Override
    public void deleteUser(Integer userId) {
        log.info("Получен запрос в сервис Users на удаление пользователя с ID: {}", userId);
        usersRepository.deleteById(userId);
        log.info("Получен ответ в сервисе Users об удалении пользователя из базы данынх с ID: {}", userId);
    }

    @Override
    public Collection<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        log.info("Получен запрос в сервис Users на получение списка пользователей с параметрами ids = {}, from = {}, size = {}",
                ids, from, size);
        Collection<UserDto> users = usersRepository.findAllById(ids)
                .stream()
                .map(UserMapper.INSTANCE::toUserDto)
                .toList();
        log.info("Отправлен ответ из сервиса Users с списком из базы данных пользователей: {}", users);
        return users;
    }

    @Override
    public UserDto saveUser(NewUserRequestDto  newUserRequestDto) {
        log.info("Получен запрос в сервис Users на сохранение пользователя: {}", newUserRequestDto);
        UserDto userDto = UserMapper.INSTANCE.toUserDto(usersRepository.save(UserMapper.INSTANCE.toUser(newUserRequestDto)));
        log.info("Отправлен ответ из сервиса Users о сохранении в базе данных юзера: " + userDto);
        return userDto;
  }

    @Override
    public Collection<RequestDto> getUserRequests(Integer userId) {
        log.info("Получен запрос в сервис Users на получение заявок пользователя с ID: {}", userId);
        Collection<RequestDto> requests = requestRepository.findById(userId)
                .stream()
                .map(UserMapper.INSTANCE::toRequestDto)
                .toList();
        log.info("Отправлен ответ с заявками пользователя из базы данных: {}", requests);
        return requests;
    }

    @Override
    public RequestDto saveUserRequest(Integer userId, Integer eventId) {
        log.info("Получен запрос в сервис Users на сохранение заявки пользователя с ID: {} и параметром eventId = {}",
                userId, eventId);
        Request request = new Request();
        request.setEvent(eventId);
        request.setRequester(userId);
        log.info("Отправлен ответ с сохраненной заявкой пользователя из базы данных: {}", request);
        return UserMapper.INSTANCE.toRequestDto(requestRepository.save(request));

    }

    @Override
    public RequestDto cancelUserRequest(Integer userId, Integer requestId) {
        log.info("Получен запрос в сервис Users на отмену заявки пользователя с ID: {} и параметром requestId = {}",
                userId, requestId);
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена"));
        requestRepository.delete(request);
        log.info("Отправлен ответ с удаленной заявкой пользователя из базы данных: {}", request);
        return UserMapper.INSTANCE.toRequestDto(request);

    }


}
