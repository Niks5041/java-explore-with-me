package ru.practicum.hit.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.HitRequestDto;
import ru.practicum.dto.HitResponseDto;
import ru.practicum.hit.service.HitService;

@RestController
@RequestMapping("/hit")
@RequiredArgsConstructor
@Slf4j
public class HitController {

    private final HitService hitService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HitResponseDto saveHit(@RequestBody HitRequestDto hitRequestDto) {
        log.info("Пришел POST /hit запрос с телом: {}", hitRequestDto);
        HitResponseDto hitResponseDto = hitService.saveHit(hitRequestDto);
        log.info("Отправлен POST /hit ответ c телом: " + hitResponseDto);
        return hitResponseDto;
    }
}
