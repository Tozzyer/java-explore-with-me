package ru.practicum.ewm.server.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "endpoint_hits")
public class EndpointHit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String app;

    @Column(nullable = false)
    private String uri;

    @Column(nullable = false)
    private String ip;

    @Column(name = "created", nullable = false)
    private LocalDateTime timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EndpointHit that)) return false;

        if (id != null && that.id != null) {
            return id.equals(that.id);
        }
        return Objects.equals(app, that.app)
                && Objects.equals(uri, that.uri)
                && Objects.equals(ip, that.ip)
                && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return (id != null)
                ? Objects.hash(id)
                : Objects.hash(app, uri, ip, timestamp);
    }
}
