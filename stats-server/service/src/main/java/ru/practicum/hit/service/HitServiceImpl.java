package ru.practicum.hit.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.HitRequestDto;
import ru.practicum.dto.HitResponseDto;
import ru.practicum.hit.dao.HitRepository;
import ru.practicum.hit.dto.HitMapper;

@Service
@Slf4j
@AllArgsConstructor
public class HitServiceImpl implements HitService {

     private final HitRepository hitRepository;

    @Override
    public HitResponseDto saveHit(HitRequestDto hitRequestDto) {
        log.info("Пришел запрос в сервис Hit с телом: " + hitRequestDto);

        return HitMapper.INSTANCE.toHitResponseDto(hitRepository.save(HitMapper.INSTANCE.toHitStats(hitRequestDto)));
    }
}
