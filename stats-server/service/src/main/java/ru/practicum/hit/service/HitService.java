package ru.practicum.hit.service;

import ru.practicum.dto.HitRequestDto;
import ru.practicum.dto.HitResponseDto;

public interface HitService {
    HitResponseDto saveHit(HitRequestDto hitRequestDto);
}
