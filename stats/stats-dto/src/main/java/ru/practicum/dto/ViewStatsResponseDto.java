package ru.practicum.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewStatsResponseDto {

    private String app;
    private String uri;
    private Long hits;
}
