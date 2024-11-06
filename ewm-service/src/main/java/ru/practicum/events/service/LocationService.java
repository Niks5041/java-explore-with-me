package ru.practicum.events.service;

import java.util.List;
import ru.practicum.events.dto.LocationDto;

public interface LocationService {

    LocationDto addLike(long userId, long locationId);

    void deleteLike(long userId, long locationId);

    List<LocationDto> getTop(long userId, Integer count);
}