package ru.practicum.stats.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dto.ResponseStatsDto;
import ru.practicum.hit.dao.HitRepository;
import ru.practicum.hit.model.HitStats;
import ru.practicum.stats.dto.StatsMapperDto;


import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@AllArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final HitRepository hitRepository;

    @Override
    public List<ResponseStatsDto> getStats(LocalDateTime start,
                                           LocalDateTime end,
                                           String[] uris,
                                           Boolean unique) {
        log.info("Пришел запрос в сервис Stats c параметрами start={}, end={}, uris={}, unique={}",
                start, end, uris, unique);

        if (uris == null) {
            Integer hitsCount = hitRepository.countByTimestampBeforeAndTimestampAfter(start, end);
            return hitRepository.findByTimestampBeforeAndTimestampAfter(end, start).stream()
                    .map(StatsMapperDto::statsToHitsDto)
                    .peek(responseStatsDto -> responseStatsDto.setHits(hitsCount))
                    .toList();
        }

        if (unique.equals(true)) {
            return hitRepository.findDistinctHitsNative(start, end, uris).stream()
                    .map(StatsMapperDto::statsToHitsDto)
                    .collect(Collectors.toMap(
                            ResponseStatsDto::getUri,
                            responseStatsDto -> {
                                Integer hitsCount = hitRepository.countDistinctIpsByTimestampBeforeAndTimestampAfterAndUriIn(start, end, uris);
                                responseStatsDto.setHits(hitsCount);
                                return responseStatsDto;
                            }
                    ))
                    .values()
                    .stream()
                    .toList();
        }

        return hitRepository.findByTimestampBeforeAndTimestampAfterAndUriIn(end, start, uris).stream()
                .collect(Collectors.groupingBy(HitStats::getUri, Collectors.counting()))
                .entrySet().stream()
                .map(entry -> {
                    ResponseStatsDto responseStatsDto = StatsMapperDto.statsToHitsDto(new HitStats());
                    responseStatsDto.setUri(entry.getKey());
                    responseStatsDto.setHits(entry.getValue().intValue());
                    return responseStatsDto;
                })
                .sorted(Comparator.comparingInt(ResponseStatsDto::getHits).reversed())
                .toList();
    }
}
