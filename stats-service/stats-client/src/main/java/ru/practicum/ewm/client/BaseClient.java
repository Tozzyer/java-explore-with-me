package ru.practicum.ewm.client;

import jakarta.annotation.Nullable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class BaseClient {

    private static final List<MediaType> APPLICATION_JSON = List.of(MediaType.APPLICATION_JSON);
    private static final String PATH_STATS = "/stats?start={start}&end={end}&uris={uris}&unique={unique}";
    private static final String PATH_HIT = "/hit";

    private final RestTemplate restTemplate;

    protected BaseClient(final RestTemplate restTemplate) {
        this.restTemplate = Objects.requireNonNull(restTemplate, "restTemplate must not be null");
    }

    protected ResponseEntity<Object> get(@Nullable final Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, PATH_STATS, parameters, null);
    }

    protected <T> ResponseEntity<Object> post(final T body) {
        return makeAndSendRequest(HttpMethod.POST, PATH_HIT, null, body);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(final HttpMethod method,
                                                          final String path,
                                                          @Nullable final Map<String, Object> parameters,
                                                          @Nullable final T body) {
        final HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());
        try {
            final ResponseEntity<Object> response = (parameters != null)
                    ? restTemplate.exchange(path, method, requestEntity, Object.class, parameters)
                    : restTemplate.exchange(path, method, requestEntity, Object.class);
            return prepareResponse(response);
        } catch (HttpStatusCodeException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsByteArray());
        }
    }

    private HttpHeaders defaultHeaders() {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(APPLICATION_JSON);
        return headers;
    }

    private static ResponseEntity<Object> prepareResponse(final ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }
        final ResponseEntity.BodyBuilder builder = ResponseEntity.status(response.getStatusCode());
        return response.hasBody() ? builder.body(response.getBody()) : builder.build();
    }
}
