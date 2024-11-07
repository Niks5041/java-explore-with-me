package ru.practicum.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.events.dao.LocationRepository;
import ru.practicum.events.dto.LocationDto;
import ru.practicum.events.dto.mapper.LocationMapper;
import ru.practicum.events.model.Location;
import ru.practicum.exceptions.NotFoundException;
import ru.practicum.users.dao.UserRepository;
import ru.practicum.users.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final LocationMapper locationMapper;

    @Override
    public LocationDto addLike(long userId, long locationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id " + userId + " не найден"));
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Локация id " + locationId + " не найдена"));
        locationRepository.addLike(userId, locationId);
        return locationMapper.locationToLocationDto(locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Локация id " + locationId + " не найдена")));
    }

    @Override
    public void deleteLike(long userId, long locationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь id " + userId + " не найден"));
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new NotFoundException("Локация " + locationId + " не найдена"));
        if (locationRepository.checkLikeExisting(userId, locationId)) {
            locationRepository.deleteLike(userId, locationId);
        } else {
            throw new NotFoundException("Лайк для локации: " + locationId + " пользователем: " + user.getId() + " не установлен");
        }
    }

    @Override
    public List<LocationDto> getTop(long userId, Integer count) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователи id " + userId + " не найден"));

        List<Location> locationTopList = locationRepository.findTop(count);

        for (Location location : locationTopList) {
            location.setLikes(locationRepository.countLikesByLocationId(location.getId()));
        }

        return locationTopList.stream()
                .map(locationMapper::locationToLocationDto)
                .toList();
    }
}