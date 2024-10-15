package ru.practicum.stats.service;


import ru.practicum.dto.ResponseStatsDto;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    List<ResponseStatsDto> getStats(LocalDateTime start,
                                    LocalDateTime end,
                                    String[] uris,
                                    Boolean unique);
}
