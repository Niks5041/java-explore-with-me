package ru.practicum.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.dto.HitRequestDto;
import ru.practicum.dto.RequestStatsDto;

import java.time.LocalTime;

@Service
@Slf4j
@AllArgsConstructor
public class MainClient {

    protected final RestTemplate rest;
    private final String hitUrl = "http://localhost:9090/hit";
    private final String statsUrl = "http://localhost:9090/stats";


    public ResponseEntity<Object> saveHit(HitRequestDto hitRequestDto) {
        log.info("Пришел POST /hit запрос в клиент с телом: " + hitRequestDto);
        ResponseEntity<Object> response = rest.postForEntity(hitUrl, hitRequestDto, Object.class);
        log.info("Отправлен POST /hit ответ с телом: " + response.getBody());
        return response;
    }

    public ResponseEntity<Object> getStats(RequestStatsDto requestStatsDto,
                                           LocalTime start,
                                           LocalTime end,
                                           String[] uris,
                                           boolean unique) {
        log.info("Пришел GET /stats запрос {} с параметрами: start={}, end={}, uris={}, unique={}" +
                requestStatsDto, start, end, uris, unique);

        String url = String.format("%s?start=%s&end=%s&uris=%s&unique=%s",
                statsUrl,
                start.toString(),
                end.toString(),
                String.join(",", uris),
                unique);

        ResponseEntity<Object> response = rest.getForEntity(url, Object.class);

        log.info("Получен ответ от GET /stats: {}", response.getBody());
        return response;
    }
}

