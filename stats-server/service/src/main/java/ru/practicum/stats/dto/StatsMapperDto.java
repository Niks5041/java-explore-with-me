package ru.practicum.stats.dto;

import lombok.experimental.UtilityClass;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.hit.model.HitStats;

@UtilityClass
public class StatsMapperDto {
    public ResponseStatsDto statsToHitsDto(HitStats hitStats) {
        ResponseStatsDto dto = new ResponseStatsDto();
        dto.setApp(hitStats.getApp());
        dto.setUri(hitStats.getUri());
        dto.setHits(null);
        return dto;
    }
}
