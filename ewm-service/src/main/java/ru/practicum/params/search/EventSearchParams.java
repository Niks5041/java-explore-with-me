package ru.practicum.params.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class EventSearchParams {
    private AdminSearchParams adminSearchParams;
    private PrivateSearchParams privateSearchParams;
    private PublicSearchParams publicSearchParams;
    private Integer from;
    private Integer size;
}
