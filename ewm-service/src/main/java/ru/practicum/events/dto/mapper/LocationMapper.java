package ru.practicum.events.dto.mapper;

import org.mapstruct.Mapper;
import ru.practicum.events.dto.LocationDto;
import ru.practicum.events.model.Location;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationDto locationToLocationDto(Location location);
}