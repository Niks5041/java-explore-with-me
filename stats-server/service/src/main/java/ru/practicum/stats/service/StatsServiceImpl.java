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
//            List<ResponseStatsDto> responseList = new ArrayList<>();
//            List<HitStats> distinctHits = hitRepository.findDistinctHitsNative(start, end, uris);
//            for (HitStats hit : distinctHits) {
//                ResponseStatsDto responseStatsDto = StatsMapperDto.statsToHitsDto(hit);
//                Integer hitsCount = hitRepository.countDistinctIpsByTimestampBeforeAndTimestampAfterAndUriIn(start, end, uris);
//                responseStatsDto.setHits(hitsCount);
//                responseList.add(responseStatsDto);
//            }
//            return responseList;

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

//        List<HitStats> hits = hitRepository.findByTimestampBeforeAndTimestampAfterAndUriIn(end, start, uris);
//        List<String> processedUris = new ArrayList<>();
//
//        for (HitStats hit : hits) {
//            String uri = hit.getUri();
//            if (!processedUris.contains(uri)) {
//                long hitsCount = hits.stream().filter(h -> h.getUri().equals(uri)).count();
//                ResponseStatsDto responseStatsDto = StatsMapperDto.statsToHitsDto(new HitStats());
//                responseStatsDto.setUri(uri);
//                responseStatsDto.setHits((int) hitsCount);
//                responseList.add(responseStatsDto);
//                processedUris.add(uri);
//            }
//        }
//
//           responseList.sort(Comparator.comparingInt(ResponseStatsDto::getHits).reversed());
//
//        return responseList;

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
