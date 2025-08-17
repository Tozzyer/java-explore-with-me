package ru.practicum.ewm.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.dto.request.NewEndpointHitRequestDto;
import ru.practicum.ewm.dto.response.EndpointHitResponseDto;
import ru.practicum.ewm.dto.response.ViewStatsResponseDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StatsClient extends BaseClient {

    private static final TypeReference<List<ViewStatsResponseDto>> VIEW_STATS_LIST_TYPE =
            new TypeReference<List<ViewStatsResponseDto>>() {};
    private final ObjectMapper objectMapper = new ObjectMapper();

    public StatsClient(@Value("${stats-service.url}") String serverUri, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUri))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build());
    }

    public EndpointHitResponseDto saveEvent(NewEndpointHitRequestDto endpointHitDto) throws IOException {
        final ResponseEntity<Object> response = post(endpointHitDto);
        return objectMapper.convertValue(response.getBody(), EndpointHitResponseDto.class);
    }

    public List<ViewStatsResponseDto> getStats(LocalDateTime start,
                                               LocalDateTime end,
                                               List<String> uris,
                                               boolean unique) throws IOException {
        final Map<String, Object> params = new HashMap<>(4);
        params.put("start", start);
        params.put("end", end);
        params.put("uris", String.join(",", uris));
        params.put("unique", unique);

        final ResponseEntity<Object> response = get(params);
        return objectMapper.convertValue(response.getBody(), VIEW_STATS_LIST_TYPE);
    }
}
