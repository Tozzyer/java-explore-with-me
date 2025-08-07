package ru.practicum;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RequiredArgsConstructor
class BaseClient {

    protected final RestTemplate rest;

    private static ResponseEntity<Object> mapGatewayResponse(ResponseEntity<Object> incoming) {
        if (incoming.getStatusCode().is2xxSuccessful()) {
            return incoming;
        }

        ResponseEntity.BodyBuilder builder = ResponseEntity.status(incoming.getStatusCode());
        return incoming.hasBody()
                ? builder.body(incoming.getBody())
                : builder.build();
    }

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return createSendRequest(HttpMethod.POST, path, body);
    }

    private <T> ResponseEntity<Object> createSendRequest(HttpMethod method, String path, T body) {
        HttpEntity<T> httpEntity = new HttpEntity<>(Objects.requireNonNull(body));
        try {
            ResponseEntity<Object> exchangeResult = rest.exchange(path, method, httpEntity, Object.class);
            return mapGatewayResponse(exchangeResult);
        } catch (HttpStatusCodeException exception) {
            return ResponseEntity
                    .status(exception.getStatusCode())
                    .body(exception.getResponseBodyAsByteArray());
        }
    }
}
