package ru.practicum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.EndpointHitRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.Instant;

public class StatsClient extends BaseClient {

    private static final String SERVICE_ID = "ewm-main-service";

    public StatsClient(@Value("${stats-server.url}") String statsUrl,
                       RestTemplateBuilder templateBuilder) {
        super(
                templateBuilder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(statsUrl))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void createHit(HttpServletRequest request) {
        EndpointHitRequestDto payload = EndpointHitRequestDto.builder()
                .app(SERVICE_ID)
                .uri(request.getRequestURI())
                .ip(request.getRemoteAddr())
                .timestamp(Timestamp.from(Instant.now()))
                .build();

        post("/hit", payload);
    }
}
