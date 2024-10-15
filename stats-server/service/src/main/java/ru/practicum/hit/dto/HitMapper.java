package ru.practicum.hit.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.practicum.dto.HitRequestDto;
import ru.practicum.dto.HitResponseDto;
import ru.practicum.hit.model.HitStats;

@Mapper
public interface HitMapper {

    HitMapper INSTANCE = Mappers.getMapper(HitMapper.class);

    HitStats toHitStats(HitRequestDto hitRequestDto);

    HitResponseDto toHitResponseDto(HitStats hitStats);
}
