package ru.practicum.entity;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewStats {

    private String app;
    private String uri;
    private Long hits;
}
