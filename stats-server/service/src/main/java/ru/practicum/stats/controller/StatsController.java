package ru.practicum.stats.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.stats.service.StatsService;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/stats")
@AllArgsConstructor
@Slf4j
public class StatsController {

    private final StatsService statsService;

    @GetMapping
    public List<ResponseStatsDto> getStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                                 @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                                 @RequestParam(required = false) String[] uris,
                                                 @RequestParam(required = false) boolean unique) {
        log.info("Пришел GET /stats запрос c параметрами start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        List<ResponseStatsDto> responseStatsDto = statsService.getStats(start, end, uris, unique);
        log.info("Отправлен GET /stats ответ с телом: " + responseStatsDto);
        return responseStatsDto;
    }
}
