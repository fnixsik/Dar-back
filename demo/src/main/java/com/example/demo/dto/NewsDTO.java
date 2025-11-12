package com.example.demo.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class NewsDTO {
    private Long id;
    private String title;
    private String content;
    private String img;
    private LocalDate date;
}
