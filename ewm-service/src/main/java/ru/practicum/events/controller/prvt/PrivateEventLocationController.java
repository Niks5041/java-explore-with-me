package ru.practicum.events.controller.prvt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.events.dto.LocationDto;
import ru.practicum.events.service.LocationService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/users/{userId}/locations")
public class PrivateEventLocationController {

    private final LocationService locationService;

    @PutMapping("/{locationId}/likes")
    public LocationDto addLike(//Добавление лайка на локацию
                               @PathVariable long userId,
                               @PathVariable long locationId) {

        log.info("==> PUT. /users/{userId}/locations/{locationId}/likes" +
                "Добавление лайка для локации id: {} пользователем id: {}", locationId, userId);
        LocationDto locationDto = locationService.addLike(userId, locationId);
        log.info("<== PUT. /users/{userId}/events/{eventId}/likes" +
                "Добавлен лайк для локации: {} пользователем id: {} added.", locationDto, userId);
        return locationDto;
    }

    @DeleteMapping("/{locationId}/likes")
    public void deleteLike(//удаление лайка на локацию
                           @PathVariable long userId,
                           @PathVariable long locationId
    ) {
        log.info("==> DELETE. /users/{userId}/events/{eventId}/likes" +
                "Удаление лайка для локации id: {} пользователем id: {}", locationId, userId);
        locationService.deleteLike(userId, locationId);
        log.info("<== DELETE. /users/{userId}/events/{eventId}/likes" +
                "Удален лайк для локации id: {} пользователем id: {} deleted.", locationId, userId);
    }

    @GetMapping("/top")
    public List<LocationDto> getTop(
            @PathVariable long userId,
            @RequestParam(required = false, defaultValue = "10") Integer count,
            HttpServletRequest httpRequest) {
        log.info("==> GET /users/{userId}/locations/top");
        List<LocationDto> locationDtoList = locationService.getTop(userId, count);
        log.info("<== GET /users/{userId}/locations/top Returning top {} locations.", locationDtoList);
        return locationDtoList;
    }
}